package com.zmh.easyexcel.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/26 16:49
 * @description:
 */
public class FieldUtils {

    /**
     * 获取AllField 包括父级
     * */
    public static List<Field> getAllFields(Class<?> cls){
        List<Field> allFields = new ArrayList<Field>();

        for(Class<?> currentClass = cls; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
        }

        return allFields;
    }
}
