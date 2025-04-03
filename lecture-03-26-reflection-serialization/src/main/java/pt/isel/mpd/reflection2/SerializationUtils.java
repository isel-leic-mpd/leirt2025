package pt.isel.mpd.reflection2;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.isel.mpd.reflection2.exceptions.NonCompatibleTypesException;
import pt.isel.mpd.reflection2.exceptions.SerializationException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import pt.isel.mpd.reflection2.annotations.Transient;

import static pt.isel.mpd.reflection2.ReflectionUtils.*;

/**
 * Utility methods to save an object graph in JSON (serialization) and
 * rebuild it from JSON (deserialization)
 * This version support nulls, but doesn't support multiple references to the
 * SAME object, a graph with cycles, or most library types (ex: LocalDate)
 */
public class SerializationUtils {
    //
    // save special types methods
    //
    
    public static JSONObject saveString(String str) {
        var jsonString = new JSONObject();
        if (str == null) {
            jsonString.put("__isNull__", true);
            return jsonString;
        }
        jsonString.put("__type__", String.class.getName());
        jsonString.put("value", str);
        return jsonString;
    }
    
    @SuppressWarnings("unchecked")
    public static JSONObject saveEnum(Object value) {
        var jsonEnum = new JSONObject();
        if (value == null) {
            jsonEnum.put("__isNull__", true);
            return jsonEnum;
        }
        jsonEnum.put("__type__", value.getClass().getName());
        jsonEnum.put("value", ((Enum)value).name());
        return jsonEnum;
    }
    
    private static JSONObject saveContainer(Collection<?> col, Class<?> containerType, Class<?> elemType) {
        var jsonCol = new JSONObject();
        if (col == null) {
            jsonCol.put("__isNull__", true);
            return jsonCol;
        }
        var jsonArray = new JSONArray(col.size());
        for (var item : col) {
            jsonArray.put(saveObject(item));
        }
        
        jsonCol.put("__type__", containerType.getName());
        jsonCol.put("__elemType__", elemType.getName() );
        jsonCol.put("items", jsonArray);
        return jsonCol;
    }
    
    public static JSONObject saveCollection(Collection<?> col, Class<?> elemType) {
        return saveContainer(col, col.getClass(), elemType);
    }
    
    
    public static JSONObject saveArray(Object[] a) {
        Class<?> arrayType = a.getClass();
        
        return saveContainer(Arrays.asList(a), arrayType, arrayType.getComponentType());
    }
    
    
    public static JSONObject saveObject(Object obj) {
        JSONObject objJson = new JSONObject();
        
        if (obj == null) {
            objJson.put("__isNull__", true);
            return objJson;
        }
        var objClass = obj.getClass();
        
        objJson.put("__type__", objClass.getName());
        
        var fields = getAllFields(obj);
        for (var field : fields) {
            if (isStatic(field)) continue;
            if (field.getAnnotation(Transient.class) != null) continue;
            field.setAccessible(true);
            try {
                var value = field.get(obj);
                
                var fieldType = field.getType();
                var fieldName = field.getName();
               
                if (isPrimitive(fieldType)) {
                    objJson.put(fieldName, value.toString());
                }
                else if (isString(fieldType)) {
                    objJson.put(fieldName, saveString((String) value));
                }
                else if (isEnum(fieldType)) {
                    objJson.put(fieldName, saveEnum(value));
                }
                else if (isCollection(fieldType)) {
                    var elemType = getGenericParamType(field.getGenericType()).orElse(Object.class);
                    objJson.put(fieldName, saveCollection((Collection<?>) value, getClassFromType(elemType)));
                }
                else if (isArray(fieldType)) {
                    objJson.put(fieldName, saveArray( (Object[]) value));
                }
                else {
                    objJson.put(fieldName, saveObject(value));
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objJson;
    }
    
    /**
     * Desserialization
     */
    
    
    @SuppressWarnings("unchecked")
    private static <T> Collection<T> loadCollection(Class<T> componentType, JSONObject json) throws Exception {
        try {
            var colType = Class.forName(json.getString("__type__"));
            var items = json.getJSONArray("items");
            
            var newCol = (Collection<T>) colType.getConstructor().newInstance();
            for (int i= 0; i < items.length(); ++i) {
                T itemObj = loadObject(componentType, items.getJSONObject(i));
                newCol.add(itemObj);
            }
            return newCol;
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static <T> T[] loadArray(Class<T> componentType, JSONObject json)  {
        var items = json.getJSONArray("items");
        var newArray = createArray(componentType, items.length());
        for (int i= 0; i < items.length(); ++i) {
            T itemObj = loadObject(componentType, items.getJSONObject(i));
            newArray[i] = itemObj;
        }
        return newArray;
    }
    
    private static String loadString(JSONObject json) {
        return json.getString("value");
    }
    
    @SuppressWarnings("unchecked")
    private static Enum<?> loadEnum(Class<Enum> enumType, JSONObject json) {
        return json.getEnum(enumType,"value");
    }
    
    private static Object loadPrimitive(Field f,String jsonName,  JSONObject json) {
        var fType = f.getType();
        if (fType == int.class)
            return json.getInt(f.getName());
        if (fType == double.class)
            return json.getDouble(f.getName());
        if (fType == boolean.class) {
            return json.getBoolean(f.getName());
        }
        throw new SerializationException("Nom supported primitive type");
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T loadObject(Class<T> cls, JSONObject json) {
        boolean isNull = json.optBoolean("__isNull__");
        if (isNull)
            return null;
        try {
            var objType = Class.forName(json.getString("__type__"));
            if (!cls.isAssignableFrom(objType)) {
                throw new NonCompatibleTypesException(cls, objType);
            }
            if (isArray(objType)) {
                var elemType = Class.forName(json.getString("__elemType__"));
                return (T) loadArray( elemType, json);
            }
            else if (isCollection(objType)) {
                var elemType = Class.forName(json.getString("__elemType__"));
                return (T) loadCollection( elemType, json);
            }
            else if (isEnum(objType)) {
                return (T) loadEnum((Class<Enum>) objType, json);
            }
            else if (isString(objType)) {
                return (T) loadString(json);
            }
            else {
                var ctor   = objType.getDeclaredConstructor();
                ctor.setAccessible(true);
                Object obj = ctor.newInstance();
                var fields = getAllFields(objType);
                for (var field : fields) {
                    if (isStatic(field))
                        continue;
                    if (field.getAnnotation(Transient.class) != null) continue;
                    String jsonName = field.getName();
                    field.setAccessible(true);
                    var fieldType = field.getType();
                    if (isPrimitive(fieldType)) {
                        field.set(obj, loadPrimitive(field, jsonName, json));
                    } else {
                        field.set(obj, loadObject(field.getType(), json.getJSONObject(jsonName)));
                    }
                }
                return (T) obj;
            }
        }
        catch(Exception e) {
            throw new RuntimeException("Error deserialize object", e);
        }
    }
    
}
