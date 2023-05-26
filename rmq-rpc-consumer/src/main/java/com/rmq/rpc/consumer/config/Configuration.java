package com.rmq.rpc.consumer.config;

import com.rmq.rpc.common.annotations.RmqServer;
import com.rmq.rpc.common.exception.RmqException;
import com.rmq.rpc.common.mq.Encoder;
import com.rmq.rpc.common.mq.Parser;
import com.rmq.rpc.common.utils.AnnotationUtils;
import com.rmq.rpc.consumer.client.RmqClient;
import com.rmq.rpc.consumer.forward.Forward;
import com.rmq.rpc.consumer.forward.impl.ForwardMethodImpl;
import com.rmq.rpc.consumer.instance.InstanceFactory;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/24 09:48:01
 */
public class Configuration {

    /**
     * 存储服务名和class的映射
     */
    private Map<String,Class<?>> serviceNameClass = new ConcurrentHashMap<>();
    /**
     * 存储服务名和实例化类的映射
     */
    private Map<String,Object> serviceNameBean = new ConcurrentHashMap<>();
    /**
     * mq启动配置信息
     */
    RmqConsumerConfig consumerConfig;
    /**
     * 编码器
     */
    Encoder encoder;
    /**
     * 解码器
     */
    Parser parser;
    /**
     * 客户端
     */
    RmqClient rmqClient;

    /**
     * 实例化策略
     */
    List<InstanceFactory> factories = new ArrayList<>();
    /**
     * 转发内容
     */
    Forward forward;

    public Configuration() {
        createForward();
    }

    /**
     * 创建转发器
     */
    public void createForward(){
        this.forward = new ForwardMethodImpl(this);
    }

    /**
     * 添加的链式操作
     * @param factory
     */
    public void addFactoryChain(InstanceFactory factory){
        factories.add(factory);
    }

    /**
     * 批量注册
     * @param classes
     */
    public void registerClasses(Class<?>... classes){
        for (Class<?> clazz : classes) {
            if (!clazz.isInterface()){
                registerClass(clazz);
            }
        }
    }

    /**
     * 添加类模板 不允许覆盖
     * @param clazz 
     */
    public void registerClass(Class<?> clazz){
        if (clazz.isInterface()){
            return;
        }
        //获取类上面的注释信息
        RmqServer annotation = AnnotationUtils.findAnnotation(clazz,RmqServer.class);
        String name = null;
        if (annotation!=null) {
            name = annotation.serviceName();
        }
        //如果注解为空可以使用类名注册，为手动注册不加注解兜底
        if(StringUtils.isBlank(name)){
            name = clazz.getName();
        }
        //添加
        addClass(name, clazz);
    }
    /**
     * 添加类模板
     * @param serviceName
     * @param clazz
     */
    public void addClass(String serviceName,Class<?> clazz){
        Class<?> absent = serviceNameClass.putIfAbsent(serviceName,clazz);
        //不等则说明存在同名不同类，打断启动过程
        if(absent!=null&&!clazz.equals(absent)){
            String format = String.format("duplicate name while registerBean:\n %s:%s\n %s:%s\n", serviceName, clazz.getName(), serviceName, absent.getName());
            throw new RmqException(format);
        }
    }

    /**
     * 获取类模板
     * @param serviceName
     * @return
     */
    public Class<?> getClazzByService(String serviceName){
        return serviceNameClass.get(serviceName);
    }

    /**
     * 获取bean
     * @param service
     * @param clazz
     * @param <T>
     * @return
     */
    public<T> T getBeanByService(String service,Class<T> clazz){
        return (T) serviceNameBean.get(service);
    }

    /**
     * 注册bean
     * @param serviceName
     * @param bean
     */
    public void registerBean(String serviceName,Object bean){
        serviceNameBean.put(serviceName,bean);
    }


    public RmqConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(RmqConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
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

    public RmqClient getRmqClient() {
        return rmqClient;
    }

    public void setRmqClient(RmqClient rmqClient) {
        this.rmqClient = rmqClient;
    }

    /**
     * @return the serviceNameClass
     */
    public Map<String, Class<?>> getServiceNameClass() {
        return serviceNameClass;
    }

    /**
     * @param serviceNameClass the serviceNameClass to set
     */
    public void setServiceNameClass(Map<String, Class<?>> serviceNameClass) {
        this.serviceNameClass = serviceNameClass;
    }

    /**
     * @return the serviceNameBean
     */
    public Map<String, Object> getServiceNameBean() {
        return serviceNameBean;
    }

    /**
     * @param serviceNameBean the serviceNameBean to set
     */
    public void setServiceNameBean(Map<String, Object> serviceNameBean) {
        this.serviceNameBean = serviceNameBean;
    }

    /**
     * @return the factories
     */
    public List<InstanceFactory> getFactories() {
        return factories;
    }

    /**
     * @param factories the factories to set
     */
    public void setFactories(List<InstanceFactory> factories) {
        this.factories = factories;
    }


    public Forward getForward() {
        return forward;
    }

    public void setForward(Forward forward) {
        this.forward = forward;
    }
}
