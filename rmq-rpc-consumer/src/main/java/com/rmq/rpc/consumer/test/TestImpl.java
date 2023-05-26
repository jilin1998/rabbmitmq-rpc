package com.rmq.rpc.consumer.test;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/26 16:06:56
 */
public class TestImpl implements Test{
    @Override
    public String send(String msg) {
        System.out.println("测试");
        return msg;
    }
}
