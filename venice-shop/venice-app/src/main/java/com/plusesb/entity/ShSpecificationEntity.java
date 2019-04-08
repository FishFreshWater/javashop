package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 规格
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-11 22:10:48
 */
@SuppressWarnings("serial")
@TableName("sh_specification")
public class ShSpecificationEntity extends AppBaseEntity {
                                /**
         * 商品ID
         */
        private Long goodsId;
                /**
         * 规格分类名称
         */
        private String name;






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
         * 设置：规格分类名称
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * 获取：规格分类名称
         */
        public String getName() {
            return name;
        }
    
}
