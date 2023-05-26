package com.rmq.rpc.common.mq;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description 应答解析器
 * @date 2023/5/23 15:53:44
 */
public interface Parser {
    /**
     * 解析入口
     * @param response
     * @param clazz
     * @return
     */
    <R,T extends Object> R parse(T response,Class<R> clazz);
}
