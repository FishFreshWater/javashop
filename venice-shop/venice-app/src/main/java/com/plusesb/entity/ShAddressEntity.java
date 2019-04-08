package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang.StringUtils;


/**
 * 用户地址信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@SuppressWarnings("serial")
@TableName("sh_address")
public class ShAddressEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 收货人姓名
             */
            private String userName;
			
		            /**
             * 手机
             */
            private String telNumber;
			
		            /**
             * 邮编
             */
            private String postalCode;
			
		            /**
             * 收货地址国家码
             */
            private String nationalCode;
			
		            /**
             * 省
             */
            private String provinceName;
			
		            /**
             * 市
             */
            private String cityName;
			
		            /**
             * 区
             */
            private String countyName;
			
		            /**
             * 详细收货地址信息
             */
            private String detailInfo;
			
		            /**
             * 会员ID
             */
            private Long userId;
			
		            /**
             * 是否默认地址
             */
            private Integer isDefault;

            //********************附加字段*****************//
            /**
             * 地区全名
             */
            @TableField(exist = false)
            private String  fullRegion;

            public String getFullRegion() {
                if (StringUtils.isEmpty(fullRegion)) {
                    fullRegion = getProvinceName() + getCityName() + getCountyName();
                }
                return fullRegion;
            }

            public void setFullRegion(String fullRegion) {
                this.fullRegion = fullRegion;
            }
            //********************附加字段*****************//


            /**
             * 设置：收货人姓名
             */
            public void setUserName(String userName) {
                this.userName = userName;
            }
            /**
             * 获取：收货人姓名
             */
            public String getUserName() {
                return userName;
            }
		
	                    /**
             * 设置：手机
             */
            public void setTelNumber(String telNumber) {
                this.telNumber = telNumber;
            }
            /**
             * 获取：手机
             */
            public String getTelNumber() {
                return telNumber;
            }
		
	                    /**
             * 设置：邮编
             */
            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }
            /**
             * 获取：邮编
             */
            public String getPostalCode() {
                return postalCode;
            }
		
	                    /**
             * 设置：收货地址国家码
             */
            public void setNationalCode(String nationalCode) {
                this.nationalCode = nationalCode;
            }
            /**
             * 获取：收货地址国家码
             */
            public String getNationalCode() {
                return nationalCode;
            }
		
	                    /**
             * 设置：省
             */
            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }
            /**
             * 获取：省
             */
            public String getProvinceName() {
                return provinceName;
            }
		
	                    /**
             * 设置：市
             */
            public void setCityName(String cityName) {
                this.cityName = cityName;
            }
            /**
             * 获取：市
             */
            public String getCityName() {
                return cityName;
            }
		
	                    /**
             * 设置：区
             */
            public void setCountyName(String countyName) {
                this.countyName = countyName;
            }
            /**
             * 获取：区
             */
            public String getCountyName() {
                return countyName;
            }
		
	                    /**
             * 设置：详细收货地址信息
             */
            public void setDetailInfo(String detailInfo) {
                this.detailInfo = detailInfo;
            }
            /**
             * 获取：详细收货地址信息
             */
            public String getDetailInfo() {
                return detailInfo;
            }
		
	                    /**
             * 设置：会员ID
             */
            public void setUserId(Long userId) {
                this.userId = userId;
            }
            /**
             * 获取：会员ID
             */
            public Long getUserId() {
                return userId;
            }
		
	                    /**
             * 设置：是否默认地址
             */
            public void setIsDefault(Integer isDefault) {
                this.isDefault = isDefault;
            }
            /**
             * 获取：是否默认地址
             */
            public Integer getIsDefault() {
                return isDefault;
            }
		
	        
	}
