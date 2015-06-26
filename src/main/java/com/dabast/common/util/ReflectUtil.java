package com.dabast.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class ReflectUtil {
    public static <T>boolean methodExists(T t,String methodName){
        Method[] methods=t.getClass().getMethods();
        for(Method method:methods){
            if(methodName.equals(method.getName())){
                return true;
            }
        }
        return false;
    }
    public static String getGetterMethodName(String fieldName) {
        return "get" + firstUpperCase(fieldName);
    }
    public static String getSetterMethodName(String fieldName) {
        return "set" + firstUpperCase(fieldName);
    }
    public static <T> Object invokeGetter(T t,String property){
        try {
            Method getter=t.getClass().getDeclaredMethod(getGetterMethodName(property)) ;
            return getter.invoke(t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String firstLowerCase(String word) {
        return  word.substring(0, 1).toLowerCase()+ word.substring(1);
    }
    public static String firstUpperCase(String word) {
        return  word.substring(0, 1).toUpperCase()+ word.substring(1);
    }
    public static <T> boolean isFieldExist(Class<T> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> Object getValue(T t,String property){
    	Class clazz=t.getClass();
    	Method method;
		try {
			method = clazz.getDeclaredMethod(getGetterMethodName(property), null);
			return method.invoke(t, null);
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		
    	
    }
}
