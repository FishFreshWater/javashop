package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 关键字
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_keywords")
public class ShKeywordsEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 关键字
             */
            private String keyword;
			
		            /**
             * 关键词的跳转链接
             */
            private String schemeUrl;
			
		            /**
             * 热销
             */
            private Integer isHot;
			
		            /**
             * 默认
             */
            private Integer isDefault;
			
		            /**
             * 显示
             */
            private Integer isShow;
			
		            /**
             * 排序
             */
            private Integer sortOrder;
			
		            /**
             * 类型
             */
            private Integer type;
			
			
	        
	        
	        
	        
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
		
	                    /**
             * 设置：关键词的跳转链接
             */
            public void setSchemeUrl(String schemeUrl) {
                this.schemeUrl = schemeUrl;
            }
            /**
             * 获取：关键词的跳转链接
             */
            public String getSchemeUrl() {
                return schemeUrl;
            }
		
	                    /**
             * 设置：热销
             */
            public void setIsHot(Integer isHot) {
                this.isHot = isHot;
            }
            /**
             * 获取：热销
             */
            public Integer getIsHot() {
                return isHot;
            }
		
	                    /**
             * 设置：默认
             */
            public void setIsDefault(Integer isDefault) {
                this.isDefault = isDefault;
            }
            /**
             * 获取：默认
             */
            public Integer getIsDefault() {
                return isDefault;
            }
		
	                    /**
             * 设置：显示
             */
            public void setIsShow(Integer isShow) {
                this.isShow = isShow;
            }
            /**
             * 获取：显示
             */
            public Integer getIsShow() {
                return isShow;
            }
		
	                    /**
             * 设置：排序
             */
            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }
            /**
             * 获取：排序
             */
            public Integer getSortOrder() {
                return sortOrder;
            }
		
	                    /**
             * 设置：类型
             */
            public void setType(Integer type) {
                this.type = type;
            }
            /**
             * 获取：类型
             */
            public Integer getType() {
                return type;
            }
		
	        
	}
