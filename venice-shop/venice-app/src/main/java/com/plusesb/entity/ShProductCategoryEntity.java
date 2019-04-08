package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 商品产品类别
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_product_category")
public class ShProductCategoryEntity extends AppBaseEntity {

	
			
			
			
			
		            /**
             * 产品类型
             */
            private Integer productType;
			
		            /**
             * 产品名字
             */
            private String name;
			
		            /**
             * 排序
             */
            private Integer sortOrder;
			
			
	        
	        
	        
	        
	                    /**
             * 设置：产品类型
             */
            public void setProductType(Integer productType) {
                this.productType = productType;
            }
            /**
             * 获取：产品类型
             */
            public Integer getProductType() {
                return productType;
            }
		
	                    /**
             * 设置：产品名字
             */
            public void setName(String name) {
                this.name = name;
            }
            /**
             * 获取：产品名字
             */
            public String getName() {
                return name;
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
		
	        
	}
