package com.rmq.rpc.consumer.forward.impl;

import com.rmq.rpc.common.mq.Parser;
import com.rmq.rpc.common.mq.json.Param;
import com.rmq.rpc.common.mq.json.ParamItem;
import com.rmq.rpc.consumer.config.Configuration;
import com.rmq.rpc.consumer.forward.Forward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jilin
 * @description
 * @date 2023年5月26日-09:26:03
 */
public class ForwardMethodImpl implements Forward {
    //
    private final Logger log = LoggerFactory.getLogger(ForwardMethodImpl.class);
    /**
     * 全局配置
     */
    private final Configuration configuration;

    /**
     * @param configuration
     */
    public ForwardMethodImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T forwardWithReturn(Param param,Class<T> clazz) {
        // 搜索方法
        Class<?> serviceNameClazz = configuration.getClazzByService(param.getServiceName());
        Object bean = configuration.getBeanByService(param.getServiceName(), serviceNameClazz);
        if (serviceNameClazz == null) {
            log.error("serviceName:{} not found Service.", param.getServiceName());
        }
        try{
            return (T) excuteMethod(param,bean,serviceNameClazz);
        }catch(Exception e){
            log.error("invoke method is faild.{}:{}",serviceNameClazz.getSimpleName(),param.getMethodName(), e);
        }
        return null;
    }

    @Override
    public void forward(String json) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forward'");
    }

    /**
     * 执行反射方法
     * 
     * @param param
     * @param o
     * @param serviceNameClazz
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private Object excuteMethod(Param param, Object o, Class<?> serviceNameClazz)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        List<Class<?>> convertToClass = convertToClass(param.getParams());
        Method method = serviceNameClazz.getDeclaredMethod(param.getMethodName(), convertToClass.toArray(new Class[0]));
        // 解析参数信息
        List<Object> jsonToArgs = jsonToArgs(param.getParams(), convertToClass);
        return method.invoke(o, jsonToArgs.toArray(new Object[0]));
    }

    /**
     * 将全限定类名转换成class
     * 
     * @param items
     * @return
     * @throws ClassNotFoundException
     */
    List<Class<?>> convertToClass(List<ParamItem> items) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        for (ParamItem item : items) {
            Class<?> forName = Class.forName(item.getClassName());
            classes.add(forName);
        }
        return classes;
    }

    /**
     * 按照类模板信息反序列化
     * 
     * @param items
     * @param classes
     * @return
     */
    List<Object> jsonToArgs(List<ParamItem> items, List<Class<?>> classes) {
        List<Object> args = new ArrayList<>();
        Parser parser = configuration.getParser();
        for (int i = 0; i < items.size(); i++) {
            ParamItem paramItem = items.get(i);
            Class<?> clazz = classes.get(i);
            Object parse = parser.parse(paramItem.getJson(), clazz);
            args.add(parse);
        }
        return args;
    }

}
