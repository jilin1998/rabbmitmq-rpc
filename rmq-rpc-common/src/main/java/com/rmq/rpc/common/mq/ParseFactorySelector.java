package com.rmq.rpc.common.mq;

import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.common.mq.paser.JsonParser;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 15:53:34
 */
public class ParseFactorySelector {

    public static Parser selectFactory(EncodeTypeEnum typeEnum){
        switch (typeEnum){
            case JSON:
                return new JsonParser();
            default:
                throw new RuntimeException("illegal encode type.");
        }
    }
}
