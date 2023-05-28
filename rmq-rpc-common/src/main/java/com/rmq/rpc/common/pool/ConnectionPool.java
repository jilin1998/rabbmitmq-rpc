package com.rmq.rpc.common.pool;
/**
 * rabbitmq连接池
 */

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    /**
     * 核心数
     */
    private final int coreSize;
    /**
     * 最大连接数
     */
    private final int maxSize;
    /**
     * 保持空闲连接数
     */
    private final long keepAliveTime;
    /**
     * 超时单位
     */
    private final TimeUnit unit;
    /**
     * 线程池创造工厂
     */
    private final ThreadFactory factory;
    /**
     * 拒绝策略
     */
    private final RejectedExecutionHandler handler; 
    
}
