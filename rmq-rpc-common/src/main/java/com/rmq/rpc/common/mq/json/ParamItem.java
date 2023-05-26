package com.rmq.rpc.common.mq.json;

import java.io.Serializable;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 14:59:27
 */
public class ParamItem implements Serializable {
    /**
     * 全限定类名
     */
    private String className;
    /**
     * 压缩后的json数据
     */
    private String json;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


}
