package com.rmq.rpc.producer.factory;

import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.proxy.ProxyBean;
import com.rmq.rpc.producer.proxy.ProxyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 接口代理工厂
 * @date 2023/5/22 14:29:43
 */
public class ProxyFactory {
    private final Logger log = LoggerFactory.getLogger(ProxyFactory.class);
   private final Configuration configuration;

    public ProxyFactory(Configuration configuration) {
        this.configuration = configuration;
    }
    /**
     * 生成代理类注入
     */
    public void proxy(){
        Map<String, Class<?>> beans = configuration.getBeans();
        Set<String> keySet = beans.keySet();
        for (String name : keySet) {
            Class<?> aClass = beans.get(name);
            Object proxyInstance =  Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{aClass}, new ProxyHandler(configuration));
            ProxyBean<?> proxyBean = new ProxyBean<>(name,proxyInstance,aClass);
            configuration.addProxyBean(name,proxyBean);
            log.info("{}.class is register by proxy",aClass.getSimpleName());
        }
        log.info("all bean is proxy complete.");
    }
}
