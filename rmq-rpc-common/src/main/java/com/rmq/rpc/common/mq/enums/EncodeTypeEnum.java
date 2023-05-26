package com.rmq.rpc.common.mq.enums;

import java.util.Locale;

/**
 * @author jilin
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/23 15:04:41
 */
public enum EncodeTypeEnum {
    /**
     * json编码
     */
    JSON("json");

    public String type;

    EncodeTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static EncodeTypeEnum enumForValue(String  type){
        type =  type.toLowerCase(Locale.ROOT);
        EncodeTypeEnum[] values = EncodeTypeEnum.values();
        for (EncodeTypeEnum value : values) {
            if (value.type.equals(type)){
                return value;
            }
        }
        return null;
    }
}
