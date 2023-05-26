package com.rmq.rpc.consumer.instance.impl;

import com.rmq.rpc.consumer.instance.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description 反射手动实例化
 * @date 2023/5/26 11:08:56
 */
public class ReflectInstanceFactory implements InstanceFactory {
    private final Logger log = LoggerFactory.getLogger(ReflectInstanceFactory.class);
    /**
     * 反射实例化,反射实例化必须存在参数
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T instanceRegisterBeans(Class<T> clazz) {
        try{
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            log.error("instance {} failed. cased not exist no arg constructor",clazz.getName(),e);
        }catch (Exception e){
            log.error("instance {} fialed. cased {}",clazz.getName(),e.getMessage(),e);
        }
        return null;
    }

}
