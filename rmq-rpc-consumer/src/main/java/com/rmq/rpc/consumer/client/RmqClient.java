package com.rmq.rpc.consumer.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rmq.rpc.common.exception.RmqException;
import com.rmq.rpc.common.utils.TopicUtil;
import com.rmq.rpc.consumer.channel.ConsumerChannel;
import com.rmq.rpc.consumer.config.Configuration;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/25 11:36:47
 */
public class RmqClient {
    private final Configuration configuration;
    /**
     * 当前客户端对应的实例
     */
    private final Connection connection;
    /**
     * 信道，后续会改成线程池
     */
    private ConsumerChannel channel;

    public RmqClient(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
        //初始化信道
       initChannel();
    }

    /**
     * 初始化信道
     */
    public void initChannel() {
        String clusterName = configuration.getConsumerConfig().getClusterName();
        try {
            String exhangeName = TopicUtil.generateExchange(clusterName);
            Channel channel = connection.createChannel();
            //给channel绑定交换机
            channel.exchangeDeclare(exhangeName, "topic");
            //接收队列
            String queName = configuration.getConsumerConfig().getQueueName();
            channel.queueDeclare(queName,false,true,false,new HashMap<>());
            //绑定queue
            channel.queueBind(queName, exhangeName, configuration.getConsumerConfig().getAppId());
            //构建回复channel
            this.channel = new ConsumerChannel(channel,exhangeName,configuration);
            //设置消费自动确认
            channel.basicConsume(queName,true,this.channel.getConsumer());
        } catch (IOException e) {
            throw new RmqException("init Channel error.", e.getCause());
        }
    }


}
