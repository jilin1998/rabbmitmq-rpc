package com.rmq.rpc.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 15:59:13
 */
public class JacksonFactory implements Factory{
    private Logger log = LoggerFactory.getLogger(JacksonFactory.class);
    final ObjectMapper mapper;


    public JacksonFactory() {
        this.mapper = new ObjectMapper();
    }

    /**
     * 反序列化
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public<T> T parseForType(String json,Class<T> clazz) throws JsonProcessingException {
        try{
           return mapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            log.error("jackson parse for {} is error",clazz.getName());
            throw e;
        }
    }

    /**
     * 序列化
     * @param o
     * @return
     */
    public String serialize(Object o) throws JsonProcessingException {
        try{
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("jackson serialize for {} is error",o.getClass().getName());
            throw e;
        }
    }
}
