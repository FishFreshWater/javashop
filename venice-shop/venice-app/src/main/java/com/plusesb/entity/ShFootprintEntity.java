package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import sun.nio.cs.ext.Big5;

import java.math.BigDecimal;


/**
 * 用户足迹
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-17 10:37:34
 */
@SuppressWarnings("serial")
@TableName("sh_footprint")
public class ShFootprintEntity extends AppBaseEntity {
                                /**
         * 商品ID
         */
        private Long goodsId;
                /**
         * 用户名称
         */
        private Long userId;


        //********************** 附加字段 *****************************************//
        @TableField(exist = false)
        private String goodsName;

        @TableField(exist = false)

        private String primaryPicUrl;
        @TableField(exist = false)

        private String goodsBrief;
        @TableField(exist = false)

        private BigDecimal retailPrice;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getPrimaryPicUrl() {
            return primaryPicUrl;
        }

        public void setPrimaryPicUrl(String primaryPicUrl) {
            this.primaryPicUrl = primaryPicUrl;
        }

        public String getGoodsBrief() {
            return goodsBrief;
        }

        public void setGoodsBrief(String goodsBrief) {
            this.goodsBrief = goodsBrief;
        }

        public BigDecimal getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(BigDecimal retailPrice) {
            this.retailPrice = retailPrice;
        }

        //********************** 附加字段 *****************************************//

        /**
         * 设置：商品ID
         */
        public void setGoodsId(Long goodsId) {
            this.goodsId = goodsId;
        }
        /**
         * 获取：商品ID
         */
        public Long getGoodsId() {
            return goodsId;
        }
    
            /**
         * 设置：用户名称
         */
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        /**
         * 获取：用户名称
         */
        public Long getUserId() {
            return userId;
        }
    
}
