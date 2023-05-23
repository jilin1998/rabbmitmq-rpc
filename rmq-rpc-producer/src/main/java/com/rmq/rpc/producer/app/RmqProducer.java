package com.rmq.rpc.producer.app;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.ClassScaner;
import com.rmq.rpc.producer.annotations.RmqServer;
import com.rmq.rpc.producer.client.RmqClient;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.config.RmqConfig;
import com.rmq.rpc.producer.exception.RmqException;
import com.rmq.rpc.producer.factory.ClientFactory;
import com.rmq.rpc.producer.factory.ProxyFactory;
import com.rmq.rpc.producer.mq.EncodeFactorySelector;
import com.rmq.rpc.producer.mq.ParseFactorySelector;
import com.rmq.rpc.producer.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.producer.proxy.ProxyBean;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 程序手动启动入口
 * @date 2023/5/22 14:33:31
 */
public class RmqProducer {
    /**
     * 启动时创建配置类
     */
    private final Configuration configuration;


    public RmqProducer(Configuration configuration,RmqConfig rmqConfig,String packages) {
        this.configuration = configuration;
        configuration.setRmqConfig(rmqConfig);
        //注册类
        this.registerBeans(packages);
    }

    /**
     * 启动时注册，可以不需要使用注解
     * @param classes
     */
    public RmqProducer(Configuration configuration,RmqConfig rmqConfig,Class<?>... classes){
        this.configuration = configuration;
        configuration.setRmqConfig(rmqConfig);
        //注册类
        configuration.registerBeans(classes);
    }
    private void checkConfig(){
        RmqConfig rmqConfig = configuration.getRmqConfig();
        if (StringUtils.isBlank(rmqConfig.getClusterName())){
            throw new RmqException("clusterName is null");
        }
        if (StringUtils.isBlank(rmqConfig.getAppId())){
            throw new RmqException("appId is null");
        }
        if (rmqConfig.getEncodeType()==null){
            throw new RmqException("encodingType is null or illegal");
        }
    }
    /**
     * 开启服务
     */
    public void start(){
        checkConfig();
        //构建编码和解码器
        configuration.setEncoder(EncodeFactorySelector.selectFactory(configuration.getRmqConfig().getEncodeType()));
        configuration.setParser(ParseFactorySelector.selectFactory(configuration.getRmqConfig().getEncodeType()));
        //使用工厂构建代理类
        ProxyFactory factory = new ProxyFactory(configuration);
        factory.proxy();
        //开启mq服务
        ClientFactory clientFactory = new ClientFactory(configuration);
        try{
            RmqClient client = clientFactory.connection();
            configuration.setRmqClient(client);
        } catch (IOException|TimeoutException e) {
            throw new RmqException("init connection is error.",e);
        }
    }

    /**
     * 根据包名注册bean
     * @param packages
     */
    public void registerBeans(String packages){
        Set<Class<?>> classNames = getClassNames(packages);
        for (Class<?> className : classNames) {
            configuration.registerBean(className);
        }
    }

    /**
     * 扫描包下的所有类
     * @param packageName
     */
    private Set<Class<?>> getClassNames(String packageName){
        return ClassScaner.scanPackage(packageName);
    }

    /**
     * 获取被代理的类
     * @param clazz
     * @param <T>
     * @return
     */
    public<T> T  getProxyBean(Class<T> clazz){
        String name = null;
        //先根据注解获取name
        RmqServer annotation = clazz.getAnnotation(RmqServer.class);
        if (annotation!=null){
            name = annotation.serviceName();
        }
        if (name==null|| StringUtils.isBlank(name)){
            name = clazz.getName();
        }
        //获取代理后的bean
        return configuration.getProxyBean(name,clazz).getBean();
    }
}
