package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


/**
 * 优惠券客户关系
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_coupon_user")
public class ShCouponUserEntity extends AppBaseEntity {

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
            private Date effectTime;
			
		            /**
             * 到期时间
             */
            private Date expirTime;
			
            /**
             * 优惠券id
             */
            private Long couponId;


            //*************附加字段****************//
            @TableField(exist = false)
            private ShCouponEntity shCouponEntity;

            public ShCouponEntity getShCouponEntity() {
                return shCouponEntity;
            }

            public void setShCouponEntity(ShCouponEntity shCouponEntity) {
                this.shCouponEntity = shCouponEntity;
            }


            @TableField(exist = false)
            private Integer enabled;
            public Integer getEnabled() {
                return enabled;
            }

            public void setEnabled(Integer enabled) {
                this.enabled = enabled;
            }
             //*************附加字段****************//


            /**
             * 设置：优惠券号
             */
            public void setCouponNumber(String couponNumber) {
                this.couponNumber = couponNumber;
            }
            /**
             * 获取：优惠券号
             */
            public String getCouponNumber() {
                return couponNumber;
            }
		
	                    /**
             * 设置：用户id
             */
            public void setUserId(Long userId) {
                this.userId = userId;
            }
            /**
             * 获取：用户id
             */
            public Long getUserId() {
                return userId;
            }
		
	                    /**
             * 设置：使用时间
             */
            public void setUsedTime(Date usedTime) {
                this.usedTime = usedTime;
            }
            /**
             * 获取：使用时间
             */
            public Date getUsedTime() {
                return usedTime;
            }
		

	                    /**
             * 设置：使用的订单id
             */
            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }
            /**
             * 获取：使用的订单id
             */
            public Long getOrderId() {
                return orderId;
            }
		
	                    /**
             * 设置：状态 1. 可用 2. 已用 3. 过期
             */
            public void setCouponStatus(Integer couponStatus) {
                this.couponStatus = couponStatus;
            }
            /**
             * 获取：状态 1. 可用 2. 已用 3. 过期
             */
            public Integer getCouponStatus() {
                return couponStatus;
            }
		
	                    /**
             * 设置：生效时间
             */
            public void setEffectTime(Date effectTime) {
                this.effectTime = effectTime;
            }
            /**
             * 获取：生效时间
             */
            public Date getEffectTime() {
                return effectTime;
            }
		
	                    /**
             * 设置：到期时间
             */
            public void setExpirTime(Date expirTime) {
                this.expirTime = expirTime;
            }
            /**
             * 获取：到期时间
             */
            public Date getExpirTime() {
                return expirTime;
            }
		
	                    /**
             * 设置：优惠券id
             */
            public void setCouponId(Long couponId) {
                this.couponId = couponId;
            }
            /**
             * 获取：优惠券id
             */
            public Long getCouponId() {
                return couponId;
            }
		
	        
	}
