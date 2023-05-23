package com.rmq.rpc.producer.factory;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rmq.rpc.producer.client.RmqClient;
import com.rmq.rpc.producer.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description mq客户端建立工厂
 * @date 2023/5/22 15:40:33
 */
public class ClientFactory {
    private final Logger log = LoggerFactory.getLogger(ClientFactory.class);
    private final Configuration configuration;

    public ClientFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 开启连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public RmqClient connection() throws IOException, TimeoutException {
        log.info("connection to rabmitMQ......");
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq
        factory.setHost(configuration.getRmqConfig().getAddress());
        factory.setPort(configuration.getRmqConfig().getPort());
        factory.setUsername(configuration.getRmqConfig().getUsername());
        factory.setPassword(configuration.getRmqConfig().getPassword());
        factory.setConnectionTimeout(configuration.getRmqConfig().getConnectionTimeOut());
        factory.setAutomaticRecoveryEnabled(true);
        factory.setVirtualHost(configuration.getRmqConfig().getvHost());
        //创建一个新连接
        Connection connection = factory.newConnection();
        log.info("connection rabmitMQ is success");
        //返回自定义封装的客户端
        return new RmqClient(configuration,connection);
    }
}
