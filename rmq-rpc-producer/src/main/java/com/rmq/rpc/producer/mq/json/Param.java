package com.rmq.rpc.producer.mq.json;

import com.rmq.rpc.producer.mq.enums.EncodeTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 14:26:51
 */
public class Param implements Serializable {
    /**
     * service名称
     */
    private String serviceName;
    /**
     * 参数
     */
    private List<ParamItem> params;
    /**
     * 编码类型枚举
     */
    private EncodeTypeEnum type;

    public Param(String serviceName,EncodeTypeEnum type) {
        this.serviceName = serviceName;
        this.params = new ArrayList<>();
        this.type = type;
    }

    public EncodeTypeEnum getType() {
        return type;
    }

    public void setType(EncodeTypeEnum type) {
        this.type = type;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ParamItem> getParams() {
        return params;
    }

    public void setParams(List<ParamItem> params) {
        this.params = params;
    }

    public void addParamItes(ParamItem paramItem){
        params.add(paramItem);
    }
}
