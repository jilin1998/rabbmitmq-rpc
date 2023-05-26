package com.rmq.rpc.consumer.channel;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rmq.rpc.common.mq.json.Param;
import com.rmq.rpc.consumer.config.Configuration;
import com.rmq.rpc.consumer.forward.Forward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/25 11:40:39
 */
public class ConsumerChannel {

	private final Logger log = LoggerFactory.getLogger(ConsumerChannel.class);

	/**
	 * 信道
	 */
	private final Channel channel;
	/**
	 * 交换机，可暂不设置
	 */
	private final String EXCHANGE_NAME;

	/**
	 * 全局配置文件
	 */
	final Configuration configuration;
	/**
	 * 消费实现类
	 */
	final DefaultConsumer consumer;

	/**
	 * @param channel       通道
	 * @param exchangeName 队列名称
	 * @param configuration 全局配置文件
	 */
	public ConsumerChannel(Channel channel, String exchangeName, Configuration configuration) {
		this.channel = channel;
		EXCHANGE_NAME = exchangeName;
		this.configuration = configuration;
		this.consumer = new ReplyConsumer(channel);
		//设置自动回
	}

	public DefaultConsumer getConsumer() {
		return consumer;
	}

	/**
	 * 内置消费者
	 * description
	 *
	 * @author jilin
	 *         createTime 2023年5月25日-下午3:29:49
	 */
	class ReplyConsumer extends DefaultConsumer {


		/**
		 * 父类构造
		 * 
		 * @param channel
		 */
		public ReplyConsumer(Channel channel) {
			super(channel);
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {
			// 转换成utf-8 json
			String message = new String(body, StandardCharsets.UTF_8);
			// 转发消息给处理器
			Forward forward = configuration.getForward();
			//寻找返回类型
			Param param = configuration.getParser().parse(message, Param.class);
			Class<?> clazz = className(param.getReturnClassName());
			Object o = forward.forwardWithReturn(param, clazz);
			//回复
			this.getChannel().basicPublish("",properties.getReplyTo(),properties,encode(o));
		}

		/**
		 * 根据类名搜索
		 * @param name
		 * @return
		 */
		public Class<?> className(String name){
			try{
				Class<?> clazz = Class.forName(name);
			} catch (ClassNotFoundException e) {
				log.error("serach class not found.{}",name);
			}
			return null;
		}

		public byte[] encode(Object o){
		 return configuration.getEncoder().buildJson(o).getBytes(StandardCharsets.UTF_8);
		}

	}

}
