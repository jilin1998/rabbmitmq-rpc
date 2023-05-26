package com.rmq.rpc.consumer.forward;

import com.rmq.rpc.common.mq.json.Param;

/**
 * @author jilin
 * @description 数据转发接口
 * @date 2023年5月25日-17:55:03
 */
public interface Forward {
    /**
     * 数据转发接口
     * @param <T> 返回的数据类型
     * @param clazz 返回的类模板
     * @return
     */
    <T> T forwardWithReturn(Param param,Class<T> clazz);
    /**
     * 数据转发接口，无需返回值
     * @param json 
     */
    void forward(String json);
    
}
