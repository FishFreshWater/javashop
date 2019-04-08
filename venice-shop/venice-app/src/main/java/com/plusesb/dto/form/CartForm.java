
package com.plusesb.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 购物车表单
 *
 * @author linyuchi
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "购物车表单")
public class CartForm {
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品数量")
    private Integer number;

    @ApiModelProperty(value = "购物车ID")
    private Long id;

    @ApiModelProperty(value = "是否选取")
    private Integer isChecked;

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
