package com.rmq.rpc.producer.test;

import com.rmq.rpc.common.annotations.RmqServer;

@RmqServer(serviceName = "test1")
public interface Test1 {
    public String send(String msg);
}
