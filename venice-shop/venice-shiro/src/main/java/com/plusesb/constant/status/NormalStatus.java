package com.plusesb.constant.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linyuchi
 * Date: 2018/3/19
 * Time: 21:41
 * Description: No Description
 */
public enum NormalStatus {

    NO("0","否"),
    YES("1","是");


    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    NormalStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据key获取value 
     *
     * @param key
     *            : 键值key 
     * @return String
     */
    public static String getValueByKey(String key) {
        NormalStatus[] enums = NormalStatus.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getKey().equals(key)) {
                return enums[i].getValue();
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
        NormalStatus[] enums = NormalStatus.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getKey().toString(), enums[i].getValue());
        }
        return map;
    }
}
