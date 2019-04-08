package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linyuchi
 * Date: 2018/3/19
 * Time: 21:41
 * Description: No Description
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CouponEffectType implements IEnum<Integer>{

    FIXED(1,"固定日期"),
    AFTER(2,"领取后X天内有效");

    private Integer value;
    private String desc;
    CouponEffectType(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
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
        CouponEffectType[] enums = CouponEffectType.values();
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
        CouponEffectType[] enums = CouponEffectType.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }

}
