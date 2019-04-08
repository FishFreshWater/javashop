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
public enum MenuType {

    /**
     * 目录
     */
    CATALOG(0,"目录"),
    /**
     * 菜单
     */
    MENU(1,"菜单"),
    /**
     * 按钮
     */
    BUTTON(2,"按钮");


    private final Integer key;
    private final String value;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    MenuType(Integer key, String value) {
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
    public static String getValueByKey(Integer key) {
        MenuType[] enums = MenuType.values();
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
        MenuType[] enums = MenuType.values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].getKey().toString(), enums[i].getValue());
        }
        return map;
    }
}
