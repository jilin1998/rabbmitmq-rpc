package com.rmq.rpc.producer.mq;

import com.rmq.rpc.producer.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.producer.mq.json.Param;

import java.lang.reflect.Method;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 15:01:53
 */
public interface Encoder {
    /**
     * 根据方法和入参构建rpc参数
     * @param method
     * @param args
     * @return
     */
    Param buildParam(String serviceName, EncodeTypeEnum encodeType, Method method, Object...args);

}
