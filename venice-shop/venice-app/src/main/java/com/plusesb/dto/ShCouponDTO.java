package com.plusesb.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.plusesb.entity.AppBaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public class ShCouponDTO{


            private Long id;
		    /**
             * 优惠券名
             */
            private String couponName;
			

		            /**
             * 优惠金额/抵扣金额
             */
            private BigDecimal couponBalance;


		    /**
             * 天数
             */
            private Integer days;
			
		            /**
             * 剩余数量
             */
            private Integer couponLeaveCount;
			
		            /**
             * 数量
             */
            private Integer couponCount;
			
		     /**
             * 是否上架
             */
            private String status;
			
		    /**
             * 开始领取时间
             */
            private Date startDay;
			
		            /**
             * 领取结束时间
             */
            private Date endDay;
			
		            /**
             * 满多少使用
             */
            private BigDecimal limitBalance;

            /**
             * 优惠券号
             */
            private String couponNumber;

            /**
             * 用户id
             */
            private Long userId;

            /**
             * 使用时间
             */
            private Date usedTime;


            /**
             * 使用的订单id
             */
            private Long orderId;

            /**
             * 状态 1. 可用 2. 已用 3. 过期
             */
            private Integer couponStatus;

            /**
             * 生效时间
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            private Date effectTime;

            /**
             * 到期时间
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            private Date expirTime;

            /**
             * 优惠券id
             */
            private Long couponUserId;

            //可用 1:可用 0：不可用
            private Integer enabled = 0;

            public Integer getEnabled() {
                return enabled;
            }

            public void setEnabled(Integer enabled) {
                this.enabled = enabled;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            /**
             * 设置：优惠券名
             */
            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }
            /**
             * 获取：优惠券名
             */
            public String getCouponName() {
                return couponName;
            }
		

	                    /**
             * 设置：优惠金额/抵扣金额
             */
            public void setCouponBalance(BigDecimal couponBalance) {
                this.couponBalance = couponBalance;
            }
            /**
             * 获取：优惠金额/抵扣金额
             */
            public BigDecimal getCouponBalance() {
                return couponBalance;
            }
		
	                    /**
             * 设置：天数
             */
            public void setDays(Integer days) {
                this.days = days;
            }
            /**
             * 获取：天数
             */
            public Integer getDays() {
                return days;
            }
		
	                    /**
             * 设置：剩余数量
             */
            public void setCouponLeaveCount(Integer couponLeaveCount) {
                this.couponLeaveCount = couponLeaveCount;
            }
            /**
             * 获取：剩余数量
             */
            public Integer getCouponLeaveCount() {
                return couponLeaveCount;
            }
		
	                    /**
             * 设置：数量
             */
            public void setCouponCount(Integer couponCount) {
                this.couponCount = couponCount;
            }
            /**
             * 获取：数量
             */
            public Integer getCouponCount() {
                return couponCount;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCouponNumber() {
                return couponNumber;
            }

            public void setCouponNumber(String couponNumber) {
                this.couponNumber = couponNumber;
            }

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public Date getUsedTime() {
                return usedTime;
            }

            public void setUsedTime(Date usedTime) {
                this.usedTime = usedTime;
            }

            public Long getOrderId() {
                return orderId;
            }

            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }

            public Integer getCouponStatus() {
                return couponStatus;
            }

            public void setCouponStatus(Integer couponStatus) {
                this.couponStatus = couponStatus;
            }

            public Date getEffectTime() {
                return effectTime;
            }

            public void setEffectTime(Date effectTime) {
                this.effectTime = effectTime;
            }

            public Date getExpirTime() {
                return expirTime;
            }

            public void setExpirTime(Date expirTime) {
                this.expirTime = expirTime;
            }

            public Long getCouponUserId() {
                return couponUserId;
            }

            public void setCouponUserId(Long couponUserId) {
                this.couponUserId = couponUserId;
            }

            /**
             * 设置：开始领取时间
             */
            public void setStartDay(Date startDay) {
                this.startDay = startDay;
            }
            /**
             * 获取：开始领取时间
             */
            public Date getStartDay() {
                return startDay;
            }
		
	                    /**
             * 设置：领取结束时间
             */
            public void setEndDay(Date endDay) {
                this.endDay = endDay;
            }
            /**
             * 获取：领取结束时间
             */
            public Date getEndDay() {
                return endDay;
            }
		
	                    /**
             * 设置：满多少使用
             */
            public void setLimitBalance(BigDecimal limitBalance) {
                this.limitBalance = limitBalance;
            }
            /**
             * 获取：满多少使用
             */
            public BigDecimal getLimitBalance() {
                return limitBalance;
            }
		
	        
	}
