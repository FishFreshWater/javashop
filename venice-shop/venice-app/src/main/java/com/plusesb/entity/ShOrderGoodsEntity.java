package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 订单商品关系
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_order_goods")
public class ShOrderGoodsEntity extends AppBaseEntity {

	
			
			
			
			
            /**
             * 订单Id
             */
            private Long orderId;
			
		            /**
             * 商品id
             */
            private Long goodsId;

            /**
             *产品名称
             */
            private String goodsName;
            /**
             * 产品Id
             */
            private Long productId;
			
		            /**
             * 商品数量
             */
            private Integer number;
			
		            /**
             * 市场价
             */
            private BigDecimal marketPrice;

		            /**
             * 零售价格
             */
            private BigDecimal retailPrice;

		            /**
             * 虚拟商品
             */
            private Integer isReal;

            /**
             * 缩略图
             */
            private String thumbnailUrl;

            /**
             *规格名称
             */
            private String specValue;

            public String getSpecValue() {
                return specValue;
            }

            public void setSpecValue(String specValue) {
                this.specValue = specValue;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getGoodsName() {
                        return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            /**
             * 设置：订单Id
             */
            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }
            /**
             * 获取：订单Id
             */
            public Long getOrderId() {
                return orderId;
            }
		
	                    /**
             * 设置：商品id
             */
            public void setGoodsId(Long goodsId) {
                this.goodsId = goodsId;
            }
            /**
             * 获取：商品id
             */
            public Long getGoodsId() {
                return goodsId;
            }
		
	                    /**
             * 设置：产品Id
             */
            public void setProductId(Long productId) {
                this.productId = productId;
            }
            /**
             * 获取：产品Id
             */
            public Long getProductId() {
                return productId;
            }
		
	                    /**
             * 设置：商品数量
             */
            public void setNumber(Integer number) {
                this.number = number;
            }
            /**
             * 获取：商品数量
             */
            public Integer getNumber() {
                return number;
            }
		
	                    /**
             * 设置：市场价
             */
            public void setMarketPrice(BigDecimal marketPrice) {
                this.marketPrice = marketPrice;
            }
            /**
             * 获取：市场价
             */
            public BigDecimal getMarketPrice() {
                return marketPrice;
            }
		
	                    /**
             * 设置：零售价格
             */
            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }
            /**
             * 获取：零售价格
             */
            public BigDecimal getRetailPrice() {
                return retailPrice;
            }
		
	                    /**
             * 设置：虚拟商品
             */
            public void setIsReal(Integer isReal) {
                this.isReal = isReal;
            }
            /**
             * 获取：虚拟商品
             */
            public Integer getIsReal() {
                return isReal;
            }
		
	        
	}
