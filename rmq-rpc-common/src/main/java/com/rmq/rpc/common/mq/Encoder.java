package com.rmq.rpc.common.mq;

import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.common.mq.json.Param;

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

    /**
     * 序列化对象成json
     * @param jsonObject
     * @return
     */
    String buildJson(Object jsonObject);
}
