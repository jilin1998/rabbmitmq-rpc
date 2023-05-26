package com.rmq.rpc.common.utils;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/25 10:54:21
 */
public class TopicUtil {
    public static String Exchange_SUFFIX = "_topics";

    public static String TOPIC_REPLY_SUFFIX="_reply";

    /**
     * 生成rpc调用exchange名称
     * @param name
     * @return
     */
    public static String generateExchange(String name){
        return name+Exchange_SUFFIX;
    }

    /**
     * 生成回复队列
     * @param name
     * @return
     */
    public static String generateReplyTopic(String name){
        return name+TOPIC_REPLY_SUFFIX;
    }

}
