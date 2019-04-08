
package com.plusesb.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单提交表单
 *
 * @author linyuchi
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "订单提交表单")
public class ShOrderSubmitDTO {

    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;

    @ApiModelProperty(value = "类型（购物车或者直接购买）")
    private String type;

    @ApiModelProperty(value = "留言")
    private String postscript;

    @ApiModelProperty(value = "收货地址ID")
    private Long addressId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
