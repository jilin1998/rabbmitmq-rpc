package com.rmq.rpc.consumer.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/24 09:48:47
 */
@ConfigurationProperties(prefix = "rmq.consumer")
public class RmqConsumerConfig {
    /**
     * 当前服务的appId
     */
    private String appId;
    /**
     * 解码格式
     */
    private EncodeTypeEnum encodeType;

    /**
     * mq的host
     */
    private String address="localhost";
    /**
     * 端口，默认-1，client内部按照ssl和非ssl自行处为5671和5672
     */
    private Integer port=-1;

    /**
     * 是否开启ssl
     */
    private Boolean ssl;
    /**
     * 用户名
     */
    private String username="guest";
    /**
     * 密码
     */
    private String password="guest";


    /**
     * rpc调用超时时间，-1表示不限制
     */
    private long rpcTimeOut=-1;
    /**
     * 集群名称，用于一个消息队列区分不同的集群系统
     */
    private String clusterName;
    /**
     * 核心线程
     */
    private Integer coreSize=10;
    /**
     * 最大线程数
     */
    private Integer maxSize=100;
    /**
     * 连接超时时间
     */
    private Integer connectionTimeOut = 60;
    /**
     * 虚拟主机
     */
    private String vHost = "/";


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public EncodeTypeEnum getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = EncodeTypeEnum.enumForValue(encodeType);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRpcTimeOut() {
        return rpcTimeOut;
    }

    public void setRpcTimeOut(long rpcTimeOut) {
        this.rpcTimeOut = rpcTimeOut;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(Integer connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public String getvHost() {
        return vHost;
    }

    public void setvHost(String vHost) {
        this.vHost = vHost;
    }

    public String getQueueName(){
        return "rpc_"+clusterName+"_"+appId;
    }
}
