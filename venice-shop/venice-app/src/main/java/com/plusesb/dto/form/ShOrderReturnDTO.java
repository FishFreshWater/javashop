
package com.plusesb.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单退款退货信息
 *
 * @author linyuchi
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "订单退款退货信息")
public class ShOrderReturnDTO {

    @ApiModelProperty(value = "描述")
    private String comment;

    @ApiModelProperty(value = "审核状态")
    private Integer checkStatus;

    @ApiModelProperty(value = "退款订单ID")
    private Long id;

    @ApiModelProperty(value = "退款金额")
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
