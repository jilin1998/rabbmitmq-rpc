package com.rmq.rpc.common.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description
 * @date 2023/5/22 14:52:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface RmqServer {
    /**
     * 服务名称
     * @return
     */
    String serviceName() default "";

    /**
     * 版本号
     * @return
     */
    String vesion() default "1.0.0";
}
