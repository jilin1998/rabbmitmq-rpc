package com.rmq.rpc.common.mq.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmq.rpc.common.json.JacksonFactory;
import com.rmq.rpc.common.mq.Encoder;
import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description 使用rpc序列化和反序列化
 * @date 2023/5/23 15:01:08
 */
public class JsonEncoder implements Encoder {
    Logger log = LoggerFactory.getLogger(JsonEncoder.class);
    /**
     * jackson工厂
     */
    JacksonFactory jacksonFactory = new JacksonFactory();

    @Override
    public Param buildParam(String serviceName, EncodeTypeEnum encodeType, Method method, Object... args)  {
        //构建json
        Param param = new Param(serviceName, encodeType,method.getName(),method.getReturnType().getName());
        try{
            for (Object arg : args) {
                String argJson = jacksonFactory.serialize(arg);
                Class<?> aClass = arg.getClass();
                ParamItem item = new ParamItem();
                item.setClassName(aClass.getName());
                item.setJson(argJson);
                param.addParamItes(item);
            }
        } catch (JsonProcessingException e) {
            log.error("buildParam serialize is error.",e);
        }
        return param;
    }

    @Override
    public String buildJson(Object jsonObject) {
        String json = null;
        try{
            json  =jacksonFactory.serialize(jsonObject);
        } catch (JsonProcessingException e) {
            log.error("serialize is error.",e);
        }
        return json;
    }

}
