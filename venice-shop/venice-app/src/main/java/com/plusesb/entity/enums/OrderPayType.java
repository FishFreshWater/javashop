package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum OrderPayType implements IEnum<String> {
    BAL("BAL","余额支付"),
    WX("WX","微信"),
    ALI("ALI","支付宝"),
    UNION("UNION","银联支付"),
    OFF("OFF","线下转账");

    private String value;
    private String desc;
    OrderPayType(final String value, final String desc) {
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

    /**
     * 根据key获取value
     *
     * @param value
     *            : 键值key
     * @return String
     */
    public static String getDescByValue(Integer value) {
        OrderPayType[] enums = OrderPayType.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getValue().equals(value)) {
                return enums[i].getDesc();
            }
        }
        return "";
    }

    /**
     * 转换为MAP集合
     *
     * @returnMap<String, String>
     */
    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        OrderPayType[] enums = OrderPayType.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}