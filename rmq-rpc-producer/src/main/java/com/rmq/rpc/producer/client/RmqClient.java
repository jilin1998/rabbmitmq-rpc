package com.rmq.rpc.producer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rmq.rpc.common.mq.Encoder;
import com.rmq.rpc.common.mq.json.Param;
import com.rmq.rpc.common.utils.TopicUtil;
import com.rmq.rpc.producer.channel.ReplyChannel;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.exception.RmqException;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description mq连接客户端
 * @date 2023/5/22 14:34:05
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
    private ReplyChannel channel;
    /**
     * 线程池
     */
    private  ExecutorService service;
    /**
     * 连接池生成工厂
     */
    private ConnectionFactory factory;

    public RmqClient(Configuration configuration, ConnectionFactory factory) {
        this.configuration = configuration;
        this.factory = factory;
        //初始化信道
        initChannel();
    }

    private void initThreadPool(){
        service = new ThreadPoolExecutor(0, 0, null, null)
    }

    /**
     * 初始化信道
     */
    public ReplyChannel initChannel() {
        String clusterName = configuration.getRmqConfig().getClusterName();
        try {
            Channel channel = connection.createChannel();
            //给channel绑定交换机
            channel.exchangeDeclare(TopicUtil.generateExchange(clusterName), "topic");
            //回复队列
            String queName = TopicUtil.generateReplyTopic(configuration.getRmqConfig().getQueueName());
            String replyQueue =channel.queueDeclare(queName,true,true,false,new HashMap<>()).getQueue();
            AMQP.BasicProperties properties =   new AMQP.BasicProperties().builder()
                    .replyTo(replyQueue)
                    .build();
            //构建回复channel
            return new ReplyChannel(configuration,channel,replyQueue,clusterName+"_topics");
        } catch (IOException e) {
            throw new RmqException("init Channel error.", e.getCause());
        }
    }

    /**
     * 调用封装的channle，执行rpc
     * @param serviceName
     * @param param
     * @return
     * @throws JsonProcessingException
     */
    public String excuteRpc(String serviceName, Param param) throws JsonProcessingException {
        Encoder encoder = configuration.getEncoder();
        String paramJson = encoder.buildJson(param);
        return channel.excuteRpc(serviceName,paramJson);
    }
}
