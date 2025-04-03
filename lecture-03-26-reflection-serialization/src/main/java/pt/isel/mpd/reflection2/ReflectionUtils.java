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
    
    
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> componentType, int size) {
        return (T[]) Array.newInstance(componentType, size);
    }
    
}
