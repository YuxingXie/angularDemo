package com.dabast.common.util;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class MongoDocumentUtil  {
    public static<T> boolean isKeyExists(Class<T> clazz,String key){
        if (clazz==null) return false;
        if (key==null) return false;
        for (java.lang.reflect.Field classField:clazz.getDeclaredFields()){
            Field documentField=clazz.getAnnotation(Field.class);
            if (documentField==null) continue;
            String documentFieldValue=documentField.value();
            if (documentFieldValue.equals(key)){
                return true;
            }
        }
        return ReflectUtil.isFieldExist(clazz,key);
    }
    public static<T> String getId(T t){
        Class<?> clazz=t.getClass();
        if (clazz==null) return null;
        for (java.lang.reflect.Field classField:clazz.getDeclaredFields()){
            Id id=clazz.getAnnotation(Id.class);
            if (id==null) continue;
            Object idValue=ReflectUtil.getValue(t, classField.getName());
            return idValue==null?null:idValue.toString();
        }
        return null;
    }
}
