package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by altchen on 2017/5/31.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderPayInfoStatus implements IEnum<Integer> {
    UNPAY(0,"未支付"), //未支付
    PAYED(1,"已支付"), //已支付
    RETURN(2,"已退款"), //已退款
    FAILURE(3,"失败"); //失败
    private Integer value;

    private String desc;

    OrderPayInfoStatus(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * 根据key获取value
     *
     * @param value : 键值key
     * @return String
     */
    public static String getDescByValue(Integer value) {
        OrderPayInfoStatus[] enums = OrderPayInfoStatus.values();
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
        OrderPayInfoStatus[] enums = OrderPayInfoStatus.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}
