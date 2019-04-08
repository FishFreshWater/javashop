package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.entity.enums.OrderReturnCheckStatus;
import com.plusesb.entity.enums.OrderReturnStatus;
import com.plusesb.entity.enums.ShOrderReturnType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单退款
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-08 19:48:28
 */
@SuppressWarnings("serial")
@TableName("sh_order_return")
public class ShOrderReturnEntity extends AppBaseEntity {
                    /**
         * 退货类型
         */
        private Integer returnType;
                /**
         * 退货退款单号
         */
        private String number;
                /**
         * 归属订单
         */
        private Long orderId;
        /**
         * 归属订单
         */
        private Long userId;
                /**
         * 退货金额
         */
        private BigDecimal amount;
                /**
         * 原因
         */
        private String reason;
                /**
         * 审核状态
         */
        private Integer checkStatus;
                /**
         * 当前状态
         */
        private Integer status;
                /**
         * 审核时间
         */
        private Date checkTime;
                /**
         * 操作人
         */
        private String operator;
                /**
         * 审核意见
         */
        private String checkComment;
                /**
         * 快递单号
         */
        private String tracking;
                        /**
         * 物流公司
         */
        private String expressCrop;
                /**
         * 物流发货备注
         */
        private String expressDesc;


        //***********************附加字段**************************//

        @TableField(exist = false)
        private ShOrderEntity shOrderEntity;

        @TableField(exist = false)
        private String statusName;

        @TableField(exist = false)
        private String checkStatusName;
        @TableField(exist = false)
        private String typeName;

        public String getTypeName() {
            return ShOrderReturnType.getDescByValue(this.getReturnType());
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public ShOrderEntity getShOrderEntity() {
            return shOrderEntity;
        }

        public void setShOrderEntity(ShOrderEntity shOrderEntity) {
            this.shOrderEntity = shOrderEntity;
        }

        public String getCheckStatusName() {
            return OrderReturnCheckStatus.getDescByValue(this.getCheckStatus());
        }

        public void setCheckStatusName(String checkStatusName) {
            this.checkStatusName = checkStatusName;
        }

        public String getStatusName() {
            return OrderReturnStatus.getDescByValue(this.getStatus());
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
        //***********************附加字段**************************//

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        /**
         * 设置：退货类型
         */
        public void setReturnType(Integer returnType) {
            this.returnType = returnType;
        }
        /**
         * 获取：退货类型
         */
        public Integer getReturnType() {
            return returnType;
        }
    
            /**
         * 设置：退货退款单号
         */
        public void setNumber(String number) {
            this.number = number;
        }
        /**
         * 获取：退货退款单号
         */
        public String getNumber() {
            return number;
        }
    
            /**
         * 设置：归属订单
         */
        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }
        /**
         * 获取：归属订单
         */
        public Long getOrderId() {
            return orderId;
        }
    
            /**
         * 设置：退货金额
         */
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
        /**
         * 获取：退货金额
         */
        public BigDecimal getAmount() {
            return amount;
        }
    
            /**
         * 设置：原因
         */
        public void setReason(String reason) {
            this.reason = reason;
        }
        /**
         * 获取：原因
         */
        public String getReason() {
            return reason;
        }
    
            /**
         * 设置：审核状态
         */
        public void setCheckStatus(Integer checkStatus) {
            this.checkStatus = checkStatus;
        }
        /**
         * 获取：审核状态
         */
        public Integer getCheckStatus() {
            return checkStatus;
        }
    
            /**
         * 设置：当前状态
         */
        public void setStatus(Integer status) {
            this.status = status;
        }
        /**
         * 获取：当前状态
         */
        public Integer getStatus() {
            return status;
        }
    
            /**
         * 设置：审核时间
         */
        public void setCheckTime(Date checkTime) {
            this.checkTime = checkTime;
        }
        /**
         * 获取：审核时间
         */
        public Date getCheckTime() {
            return checkTime;
        }
    
            /**
         * 设置：操作人
         */
        public void setOperator(String operator) {
            this.operator = operator;
        }
        /**
         * 获取：操作人
         */
        public String getOperator() {
            return operator;
        }
    
            /**
         * 设置：审核意见
         */
        public void setCheckComment(String checkComment) {
            this.checkComment = checkComment;
        }
        /**
         * 获取：审核意见
         */
        public String getCheckComment() {
            return checkComment;
        }
    
            /**
         * 设置：快递单号
         */
        public void setTracking(String tracking) {
            this.tracking = tracking;
        }
        /**
         * 获取：快递单号
         */
        public String getTracking() {
            return tracking;
        }
    
    
    
            /**
         * 设置：物流公司
         */
        public void setExpressCrop(String expressCrop) {
            this.expressCrop = expressCrop;
        }
        /**
         * 获取：物流公司
         */
        public String getExpressCrop() {
            return expressCrop;
        }
    
            /**
         * 设置：物流发货备注
         */
        public void setExpressDesc(String expressDesc) {
            this.expressDesc = expressDesc;
        }
        /**
         * 获取：物流发货备注
         */
        public String getExpressDesc() {
            return expressDesc;
        }
    
}
