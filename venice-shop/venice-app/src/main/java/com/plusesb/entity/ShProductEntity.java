package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 产品表
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_product")
public class ShProductEntity extends AppBaseEntity {

        /**
         * 库存量
         */
        private Integer stockNumber;

            /**
             * 市场价
             */
            private BigDecimal marketPrice;

            /**
             * 零售价格
             */
            private BigDecimal retailPrice;

		            /**
             * 产品型号
             */
            private String productModel;

		            /**
             * 商品id
             */
            private Long goodsId;
            /**
             * 产品分类
             */
            private String specKey;
            /**
             * 产品分类
             */
            private String specValue;

            /**
             * 缩略图图片
             */
            private String thumbnailUrl;

            /**
             *是否默认
             */
            private Integer isDefault;

            public Integer getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(Integer isDefault) {
                this.isDefault = isDefault;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            /**
             * 设置：规格属性
             */
            public void setSpecKey(String specKey) {
                this.specKey = specKey;
            }
            /**
             * 获取：规格属性
             */
            public String getSpecKey() {
                return specKey;
            }
		
	                    /**
             * 设置：规格值
             */
            public void setSpecValue(String specValue) {
                this.specValue = specValue;
            }
            /**
             * 获取：规格值
             */
            public String getSpecValue() {
                return specValue;
            }
		
	                    /**
             * 设置：库存量
             */
            public void setStockNumber(Integer stockNumber) {
                this.stockNumber = stockNumber;
            }
            /**
             * 获取：库存量
             */
            public Integer getStockNumber() {
                return stockNumber;
            }

            public BigDecimal getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(BigDecimal marketPrice) {
                this.marketPrice = marketPrice;
            }

            public BigDecimal getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }

            /**
             * 设置：产品型号
             */
            public void setProductModel(String productModel) {
                this.productModel = productModel;
            }
            /**
             * 获取：产品型号
             */
            public String getProductModel() {
                return productModel;
            }

            public Long getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(Long goodsId) {
                this.goodsId = goodsId;
            }
}
