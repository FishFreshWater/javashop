package com.plusesb.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum CouponStatue {


    USEABLE(1,"可用"),
    USED (2,"已用"),
    EXPIR(3,"过期");

    private Integer value;
    private String desc;
    CouponStatue(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue() {
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
        CouponType[] enums = CouponType.values();
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
        CouponType[] enums = CouponType.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}
