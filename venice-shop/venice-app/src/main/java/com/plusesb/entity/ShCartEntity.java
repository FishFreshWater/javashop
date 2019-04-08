package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 购物车
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@SuppressWarnings("serial")
@TableName("sh_cart")
public class ShCartEntity extends AppBaseEntity {

		            /**
             * 会员Id
             */
            private Long userId;
			
		            /**
             * 商品Id
             */
            private Long goodsId;
			
		            /**
             * 产品id
             */
            private Long productId;
			
//		            /**
//             * 市场价
//             */
//            private BigDecimal marketPrice;
//
//		            /**
//             * 零售价格
//             */
//            private BigDecimal retailPrice;
			
		            /**
             * 数量
             */
            private Integer number;

            /**
             * 是否选取
             */
            private Integer checked;

            //**********************************展示字段******************************//
            /**
             * 产品图片
             */
            @TableField(exist = false)
            private String thumbnailUrl;
            /**
             *所选规格
             */
            @TableField(exist = false)
            private String specValue;
            /**
             *产品价格
             */
            @TableField(exist = false)
            private BigDecimal retailPrice;
            /**
             *商品名称
             */
            @TableField(exist = false)
            private String goodsName;

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getSpecValue() {
                return specValue;
            }

            public void setSpecValue(String specValue) {
                this.specValue = specValue;
            }

            public BigDecimal getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
//**********************************展示字段******************************//

            public Integer getChecked() {
                return checked;
            }

            public void setChecked(Integer checked) {
                this.checked = checked;
            }

            /**
             * 设置：会员Id
             */
            public void setUserId(Long userId) {
                this.userId = userId;
            }
            /**
             * 获取：会员Id
             */
            public Long getUserId() {
                return userId;
            }
		
	                    /**
             * 设置：商品Id
             */
            public void setGoodsId(Long goodsId) {
                this.goodsId = goodsId;
            }
            /**
             * 获取：商品Id
             */
            public Long getGoodsId() {
                return goodsId;
            }
		
	                    /**
             * 设置：产品id
             */
            public void setProductId(Long productId) {
                this.productId = productId;
            }
            /**
             * 获取：产品id
             */
            public Long getProductId() {
                return productId;
            }
		

	                    /**
             * 设置：数量
             */
            public void setNumber(Integer number) {
                this.number = number;
            }
            /**
             * 获取：数量
             */
            public Integer getNumber() {
                return number;
            }
		
	        
	}
