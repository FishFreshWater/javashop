package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 主题管理商品
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-09 11:27:53
 */
@SuppressWarnings("serial")
@TableName("sh_topic_goods")
public class ShTopicGoodsEntity extends AppBaseEntity {
                            /**
         * 商品ID
         */
        private Long goodsId;
                /**
         * 话题ID
         */
        private Long topicId;
    
    
    
    
    
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
         * 设置：话题ID
         */
        public void setTopicId(Long topicId) {
            this.topicId = topicId;
        }
        /**
         * 获取：话题ID
         */
        public Long getTopicId() {
            return topicId;
        }
    
}
