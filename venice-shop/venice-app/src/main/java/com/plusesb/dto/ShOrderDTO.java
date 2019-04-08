package com.plusesb.dto;

import java.io.Serializable;


/**
 * @author linyuchi
 * @email 532826912@qq.com
 * @date 2017-08-15 08:03:41
 */
public class ShOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单编码
     */
    private String orderNumber;

    /**
     *退款原因
     */
    private String comment;

    /**
     * formId发送信息时使用
     */

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
