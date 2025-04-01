package pt.isel.mpd.reflection2;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.isel.mpd.reflection2.exceptions.MethodException;
import pt.isel.mpd.reflection2.exceptions.NonCompatibleTypesException;
import pt.isel.mpd.reflection2.exceptions.SerializationException;
import pt.isel.mpd.reflection2.exceptions.WrongMethodTypeException;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtils {
    /**
     * Check if a class is the same or a subclass of another class
     * @param cls
     * @param superCls
     * @return
     */
    public static boolean isSameOrSubClass(Class<?> cls, Class<?> superCls) {
        var currentClass = cls;
        while(currentClass != null)  {
            if (currentClass == superCls) return true;
            currentClass = currentClass.getSuperclass();
        }
        return false;
    }

    /**
     * Check if a type (class or interface)
     * implements an interface or any of its super interfaces
     * @param type
     * @param interfaceType
     * @return
     */
    public static boolean implementsInterface(Class<?> type, Class<?> interfaceType) {
        if (type.isInterface()) {
            if (type == interfaceType) return true;
            for (var i : type.getInterfaces()) {
                if (implementsInterface(i, interfaceType)) return true;
            }
        }
        else {
            var currentCls = type;
            while(currentCls != null) {
                var implemented  = currentCls.getInterfaces();
                for (var i : implemented) {
                     if (implementsInterface(i, interfaceType)) return true;
                }
                currentCls = currentCls.getSuperclass();
            }
        }
        return false;
    }
    
    /**
     * Check if an object class is compatible with
     * a certain type (class or interface)
     * This is similar to the Java instanceof operator
     * @param obj
     * @param type
     * @return
     */
    public static boolean isInstanceOf(Object obj, Class<?> type) {
        Class<?> objClass = obj.getClass();
        if (type.isInterface()) {
            return implementsInterface(objClass, type);
        }
        else {
            return isSameOrSubClass(objClass, type);
        }
    }
    
    
    public static boolean isAbstract(Class<?> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }
    
    public static boolean isArray(Class<?> cls) {
        return cls.isArray();
    }
    
    public static boolean isString(Class<?> cls) {
        return cls == String.class;
    }
    
    public static boolean  isCollection(Class<?> cls) {
        return implementsInterface(cls, Collection.class);
    }
    
    public static boolean  isPrimitive(Class<?> cls) {
        return cls.isPrimitive();
    }
    
    public static  boolean isGeneric(Type type) {
        return type instanceof java.lang.reflect.ParameterizedType;
    }
    
    public static boolean isEnum(Class<?> cls) {
        return Enum.class.isAssignableFrom(cls);
    }
    
    /**
     * Get the generic type of a field.
     * Supports only one generic type parameter
     * @param type
     * @return
     */
    public static Optional<Type> getGenericParamType(Type type) {
        if (!isGeneric(type)) return Optional.empty();
        return Optional.of(((ParameterizedType) type).getActualTypeArguments()[0]);
    }
    
    @SuppressWarnings("unchecked")
    public static Class<?> getClassFromType(Type t) {
        if (t instanceof ParameterizedType pt) {
            return (Class<?>) pt.getRawType();
        }
        else {
            return (Class<?>) t;
        }
    }
    
    public static List<Field>  getAllFields(Class<?> cls) {
        var fields = new ArrayList<Field>();
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        return fields;
    }
    
    public static List<Field> getAllFields(Object obj) {
        return getAllFields(obj.getClass());
    }
    
    public static boolean isStatic(Member member) {
        return Modifier.isStatic(member.getModifiers());
    }
    
    public static boolean isPrivate(Member member) {
        return Modifier.isPrivate(member.getModifiers());
    }
    
    public static boolean isPublic(Member member) {
        return Modifier.isPublic(member.getModifiers());
    }
    
    public static boolean isProtected(Member member) {
        return Modifier.isProtected(member.getModifiers());
    }
    
    /**
     * Methods
     */
    public static List<Method> getMethods(Class<?> cls) {
        var methods = new ArrayList<Method>();
        while (cls != null) {
            methods.addAll(Arrays.asList(cls.getDeclaredMethods()));
            cls = cls.getSuperclass();
        }
        return methods;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticMethod(String clsName, String methodName, Class<?>[] params, Object[] args) {
        try {
            var cls = Class.forName(clsName);
            
            var method = cls.getMethod(methodName, params);
            if (!isStatic(method)) {
                throw new WrongMethodTypeException("Should be static");
            }
            return (T) method.invoke(null, args);
        }
        catch(NoSuchMethodException | ClassNotFoundException |
              InvocationTargetException | IllegalAccessException| IllegalArgumentException e) {
            throw new MethodException(e);
        }
    }
    
    
    /**
     * Serialization
     */
    
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
    
    @SuppressWarnings("unchecked")
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
            field.setAccessible(true);
            try {
                var value = field.get(obj);
                
                var fieldType = field.getType();
                
                if (isPrimitive(fieldType)) {
                    objJson.put(field.getName(), value.toString());
                }
                else if (isString(fieldType)) {
                    objJson.put(field.getName(), saveString((String) value));
                }
                else if (isEnum(fieldType)) {
                    objJson.put(field.getName(), saveEnum(value));
                }
                else if (isCollection(fieldType)) {
                    var elemType = getGenericParamType(field.getGenericType()).orElse(Object.class);
                    objJson.put(field.getName(), saveCollection((Collection<?>) value, getClassFromType(elemType)));
                }
                else if (isArray(fieldType)) {
                    objJson.put(field.getName(), saveArray( (Object[]) value));
                }
                else {
                    objJson.put(field.getName(), saveObject(value));
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
    public static <T> T[] createArray(Class<T> componentType, int size) {
        return (T[]) Array.newInstance(componentType, size);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> loadCollection(Class<T> componentType, JSONObject json) throws Exception {
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
    
    
    public static <T> T[] loadArray(Class<T> componentType, JSONObject json)  {
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
    
    private static Object loadPrimitive(Field f, JSONObject json) {
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
                return (T) loadCollection( objType, json);
            }
            else if (isEnum(objType)) {
                return (T) loadEnum((Class<Enum>) objType, json);
            }
            else if (isString(objType)) {
                return (T) loadString(json);
            }
            else {
                var ctor = objType.getDeclaredConstructor();
                ctor.setAccessible(true);
                var obj = ctor.newInstance();
                var fields = getAllFields(objType);
                for (var field : fields) {
                    if (isStatic(field))
                        continue;
                    field.setAccessible(true);
                    var fieldType = field.getType();
                    if (isPrimitive(fieldType)) {
                        field.set(obj, loadPrimitive(field, json));
                    } else {
                        field.set(obj, loadObject(field.getType(), json.getJSONObject(field.getName())));
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
