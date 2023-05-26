package com.rmq.rpc.consumer.chain;

import com.rmq.rpc.common.exception.RmqException;
import com.rmq.rpc.consumer.chain.wrapper.ChainWrapper;
import com.rmq.rpc.consumer.config.Configuration;
import com.rmq.rpc.consumer.instance.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/26 11:37:30
 */
public class InstanceChain {
    private final Logger log = LoggerFactory.getLogger(InstanceChain.class);
    final ChainWrapper head;
    /**
     * 全局配置
     */
    private final Configuration configuration;
    public InstanceChain(Configuration configuration,List<InstanceFactory> factories) {
        this.configuration = configuration;
        if (factories==null||factories.size()==0){
            throw new RmqException("InstanceFatory can not empty");
        }
        //先构建头节点
        head = new ChainWrapper(factories.get(0));
        link(factories);
    }
    /**
     * 构建责任链
     * @param factories
     */
    private void link(List<InstanceFactory> factories){
        ChainWrapper pre = head;
        for (int i = 1; i < factories.size(); i++) {
            InstanceFactory factory = factories.get(i);
            ChainWrapper item = new ChainWrapper(factory);
            //构建后续节点
            pre.setNext(pre);
            pre = item;
        }
    }
    /**
     * 实例化过程
     */
    public void instance(){
        Set<String> serviceNames = configuration.getServiceNameClass().keySet();
        for (String serviceName : serviceNames) {
            Class<?> clazz = configuration.getClazzByService(serviceName);
            //实例化
            Object bean = doChain(clazz);
            if(bean==null){
                throw new RmqException("instance "+clazz.getName()+"is faild. instanceObject is null");
            }
            //注册实例化后的bean
            configuration.registerBean(serviceName, bean);
            log.info("instance bean : {} is success.",clazz.getSimpleName());
        }
    }

    /**
     * 链式实例化
     * @param clazz
     * @param <T>
     * @return
     */
   private<T> T doChain(Class<T> clazz){
        return startChain(clazz);
   }

    /**
     * 开始链式实例化
     * @param clazz
     * @param <T>
     * @return
     */
   public  <T> T startChain(Class<T> clazz){
       if (head==null){
           throw new RmqException("not register instanceFactory.");
       }
       return head.chain(clazz);
   }
}
