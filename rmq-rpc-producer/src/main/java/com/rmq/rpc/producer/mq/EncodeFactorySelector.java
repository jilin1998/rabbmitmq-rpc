package com.rmq.rpc.producer.mq;

import com.rmq.rpc.producer.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.producer.mq.json.JsonEncoder;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description 编码工厂选择器
 * @date 2023/5/23 15:22:31
 */
public class EncodeFactorySelector {

    public static Encoder selectFactory(EncodeTypeEnum typeEnum){
        switch (typeEnum){
            case JSON:
                return new JsonEncoder();
            default:
                throw new RuntimeException("illegal encode type.");
        }
    }
}
