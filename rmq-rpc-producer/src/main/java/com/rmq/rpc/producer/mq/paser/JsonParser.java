package com.rmq.rpc.producer.mq.paser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmq.rpc.producer.json.JacksonFactory;
import com.rmq.rpc.producer.mq.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 15:53:08
 */
public class JsonParser implements Parser {

    private Logger log = LoggerFactory.getLogger(JsonParser.class);
    /**
     * json格式化工厂
     */
    JacksonFactory jacksonFactory = new JacksonFactory();

    @Override
    public <R, T> R parse(T response, Class<R> clazz) {
        try{
            return jacksonFactory.parseForType((String) response,clazz);
        } catch (JsonProcessingException e) {
            log.error("parse is error.",e);
        }
        return null;
    }

}
