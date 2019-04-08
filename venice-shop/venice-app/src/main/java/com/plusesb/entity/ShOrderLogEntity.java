package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 订单日志
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_order_log")
public class ShOrderLogEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 操作内容
             */
            private String content;
			
		            /**
             * 操作者
             */
            private String userName;
			
		            /**
             * 订单id
             */
            private Long orderId;
			
			
	        
	        
	        
	        
	                    /**
             * 设置：操作内容
             */
            public void setContent(String content) {
                this.content = content;
            }
            /**
             * 获取：操作内容
             */
            public String getContent() {
                return content;
            }
		
	                    /**
             * 设置：操作者
             */
            public void setUserName(String userName) {
                this.userName = userName;
            }
            /**
             * 获取：操作者
             */
            public String getUserName() {
                return userName;
            }
		
	                    /**
             * 设置：订单id
             */
            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }
            /**
             * 获取：订单id
             */
            public Long getOrderId() {
                return orderId;
            }
		
	        
	}
