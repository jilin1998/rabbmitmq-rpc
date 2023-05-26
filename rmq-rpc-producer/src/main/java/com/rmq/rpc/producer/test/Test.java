package com.rmq.rpc.producer.test;

import com.rmq.rpc.common.annotations.RmqServer;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description
 * @date 2023/5/22 16:31:49
 */
@RmqServer(serviceName = "test")
public interface Test {

    public String send(String msg);
}
