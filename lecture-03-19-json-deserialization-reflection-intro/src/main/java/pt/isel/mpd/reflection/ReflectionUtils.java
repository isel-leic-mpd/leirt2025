package pt.isel.mpd.reflection;

import pt.isel.mpd.exceptions.MethodException;
import pt.isel.mpd.exceptions.WrongMethodTypeException;

import java.lang.reflect.*;
import java.util.*;

import static pt.isel.mpd.utils.Utils.TODO;

public class ReflectionUtils {
    /**
     * Check if a type (class or interface)
     * implements an interface or any of its super interfaces
     * @param type
     * @param interfaceType
     * @return
     */
    public static boolean implementsInterface(Class<?> type, Class<?> interfaceType) {
       TODO();
       return false;
    }
    
    /**
     * Check if a class is the same or a subclass of another class
     * @param cls
     * @param superCls
     * @return
     */
    public static boolean isSameOrSubClass(Class<?> cls, Class<?> superCls) {
        TODO();
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
       TODO();
       return false;
    }
    
    
    
    /**
     * fields
     */
    
    public static List<Field>  getAllFields(Class<?> cls) {
        TODO();
        return null;
    }
    
    public static boolean isStatic(Member member) {
        return Modifier.isStatic(member.getModifiers());
    }
    
    
    /**
     * Methods
      */
    
    public static List<Method> getMethods(Class<?> cls) {
        TODO();
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticMethod(String clsName, String methodName, Class<?>[] params, Object[] args) {
       TODO();
       return null;
    }
}
