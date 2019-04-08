package com.plusesb.constant.status;

import com.baomidou.mybatisplus.core.enums.IEnum;
import java.util.HashMap;
import java.util.Map;

public enum UserStatus implements IEnum<Integer> {

    UNACTIVE (0,"未激活"),
    ACTIVE (1,"正常");

    private Integer value;
    private String desc;
    UserStatus(final Integer value, final String desc) {
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
    public static String getDescByValue(String value) {
        UserStatus[] enums = UserStatus.values();
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
        UserStatus[] enums = UserStatus.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getValue().toString(), enums[i].getDesc());
        }
        return map;
    }
}
