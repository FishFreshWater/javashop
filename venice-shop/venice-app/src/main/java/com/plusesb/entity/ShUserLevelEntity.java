package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 会员等级
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@SuppressWarnings("serial")
@TableName("sh_user_level")
public class ShUserLevelEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 会员等级名称
             */
            private String levelName;
			
		            /**
             * 折扣
             */
            private BigDecimal discount;
			
		            /**
             * 等级类型
             */
            private Integer levelType;
			
		            /**
             * 所需消费金额
             */
            private BigDecimal needConsumerAmt;
			
		            /**
             * 会员数
             */
            private Integer userNumber;
			
		            /**
             * 会员等级变更方式(01:通过管理员;02:根据消费金额升降;03:根据消费金额只升不降)
             */
            private Integer levelChangeType;
			
			
	        
	        
	        
	        
	                    /**
             * 设置：会员等级名称
             */
            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }
            /**
             * 获取：会员等级名称
             */
            public String getLevelName() {
                return levelName;
            }
		
	                    /**
             * 设置：折扣
             */
            public void setDiscount(BigDecimal discount) {
                this.discount = discount;
            }
            /**
             * 获取：折扣
             */
            public BigDecimal getDiscount() {
                return discount;
            }
		
	                    /**
             * 设置：等级类型
             */
            public void setLevelType(Integer levelType) {
                this.levelType = levelType;
            }
            /**
             * 获取：等级类型
             */
            public Integer getLevelType() {
                return levelType;
            }
		
	                    /**
             * 设置：所需消费金额
             */
            public void setNeedConsumerAmt(BigDecimal needConsumerAmt) {
                this.needConsumerAmt = needConsumerAmt;
            }
            /**
             * 获取：所需消费金额
             */
            public BigDecimal getNeedConsumerAmt() {
                return needConsumerAmt;
            }
		
	                    /**
             * 设置：会员数
             */
            public void setUserNumber(Integer userNumber) {
                this.userNumber = userNumber;
            }
            /**
             * 获取：会员数
             */
            public Integer getUserNumber() {
                return userNumber;
            }
		
	                    /**
             * 设置：会员等级变更方式(01:通过管理员;02:根据消费金额升降;03:根据消费金额只升不降)
             */
            public void setLevelChangeType(Integer levelChangeType) {
                this.levelChangeType = levelChangeType;
            }
            /**
             * 获取：会员等级变更方式(01:通过管理员;02:根据消费金额升降;03:根据消费金额只升不降)
             */
            public Integer getLevelChangeType() {
                return levelChangeType;
            }
		
	        
	}
