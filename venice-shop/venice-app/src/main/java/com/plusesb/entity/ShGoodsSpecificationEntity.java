package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 商品规格
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-11 19:53:38
 */
@SuppressWarnings("serial")
@TableName("sh_goods_specification")
public class ShGoodsSpecificationEntity extends AppBaseEntity {
                                /**
         * 商品ID
         */
        private Long goodsId;
                /**
         * 规格分类名称
         */
        private Long specificationId;
                /**
         * 规格值
         */
        private String value;


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

        public Long getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(Long specificationId) {
            this.specificationId = specificationId;
        }

        /**
         * 设置：规格值
         */
        public void setValue(String value) {
            this.value = value;
        }
        /**
         * 获取：规格值
         */
        public String getValue() {
            return value;
        }
    
}
