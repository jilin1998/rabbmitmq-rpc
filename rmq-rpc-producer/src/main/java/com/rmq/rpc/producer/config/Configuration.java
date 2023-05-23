package com.rmq.rpc.producer.config;


import com.rmq.rpc.producer.annotations.RmqServer;
import com.rmq.rpc.producer.client.RmqClient;
import com.rmq.rpc.producer.exception.RmqException;
import com.rmq.rpc.producer.mq.Encoder;
import com.rmq.rpc.producer.mq.Parser;
import com.rmq.rpc.producer.proxy.ProxyBean;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 全局配置
 * @date 2023/5/22 14:30:09
 */
public class Configuration {
    /**
     * 存储需要被代理的接口
     */
    Map<String,Class<?>> beans = new ConcurrentHashMap<>();
    /**
     * 存储原本接口和代理后的接口
     */
    Map<String, ProxyBean<?>> proxyBeans = new ConcurrentHashMap<>();
    /**
     * 存储代理类和name的关系
     */
    Map<Object,String> proxyForName = new HashMap<>();
    /**
     * 序列化工厂
     */
    Encoder encoder;
    /**
     * 解析工厂
     */
    Parser parser;

    /**
     * rmq配置类
     */
    RmqConfig rmqConfig;
    /**
     * mq客户端
     */
    RmqClient rmqClient;

    /**
     * 获取代理类对应的serviceName
     * @param proxyBeans
     * @return
     */
    public String getServiceName(Object proxyBeans){
        String name = proxyForName.get(proxyBeans);
        if (StringUtils.isBlank(name)){
            throw new RmqException("proxyBeans not found service name:"+proxyBeans);
        }
        return name;
    }

    public<T> ProxyBean<T> getProxyBean(String name,Class<T> tClass){
        Object proxyBean = proxyBeans.get(name);
        if (proxyBean==null){
            throw new RmqException("name:"+name+"\t proxyBean is not register.");
        }
        return (ProxyBean<T>) proxyBean;
    }

    /**
     * 获取需要被代理类的列表
     * @return
     */
    public Map<String, Class<?>> getBeans() {
        return beans;
    }

    /**
     * 批量注册
     * @param classes
     */
    public void registerBeans(Class<?>... classes){
        for (Class<?> aClass : classes) {
            registerBean(aClass);
        }
    }
    /**
     * 注册bean类，允许覆盖，使用类全限定名作为name
     * @param clazz 类模板
     */
    public void registerBean(Class<?> clazz){
        //先获取注解
        RmqServer annotation = clazz.getAnnotation(RmqServer.class);
        String name=null;
        if (annotation!=null){
           name =  annotation.serviceName();
        }
        //如果注解不存在name，则使用全限定类名作为name
        if (name==null|| StringUtils.isBlank(name)){
            name = clazz.getName();
        }
        //注册类
        addBean(name,clazz);
    }
    /**
     * 注册bean类，允许覆盖
     * @param name 注册名称
     * @param clazz 类
     */
    protected void addBean(String name,Class<?> clazz){
        Class<?> aClass = beans.get(name);
        //重复则抛出异常
        if (aClass!=null){
            String exc = System.out.format("duplicate name while registerBean:\n {}:{}\n {}:{}\n",name,clazz.getName(),name,aClass.getName()).toString();
            throw new RuntimeException(exc);
        }
        beans.put(name,clazz);
    }

    /**
     * 注册代理后的对象
     * @param proxy
     */
    public<T> void addProxyBean(String name,ProxyBean<?> proxy){
        proxyBeans.put(name,proxy);
        proxyForName.put(proxy.getBean(),name);
    }

    public void setEncodeFactory(){

    }

    public RmqConfig getRmqConfig() {
        return rmqConfig;
    }

    public void setRmqConfig(RmqConfig rmqConfig) {
        this.rmqConfig = rmqConfig;
    }

    public RmqClient getRmqClient() {
        return rmqClient;
    }

    public void setRmqClient(RmqClient rmqClient) {
        this.rmqClient = rmqClient;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
