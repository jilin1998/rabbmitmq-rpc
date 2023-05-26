package com.rmq.rpc.common.exception;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description 自定义异常类
 * @date 2023/5/22 15:05:35
 */
public class RmqException extends RuntimeException{
    public RmqException() {
    }

    public RmqException(String message) {
        super(message);
    }


    public RmqException(String message, Throwable cause) {
        super(message, cause);
    }
}
