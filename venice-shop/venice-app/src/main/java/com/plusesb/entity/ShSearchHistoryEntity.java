package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 搜索历史
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@SuppressWarnings("serial")
@TableName("sh_search_history")
public class ShSearchHistoryEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 会员Id
             */
            private Long userId;
			
		            /**
             * 关键字
             */
            private String keyword;
			

	        
	        
	        
	        

	                    /**
             * 设置：会员Id
             */
            public void setUserId(Long userId) {
                this.userId = userId;
            }
            /**
             * 获取：会员Id
             */
            public Long getUserId() {
                return userId;
            }
		
	                    /**
             * 设置：关键字
             */
            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }
            /**
             * 获取：关键字
             */
            public String getKeyword() {
                return keyword;
            }
		

	        
	}
