package com.plusesb.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单表的退货状态
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderReturnStatus implements IEnum<Integer> {
    INIT(0,"进行中"),
    COMPLETED(1,"已完成"),
    CANCEL(2,"已取消");
    private Integer value;
    private String desc;

    OrderReturnStatus(final Integer value, final String desc) {
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
        OrderReturnStatus[] enums = OrderReturnStatus.values();
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
        OrderReturnStatus[] enums = OrderReturnStatus.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}
