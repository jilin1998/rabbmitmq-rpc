package com.rmq.rpc.consumer.chain.wrapper;

import com.rmq.rpc.consumer.instance.InstanceFactory;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description 责任链包装类, 将实例化工厂包装
 * @date 2023/5/26 11:37:44
 */
public class ChainWrapper {

    private final InstanceFactory factory;
    /**
     * 下一个元素指针
     */
    private ChainWrapper next;

    public ChainWrapper(InstanceFactory factory) {
        this.factory = factory;
    }

    /**
     * 获取元素
     *
     * @param next
     */
    public void setNext(ChainWrapper next) {
        this.next = next;
    }

    /**
     * 是否有下一个元素
     * @return
     */
    public boolean hasNext(){
        return next!=null;
    }

    public ChainWrapper next() {
        return this.next;
    }

    /**
     * 链式操作
     * @param clazz
     * @param <T>
     * @return
     */
    public<T> T doChain(Class<T> clazz){
        if (!hasNext()){
            return null;
        }
        return this.next.chain(clazz);
    }

    /**
     * 链式执行
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T chain(Class<T> clazz) {
        T bean = factory.instanceRegisterBeans(clazz);
        if (bean==null){
            //下一个实例
            if (hasNext()){
               bean = this.doChain(clazz);
            }
        }
        return bean;
    }
}
