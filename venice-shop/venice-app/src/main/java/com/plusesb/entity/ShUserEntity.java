package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@SuppressWarnings("serial")
@TableName("sh_user")
public class ShUserEntity extends AppBaseEntity {


		            /**
             * 用户姓名
             */
            private String username;
			
            /**
             * 用户类型(01:买家;02:卖家)
             */
            private Integer userType;
			
            /**
             * 手机号
             */
            private String mobile;
			
            /**
             * 用户密码
             */
            private String password;
			
		            /**
             * 用户邮箱
             */
            private String email;
			
            /**
             * 积分
             */
            private BigDecimal integral;
			
            /**
             * 用户余额
             */
            private BigDecimal balanceAmt;
			
            /**
             * 上次登录ip
             */
            private String lastLoginIp;
			
            /**
             * 用户生日
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            private Date birthday;
			
            /**
             * 上次登录时间
             */
            private Date lastLoginTime;
			
            /**
             * 会员等级(1:默认;2：待定)
             */
            private Long userLevelId;
			
		            /**
             * 用户昵称
             */
            private String nickname;
			

		            /**
             * 性别
             */
            private Integer gender;
			
			
		            /**
             * 用户头像
             */
            private String avatarUrl;
			
		            /**
             * 所属城市
             */
            private String city;
			
            /**
             * 微信openid
             */
            private String openid;
			
		    /**
             * 省份
             */
            private String province;
			
	        
	        
	        
	        
	                    /**
             * 设置：用户姓名
             */
            public void setUsername(String username) {
                this.username = username;
            }
            /**
             * 获取：用户姓名
             */
            public String getUsername() {
                return username;
            }
		
	                    /**
             * 设置：用户类型(01:买家;02:卖家)
             */
            public void setUserType(Integer userType) {
                this.userType = userType;
            }
            /**
             * 获取：用户类型(01:买家;02:卖家)
             */
            public Integer getUserType() {
                return userType;
            }
		
	                    /**
             * 设置：手机号
             */
            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
            /**
             * 获取：手机号
             */
            public String getMobile() {
                return mobile;
            }
		
	                    /**
             * 设置：用户密码
             */
            public void setPassword(String password) {
                this.password = password;
            }
            /**
             * 获取：用户密码
             */
            public String getPassword() {
                return password;
            }
		
	                    /**
             * 设置：用户邮箱
             */
            public void setEmail(String email) {
                this.email = email;
            }
            /**
             * 获取：用户邮箱
             */
            public String getEmail() {
                return email;
            }
		
	                    /**
             * 设置：积分
             */
            public void setIntegral(BigDecimal integral) {
                this.integral = integral;
            }
            /**
             * 获取：积分
             */
            public BigDecimal getIntegral() {
                return integral;
            }
		
	                    /**
             * 设置：用户余额
             */
            public void setBalanceAmt(BigDecimal balanceAmt) {
                this.balanceAmt = balanceAmt;
            }
            /**
             * 获取：用户余额
             */
            public BigDecimal getBalanceAmt() {
                return balanceAmt;
            }
		
	                    /**
             * 设置：上次登录ip
             */
            public void setLastLoginIp(String lastLoginIp) {
                this.lastLoginIp = lastLoginIp;
            }
            /**
             * 获取：上次登录ip
             */
            public String getLastLoginIp() {
                return lastLoginIp;
            }
		
	                    /**
             * 设置：用户生日
             */
            public void setBirthday(Date birthday) {
                this.birthday = birthday;
            }
            /**
             * 获取：用户生日
             */
            public Date getBirthday() {
                return birthday;
            }
		
	                    /**
             * 设置：上次登录时间
             */
            public void setLastLoginTime(Date lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }
            /**
             * 获取：上次登录时间
             */
            public Date getLastLoginTime() {
                return lastLoginTime;
            }
		
	                    /**
             * 设置：所属用户组(01:默认;02：待定)
             */
            public void setUserLevelId(Long userLevelId) {
                this.userLevelId = userLevelId;
            }
            /**
             * 获取：所属用户组(01:默认;02：待定)
             */
            public Long getUserLevelId() {
                return userLevelId;
            }
		
	                    /**
             * 设置：用户昵称
             */
            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
            /**
             * 获取：用户昵称
             */
            public String getNickname() {
                return nickname;
            }
		
	                    /**
             * 设置：性别
             */
            public void setGender(Integer gender) {
                this.gender = gender;
            }
            /**
             * 获取：性别
             */
            public Integer getGender() {
                return gender;
            }
		
	        
	                    /**
             * 设置：
             */
            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }
            /**
             * 获取：
             */
            public String getAvatarUrl() {
                return avatarUrl;
            }
		
	                    /**
             * 设置：
             */
            public void setCity(String city) {
                this.city = city;
            }
            /**
             * 获取：
             */
            public String getCity() {
                return city;
            }
		
	                    /**
             * 设置：
             */
            public void setOpenid(String openid) {
                this.openid = openid;
            }
            /**
             * 获取：
             */
            public String getOpenid() {
                return openid;
            }
		
	                    /**
             * 设置：
             */
            public void setProvince(String province) {
                this.province = province;
            }
            /**
             * 获取：
             */
            public String getProvince() {
                return province;
            }
		
	}
