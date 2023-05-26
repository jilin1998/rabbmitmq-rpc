package com.rmq.rpc.consumer.instance;


/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description 实例化类的工厂 ,可实现此接口，实现自定义实例化策略。可进行类过滤，不是实例化直接返回null，由com.rmq.rpc.consumer.instance.impl兜底
 * @date 2023/5/26 11:05:12
 */
public interface InstanceFactory {
    /**
     * 实例化方法
     * @param clazz
     */
    <T> T instanceRegisterBeans(Class<T> clazz);
}
