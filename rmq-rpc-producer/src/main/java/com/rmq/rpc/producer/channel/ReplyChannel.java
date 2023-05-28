/*
 * @Author: jilin jilin_cq@163.com
 * @Date: 2023-05-28 10:19:21
 * @LastEditors: jilin jilin_cq@163.com
 * @LastEditTime: 2023-05-28 16:02:05
 * @FilePath: \rabbmitmq-rpc\rmq-rpc-producer\src\main\java\com\rmq\rpc\producer\channel\ReplyChannel.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */

package com.rmq.rpc.producer.channel;

import com.rabbitmq.client.AMQP.BasicProperties;
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
            BasicProperties properties = new BasicProperties().builder().replyTo(replyQueue).build(); 
            channel.basicPublish(EXCHANGE_NAME, serviceName, properties, message.getBytes(StandardCharsets.UTF_8));
            GetResponse getResponse = null;
            while (getResponse == null) {
                getResponse = channel.basicGet(properties.getReplyTo(), true);
            }
            return new String(getResponse.getBody(),StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RmqException("send rpc mq error.",e);
        }
    }
}
