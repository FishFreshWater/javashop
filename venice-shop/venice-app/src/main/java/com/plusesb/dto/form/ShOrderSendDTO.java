
package com.plusesb.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单提交表单
 *
 * @author linyuchi
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "订单发货")
public class ShOrderSendDTO {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 快递号ID
     */
    private String shippingNo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }
}
