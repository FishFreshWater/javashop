package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_coupon")
public class ShCouponEntity extends AppBaseEntity {


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
            private Integer status;
			
		    /**
             * 开始领取时间
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            private Date startDay;
			
		            /**
             * 领取结束时间
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            private Date endDay;
			
		            /**
             * 满多少使用
             */
            private BigDecimal limitBalance;


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

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
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
