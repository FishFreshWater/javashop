package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.entity.enums.OrderPayStatus;
import com.plusesb.entity.enums.OrderShippingStatus;
import com.plusesb.entity.enums.OrderSingleStatus;
import com.plusesb.entity.enums.OrderStatus;
import com.plusesb.utils.ShOrderUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.OrderUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@SuppressWarnings("serial")
@TableName("sh_order")
public class ShOrderEntity extends AppBaseEntity {

		            /**
             * 收货人用户id
             */
            private Long userId;

            /**
             *收货人
             */
            @TableField(exist = false)
            private ShUserEntity shUser;

            /**
             * 商品标题
             */
            private String goodsName;
			
		            /**
             * 订单状态
             */
            private String orderStatus;

            /**
             * 支付状态
             */
            private String payStatus;
            /**
             * 快递状态
             */
            private String shippingStatus;
			
		            /**
             * 订单类型(DEF:普通订单;SELF:自提订单)
             */
            private String orderType;
			
		            /**
             * 优惠类型(01:首单优惠;02:减运费;03:使用卡券;04:积分减免)
             */
            private Integer discountType;
			

		            /**
             * 收货人
             */
            private String consignee;
			
		            /**
             * 国家
             */
            private String country;
			
		            /**
             * 省
             */
            private String province;
			
		            /**
             * 市
             */
            private String city;
			
		            /**
             * 区
             */
            private String area;
			
		            /**
             * 详细地址
             */
            private String address;
			
		            /**
             * 收货人电话
             */
            private String mobile;
			
		            /**
             * 买家留言
             */
            private String postscript;


            /**
             * 运费
             */
            private BigDecimal freight;

		            /**
             * 快递公司名字
             */
            private String shippingName;
			
		            /**
             * 快递编号
             */
            private String shippingNo;
            /**
             * 发货时间
             */
            private Date shippingTime;

			

            /**
             * 实际需要支付的金额
             */
            private BigDecimal actualPrice;
			
		            /**
             * 积分
             */
            private Integer integral;
			
		            /**
             * 积分抵扣金额
             */
            private BigDecimal integralMoney;
			
		            /**
             * 订单总价(商品总价+快递费-优惠-折扣等)
             */
            private BigDecimal orderPrice;
			
		            /**
             * 商品总价
             */
            private BigDecimal goodsPrice;
			
		            /**
             * 订单添加时间
             */
            private Date addTime;
			
		            /**
             * 订单确认时间
             */
            private Date confirmTime;
			
		            /**
             * 支付时间
             */
            private Date payTime;
			

			
		            /**
             * 完成时间
             */
            private Date finishTime;
			
		            /**
             * 退款时间
             */
            private BigDecimal refundTime;
			
		            /**
             * 配送价格
             */
            private BigDecimal freightPrice;
			
		            /**
             * 使用的优惠券id
             */
            private Long couponId;
			
		            /**
             * 优惠券价格
             */
            private BigDecimal couponPrice;
			
		            /**
             * 上级订单id
             */
            private Long parentId;
			
		            /**
             * 满减价格
             */
            private BigDecimal fullCutPrice;
			
            /**
             * 订单号,规则(年月日时分秒+用户ID后4位)=16位   年两位如17,用户id4位补0,如0899
             */
            private String orderNumber;





            //*******************************展示字段*****************************//
            /**
             * 订单附带商品
             */
            @TableField(exist = false)
            private List<ShOrderGoodsEntity> shOrderGoodsEntityList;
            /**
             *商品数量
             */
            @TableField(exist = false)
            private Integer goodsCount;

            @TableField(exist = false)
            private String userName;

            @TableField(exist = false)
            private String userMobile;
            @TableField(exist = false)
            private String  fullRegion;

            public String getFullRegion() {
                if (StringUtils.isEmpty(fullRegion)) {
                    fullRegion = getProvince() + getCity() + getCity();
                }
                return fullRegion;
            }

            public void setFullRegion(String fullRegion) {
                this.fullRegion = fullRegion;
            }
            /**
             * 流程状态
             */
            @TableField(exist = false)
            private Integer singleStatus;
            /**
             * 流程状态
             */
            @TableField(exist = false)
            private String singleStatusName;

            public Integer getSingleStatus() {
                return ShOrderUtils.getSingleOrderStatus(this.orderStatus,this.payStatus,this.shippingStatus);
            }

            public void setSingleStatus(Integer singleStatus) {
                this.singleStatus = singleStatus;
            }

            public String getSingleStatusName() {
                return OrderSingleStatus.getNameByKey(this.getSingleStatus());
            }

            public void setSingleStatusName(String singleStatusName) {
                this.singleStatusName = singleStatusName;
            }

            public Integer getGoodsCount() {
                        return goodsCount;
            }

            public void setGoodsCount(Integer goodsCount) {
                this.goodsCount = goodsCount;
            }

            public List<ShOrderGoodsEntity> getShOrderGoodsEntityList() {
                return shOrderGoodsEntityList;
            }

            public void setShOrderGoodsEntityList(List<ShOrderGoodsEntity> shOrderGoodsEntityList) {
                this.shOrderGoodsEntityList = shOrderGoodsEntityList;
            }

            public String getUserName() {
                        return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            //*******************************展示字段*****************************//


            public BigDecimal getFreight() {
                return freight;
            }

            public void setFreight(BigDecimal freight) {
                this.freight = freight;
            }

            public ShUserEntity getShUser() {
                        return shUser;
                    }

            public void setShUser(ShUserEntity shUser) {
                this.shUser = shUser;
            }

            /**
             * 设置：收货人用户id
             */
            public void setUserId(Long userId) {
                this.userId = userId;
            }
            /**
             * 获取：收货人用户id
             */
            public Long getUserId() {
                return userId;
            }
		
	                    /**
             * 设置：商品标题
             */
            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
            /**
             * 获取：商品标题
             */
            public String getGoodsName() {
                return goodsName;
            }
		

	                    /**
             * 设置：订单类型(01:普通订单;02:自提订单)
             */
            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }
            /**
             * 获取：订单类型(01:普通订单;02:自提订单)
             */
            public String getOrderType() {
                return orderType;
            }
		
	                    /**
             * 设置：优惠类型(01:首单优惠;02:减运费;03:使用卡券;04:积分减免)
             */
            public void setDiscountType(Integer discountType) {
                this.discountType = discountType;
            }
            /**
             * 获取：优惠类型(01:首单优惠;02:减运费;03:使用卡券;04:积分减免)
             */
            public Integer getDiscountType() {
                return discountType;
            }
		

	                    /**
             * 设置：收货人
             */
            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }
            /**
             * 获取：收货人
             */
            public String getConsignee() {
                return consignee;
            }
		
	                    /**
             * 设置：国家
             */
            public void setCountry(String country) {
                this.country = country;
            }
            /**
             * 获取：国家
             */
            public String getCountry() {
                return country;
            }
		
	                    /**
             * 设置：省
             */
            public void setProvince(String province) {
                this.province = province;
            }
            /**
             * 获取：省
             */
            public String getProvince() {
                return province;
            }
		
	                    /**
             * 设置：市
             */
            public void setCity(String city) {
                this.city = city;
            }
            /**
             * 获取：市
             */
            public String getCity() {
                return city;
            }
		
	                    /**
             * 设置：区
             */
            public void setArea(String area) {
                this.area = area;
            }
            /**
             * 获取：区
             */
            public String getArea() {
                return area;
            }
		
	                    /**
             * 设置：详细地址
             */
            public void setAddress(String address) {
                this.address = address;
            }
            /**
             * 获取：详细地址
             */
            public String getAddress() {
                return address;
            }
		
	                    /**
             * 设置：收货人电话
             */
            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
            /**
             * 获取：收货人电话
             */
            public String getMobile() {
                return mobile;
            }
		
	                    /**
             * 设置：买家留言
             */
            public void setPostscript(String postscript) {
                this.postscript = postscript;
            }
            /**
             * 获取：买家留言
             */
            public String getPostscript() {
                return postscript;
            }

	                    /**
             * 设置：快递公司名字
             */
            public void setShippingName(String shippingName) {
                this.shippingName = shippingName;
            }
            /**
             * 获取：快递公司名字
             */
            public String getShippingName() {
                return shippingName;
            }
		
	                    /**
             * 设置：快递编号
             */
            public void setShippingNo(String shippingNo) {
                this.shippingNo = shippingNo;
            }
            /**
             * 获取：快递编号
             */
            public String getShippingNo() {
                return shippingNo;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getShippingStatus() {
                return shippingStatus;
            }

            public void setShippingStatus(String shippingStatus) {
                this.shippingStatus = shippingStatus;
            }


	                    /**
             * 设置：实际需要支付的金额
             */
            public void setActualPrice(BigDecimal actualPrice) {
                this.actualPrice = actualPrice;
            }
            /**
             * 获取：实际需要支付的金额
             */
            public BigDecimal getActualPrice() {
                return actualPrice;
            }
		
	                    /**
             * 设置：积分
             */
            public void setIntegral(Integer integral) {
                this.integral = integral;
            }
            /**
             * 获取：积分
             */
            public Integer getIntegral() {
                return integral;
            }
		
	                    /**
             * 设置：积分抵扣金额
             */
            public void setIntegralMoney(BigDecimal integralMoney) {
                this.integralMoney = integralMoney;
            }
            /**
             * 获取：积分抵扣金额
             */
            public BigDecimal getIntegralMoney() {
                return integralMoney;
            }
		
	                    /**
             * 设置：订单总价(商品总价+快递费-优惠-折扣等)
             */
            public void setOrderPrice(BigDecimal orderPrice) {
                this.orderPrice = orderPrice;
            }
            /**
             * 获取：订单总价(商品总价+快递费-优惠-折扣等)
             */
            public BigDecimal getOrderPrice() {
                return orderPrice;
            }
		
	                    /**
             * 设置：商品总价
             */
            public void setGoodsPrice(BigDecimal goodsPrice) {
                this.goodsPrice = goodsPrice;
            }
            /**
             * 获取：商品总价
             */
            public BigDecimal getGoodsPrice() {
                return goodsPrice;
            }
		
	                    /**
             * 设置：订单添加时间
             */
            public void setAddTime(Date addTime) {
                this.addTime = addTime;
            }
            /**
             * 获取：订单添加时间
             */
            public Date getAddTime() {
                return addTime;
            }
		
	                    /**
             * 设置：订单确认时间
             */
            public void setConfirmTime(Date confirmTime) {
                this.confirmTime = confirmTime;
            }
            /**
             * 获取：订单确认时间
             */
            public Date getConfirmTime() {
                return confirmTime;
            }
		
	                    /**
             * 设置：支付时间
             */
            public void setPayTime(Date payTime) {
                this.payTime = payTime;
            }
            /**
             * 获取：支付时间
             */
            public Date getPayTime() {
                return payTime;
            }

            public Date getShippingTime() {
                return shippingTime;
            }

            public void setShippingTime(Date shippingTime) {
                this.shippingTime = shippingTime;
            }

            /**
             * 设置：完成时间
             */
            public void setFinishTime(Date finishTime) {
                this.finishTime = finishTime;
            }
            /**
             * 获取：完成时间
             */
            public Date getFinishTime() {
                return finishTime;
            }
		
	                    /**
             * 设置：退款时间
             */
            public void setRefundTime(BigDecimal refundTime) {
                this.refundTime = refundTime;
            }
            /**
             * 获取：退款时间
             */
            public BigDecimal getRefundTime() {
                return refundTime;
            }
		
	                    /**
             * 设置：配送价格
             */
            public void setFreightPrice(BigDecimal freightPrice) {
                this.freightPrice = freightPrice;
            }
            /**
             * 获取：配送价格
             */
            public BigDecimal getFreightPrice() {
                return freightPrice;
            }
		
	                    /**
             * 设置：使用的优惠券id
             */
            public void setCouponId(Long couponId) {
                this.couponId = couponId;
            }
            /**
             * 获取：使用的优惠券id
             */
            public Long getCouponId() {
                return couponId;
            }
		
	                    /**
             * 设置：优惠券价格
             */
            public void setCouponPrice(BigDecimal couponPrice) {
                this.couponPrice = couponPrice;
            }
            /**
             * 获取：优惠券价格
             */
            public BigDecimal getCouponPrice() {
                return couponPrice;
            }
		
	                    /**
             * 设置：上级订单id
             */
            public void setParentId(Long parentId) {
                this.parentId = parentId;
            }
            /**
             * 获取：上级订单id
             */
            public Long getParentId() {
                return parentId;
            }
		
	                    /**
             * 设置：满减价格
             */
            public void setFullCutPrice(BigDecimal fullCutPrice) {
                this.fullCutPrice = fullCutPrice;
            }
            /**
             * 获取：满减价格
             */
            public BigDecimal getFullCutPrice() {
                return fullCutPrice;
            }
		
	                    /**
             * 设置：下单数量
             */
            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }
            /**
             * 获取：下单数量
             */
            public String getOrderNumber() {
                return orderNumber;
            }
		

	}
