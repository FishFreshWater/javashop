package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 订单退款日志
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-08 19:48:28
 */
@SuppressWarnings("serial")
@TableName("sh_order_return_log")
public class ShOrderReturnLogEntity extends AppBaseEntity {
                /**
         * 退换货主表ID
         */
        private Long orderReturnId;
                /**
         * 操作员
         */
        private String operator;
                /**
         * 意见
         */
        private String content;
                
    
            /**
         * 设置：退换货主表ID
         */
        public void setOrderReturnId(Long orderReturnId) {
            this.orderReturnId = orderReturnId;
        }
        /**
         * 获取：退换货主表ID
         */
        public Long getOrderReturnId() {
            return orderReturnId;
        }
    
            /**
         * 设置：操作员
         */
        public void setOperator(String operator) {
            this.operator = operator;
        }
        /**
         * 获取：操作员
         */
        public String getOperator() {
            return operator;
        }
    
            /**
         * 设置：意见
         */
        public void setContent(String content) {
            this.content = content;
        }
        /**
         * 获取：意见
         */
        public String getContent() {
            return content;
        }
    
    
    
    
}
