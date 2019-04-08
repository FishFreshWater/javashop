package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by altchen on 2017/5/31.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderPayStatus implements IEnum<String> {
    PENDING("PENDING","待支付"),
    PAYED("PAYED","已支付"),
    REFUNDING("REFUNDING","退款中"),
    REFUND("REFUND","已退款");

    private String value;
    private String desc;
    OrderPayStatus(final String value, final String desc) {
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
    public static String getDescByValue(String value) {
        OrderPayStatus[] enums = OrderPayStatus.values();
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
        OrderPayStatus[] enums = OrderPayStatus.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}
