package com.rmq.rpc.producer.channel;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.exception.RmqException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 带回复队列的channel
 * @date 2023/5/22 17:20:56
 */
public class ReplyChannel {
    private final Channel channel;

    private final String replyQueue;

    private final String EXCHANGE_NAME;

    Configuration configuration;

    public ReplyChannel(Configuration configuration,Channel channel, String replyQueue,String exchangeName) {
        this.channel = channel;
        this.replyQueue= replyQueue;
        this.EXCHANGE_NAME = exchangeName;
        this.configuration = configuration;
    }

    /**
     * 执行rpc方法
     * @param serviceName 目标服务id
     * @param message 发送信息
     * @return 返回json字符串
     */
    public String excuteRpc(String serviceName,String message){

        try {
            new AMQP().
            channel.basicPublish(EXCHANGE_NAME, serviceName, properties, message.getBytes(StandardCharsets.UTF_8));
            GetResponse getResponse = null;
            while (getResponse == null) {
                getResponse = channel.basicGet(properties.getReplyTo(), true);
            }
            return new String(getResponse.getBody());
        } catch (IOException | CloneNotSupportedException e) {
            throw new RmqException("send rpc mq error.",e);
        }
    }
}
