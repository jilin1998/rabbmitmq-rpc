package com.rmq.rpc.common.json;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description 工厂接口
 * @date 2023/5/23 16:30:11
 */
public interface Factory {
    /**
     * 解码
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T parseForType(String json,Class<T> clazz) throws Exception;

    /**
     * 编码
     * @param o
     * @return
     * @throws Exception
     */
    String serialize(Object o) throws Exception;
}
