package com.rmq.rpc.producer.config;

import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description
 * @date 2023/5/22 15:28:52
 */
@ConfigurationProperties(prefix = "rmq.producer")
@Component
public class RmqProducerConfig {
    /**
     * 当前服务的appId
     */
    private String appId;

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
     * 连接超时时间
     */
    private Integer connectionTimeOut = 60;
    /**
     * 虚拟主机
     */
    private String vHost = "/";
    /**
     * 核心线程
     */
    private Integer coreSize=10;
    /**
     * 最大线程数
     */
    private Integer maxSize=100;
    /**
     * 线程存活时间
     */
    private Long keepAliveTime=60L;
    
    
    
    

    public void setEncodeType(EncodeTypeEnum encodeType) {
        this.encodeType = encodeType;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public EncodeTypeEnum getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = EncodeTypeEnum.enumForValue(encodeType);
    }

    public String getvHost() {
        return vHost;
    }

    public void setvHost(String vHost) {
        this.vHost = vHost;
    }

    public Integer getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(Integer connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getQueueName(){
        return "rpc_"+clusterName+"_"+appId;
    }
}
