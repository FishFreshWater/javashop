package com.plusesb.entity.enums;

/**
 * Created by altchen on 2017/5/31.
 *  1--（待支付）已提交待支付 2--（已取消）已取消 3--（已支付）已提交已支付待发货
 *  4--（已退款）已退款  5--（部分发货）已提交已支付部分发货 6--（已发货）已提交已支付全部发货  7--(已退货)已提交已支付已退货 8--（已完成）已完成
 */
public enum OrderSingleStatus {
    PENDING(1,"待付款"),
    CANCEL(2,"已取消"),
    PAYED(3,"待发货"),
    REFUND(4,"已退款"),
    PART_SEND(5,"部分发货"),
    SEND(6,"待收货"),
    RETURN(7,"已退货"),
    COMPLETE(8,"已完成");

    private Integer code;
    private String name;
    OrderSingleStatus(Integer code, String name){
        this.code= code;
        this.name= name;
    }
    public Integer getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    /**
     * 根据key获取value
     *
     * @param code
     *            : 键值key
     * @return String
     */
    public static String getNameByKey(Integer code) {
        OrderSingleStatus[] enums = OrderSingleStatus.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(code)) {
                return enums[i].getName();
            }
        }
        return "";
    }
}
