package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Created by linyuchi on 2017/5/31.
 */
public enum ShOrderType implements IEnum<String> {
    DEF("DEF","普通订单"),
    SELF("SELF","自提订单");

    private String value;
    private String desc;

    ShOrderType(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
}
