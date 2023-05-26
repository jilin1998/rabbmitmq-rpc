package com.rmq.rpc.consumer.BeanCache;

import com.rmq.rpc.consumer.config.Configuration;

/**
 * @author jilin
 * @description
 * @date 2023年5月26日-09:31:58
 */
public class BeanCacheFactory {
    /**
     * 全局配置
     */
    private Configuration configuration;

    /**
     * @param configuration
     * 构造方法
     */
    public BeanCacheFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    
    
}
