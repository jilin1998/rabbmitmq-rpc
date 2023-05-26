package com.rmq.rpc.consumer.app;

import cn.hutool.core.lang.ClassScaner;
import com.rmq.rpc.common.exception.RmqException;
import com.rmq.rpc.common.mq.EncodeFactorySelector;
import com.rmq.rpc.common.mq.Encoder;
import com.rmq.rpc.common.mq.ParseFactorySelector;
import com.rmq.rpc.common.mq.Parser;
import com.rmq.rpc.consumer.chain.InstanceChain;
import com.rmq.rpc.consumer.client.RmqClient;
import com.rmq.rpc.consumer.config.Configuration;
import com.rmq.rpc.consumer.config.RmqConsumerConfig;
import com.rmq.rpc.consumer.factory.ClientFactory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * @author jilin
 * @description
 * @date 2023年5月26日-10:52:28
 */
public class RmqConsumer {
    /**
     * 全局配置文件
     */
    private final Configuration configuration;

    /**
     * 使用类直接注册
     * 
     * @param configuration
     * @param rmqConfig
     * @param classes
     */
    public RmqConsumer(Configuration configuration, RmqConsumerConfig rmqConfig, Class<?>... classes) {
        this.configuration = configuration;
        configuration.setConsumerConfig(rmqConfig);
        //构建转发器

        // 批量注册
        registerClasses(classes);
    }

    public RmqConsumer(Configuration configuration, RmqConsumerConfig rmqConfig, String packages) {
        this.configuration = configuration;
        configuration.setConsumerConfig(rmqConfig);
        // 使用包名扫描注册
        registerPackage(packages);
    }

    /**
     * 包名扫描注册
     * 
     * @param packages
     */
    public void registerPackage(String packages) {
        Set<Class<?>> classes = scanPackages(packages);
        for (Class<?> aClass : classes) {
            registerClass(aClass);
        }
    }

    /**
     * 注册类信息
     * 
     * @param classes
     */
    public void registerClasses(Class<?>... classes) {
        configuration.registerClasses(classes);
    }

    /**
     * 注册单个类，为后续容器刷新和单个注册留接口
     * 
     * @param clazz
     */
    public void registerClass(Class<?> clazz) {
        configuration.registerClass(clazz);
    }

    /**
     * 利用hutool扫描类
     * 
     * @param packages
     * @return
     */
    public Set<Class<?>> scanPackages(String packages) {
        return ClassScaner.scanPackage(packages);
    }

    /**
     * 启动
     */
    public void start() {
        // 构建解码器
        Parser parser = ParseFactorySelector.selectFactory(configuration.getConsumerConfig().getEncodeType());
        // 构建编码器
        Encoder encoder = EncodeFactorySelector.selectFactory(configuration.getConsumerConfig().getEncodeType());
        configuration.setParser(parser);
        configuration.setEncoder(encoder);
        // 实例化类
        instace();
        // 启动mq
        ClientFactory clientFactory = new ClientFactory(configuration);
        try {
            RmqClient client = clientFactory.connection();
            configuration.setRmqClient(client);
        } catch (IOException | TimeoutException e) {
            throw new RmqException("connect rabbitmq is failed.", e);
        }
    }

    /**
     * 调用实例化工厂
     */
    private void instace() {
        InstanceChain instanceChain = new InstanceChain(configuration, configuration.getFactories());
        //开始实例化
        instanceChain.instance();
    }

}
