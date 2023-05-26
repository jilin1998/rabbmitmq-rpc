package com.rmq.rpc.common.utils;

import java.lang.annotation.Annotation;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/26 16:20:00
 */
public class AnnotationUtils {
    public static <A extends  Annotation> A findAnnotation(Class<?> clazz,Class<A> annotationType){
        //搜索
        A annotation = clazz.getDeclaredAnnotation(annotationType);
        if (annotation!=null){
            return annotation;
        }else{
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> aClass : interfaces) {
                annotation = aClass.getDeclaredAnnotation(annotationType);
                if (annotation!=null){
                    break;
                }
            }
        }
        return annotation;
    }
}
