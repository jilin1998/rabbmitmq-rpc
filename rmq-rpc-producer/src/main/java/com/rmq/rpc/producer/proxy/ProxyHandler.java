package com.rmq.rpc.producer.proxy;

import com.rmq.rpc.producer.client.RmqClient;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.mq.EncodeFactorySelector;
import com.rmq.rpc.producer.mq.Encoder;
import com.rmq.rpc.producer.mq.ParseFactorySelector;
import com.rmq.rpc.producer.mq.Parser;
import com.rmq.rpc.producer.mq.json.Param;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 代理对象
 * @date 2023/5/22 14:37:46
 */
public class ProxyHandler implements InvocationHandler {
    /**
     * 全局配置
     */
    private final Configuration configuration;

    public ProxyHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //跳过Object.class的方法
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this,args);
        }
        //获取到对应的client来执行方法
        RmqClient client = configuration.getRmqClient();
        //构造数据
        //获取目标服务id
        String serviceName = configuration.getServiceName(proxy);

        //选择工厂
        //TODO 后续通过配置文件指定序列化方式
        Encoder encoder = configuration.getEncoder();
        Parser parser = configuration.getParser();

        Param param = encoder.buildParam(serviceName,configuration.getRmqConfig().getEncodeType(),method, args);
        //通过client发送rpc
        String result = client.excuteRpc(serviceName, param);

        //构造参数
        return parser.parse(result,method.getReturnType());
    }
}
