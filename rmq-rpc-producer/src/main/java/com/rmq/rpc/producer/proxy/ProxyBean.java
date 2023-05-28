
package com.rmq.rpc.producer.proxy;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 存储代理后的对象
 * @date 2023/5/22 17:40:22
 */
public class ProxyBean<T> {
    /**
     * 服务名称
     */
    private String name;

    private T bean;
    /**
     * 类模板
     */
    private Class clazz;

    public ProxyBean(String name, T bean,Class clazz) {
        this.name = name;
        this.bean = bean;
        this.clazz = clazz;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }



    public Class<T> getClazz() {
        return clazz;
    }



    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
