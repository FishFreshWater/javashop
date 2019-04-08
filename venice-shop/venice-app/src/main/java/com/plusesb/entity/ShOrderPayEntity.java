package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.entity.enums.OrderPayInfoStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_order_pay")
public class ShOrderPayEntity extends AppBaseEntity {



            /**
             * 支付类型:详见:OrderPayInfoPayType
             */
            private String payType;
			
			
		            /**
             * 订单编号
             */
            private Long orderId;
			
		    /**
             * 支付单单号
             */
            private String payNumber;
			
		            /**
             * 支付金额 (分)
             */
            private BigDecimal payBalance;
			
		            /**
             * 状态
             */
            private Integer status;
			
		            /**
             * 第三方支付单号(微信)
             */
            private String thirdPayId;
            /**
             * 第三方预支付编码
             */
            private String prepayId;

            /**
             * 附加数据
             */
            private String addData;
			
		            /**
             * 支付时间
             */
            private Date payTime;
			
		            /**
             * 网关响应吗
             */
            private String gatewayResCode;
			
		            /**
             * 网关响应体
             */
            private String gatewayResBody;

            //**********状态**********//

            @TableField(exist = false)
            private String statusName;

            public String getStatusName() {
                return OrderPayInfoStatus.getDescByValue(this.getStatus());
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public String getPrepayId() {
                return prepayId;
            }

            public void setPrepayId(String prepayId) {
                this.prepayId = prepayId;
            }

            public String getPayType() {
                                return payType;
                            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            /**
             * 设置：订单编号
             */
            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }
            /**
             * 获取：订单编号
             */
            public Long getOrderId() {
                return orderId;
            }

            public String getPayNumber() {
                return payNumber;
            }

            public void setPayNumber(String payNumber) {
                this.payNumber = payNumber;
            }

            /**
             * 设置：支付金额
             */
            public void setPayBalance(BigDecimal payBalance) {
                this.payBalance = payBalance;
            }
            /**
             * 获取：支付金额
             */
            public BigDecimal getPayBalance() {
                return payBalance;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            /**
             * 设置：第三方支付单号(微信)
             */
            public void setThirdPayId(String thirdPayId) {
                this.thirdPayId = thirdPayId;
            }
            /**
             * 获取：第三方支付单号(微信)
             */
            public String getThirdPayId() {
                return thirdPayId;
            }
		
	                    /**
             * 设置：附加数据
             */
            public void setAddData(String addData) {
                this.addData = addData;
            }
            /**
             * 获取：附加数据
             */
            public String getAddData() {
                return addData;
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
		
	                    /**
             * 设置：网关响应吗
             */
            public void setGatewayResCode(String gatewayResCode) {
                this.gatewayResCode = gatewayResCode;
            }
            /**
             * 获取：网关响应吗
             */
            public String getGatewayResCode() {
                return gatewayResCode;
            }
		
	                    /**
             * 设置：网关响应体
             */
            public void setGatewayResBody(String gatewayResBody) {
                this.gatewayResBody = gatewayResBody;
            }
            /**
             * 获取：网关响应体
             */
            public String getGatewayResBody() {
                return gatewayResBody;
            }
		
	        
	}
