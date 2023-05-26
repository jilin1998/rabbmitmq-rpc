package com.rmq.rpc.consumer.instance.impl;

import com.rmq.rpc.common.exception.RmqException;
import com.rmq.rpc.consumer.instance.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description 借助spring实例化，直接从spring容器取出bean
 * @date 2023/5/26 11:08:15
 */
public class SpringInstanceFactory implements InstanceFactory {

    private final Logger log = LoggerFactory.getLogger(SpringInstanceFactory.class);
   private final   ApplicationContext applicationContext;

    /**
     * 初始化springInstace必须传入spring上下文
     * @param applicationContext spring上下文
     */
    public SpringInstanceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T instanceRegisterBeans(Class<T> clazz) {
        try{
            return applicationContext.getBean(clazz);
        }catch (BeansException e){
            String err = System.out.format("bean: {} not register in spring.",clazz.getName()).toString();
            throw new RmqException(err,e);
        }
    }
}
