package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 商品评价
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-11 15:25:46
 */
@SuppressWarnings("serial")
@TableName("sh_comment")
public class ShCommentEntity extends AppBaseEntity {
                            /**
         * 评论内容
         */
        private String content;
                /**
         * 用户
         */
        private Long userId;
                /**
         * 商品
         */
        private Long goodsId;
                /**
         * 评论图片
         */
        private String picUrl;

            /**
         * 设置：评论内容
         */
        public void setContent(String content) {
            this.content = content;
        }
        /**
         * 获取：评论内容
         */
        public String getContent() {
            return content;
        }
    
            /**
         * 设置：用户
         */
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        /**
         * 获取：用户
         */
        public Long getUserId() {
            return userId;
        }
    
            /**
         * 设置：商品
         */
        public void setGoodsId(Long goodsId) {
            this.goodsId = goodsId;
        }
        /**
         * 获取：商品
         */
        public Long getGoodsId() {
            return goodsId;
        }
    
            /**
         * 设置：评论图片
         */
        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
        /**
         * 获取：评论图片
         */
        public String getPicUrl() {
            return picUrl;
        }
    
}
