package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@SuppressWarnings("serial")
@TableName("sh_topic")
public class ShTopicEntity extends AppBaseEntity {

		            /**
             * 活动主题
             */
            private String title;
			
		            /**
             * 化名
             */
            private String avatar;
			
		            /**
             * 活动条例图片
             */
            private String itemPicUrl;
			
		            /**
             * 子标题
             */
            private String subtitle;
			
		            /**
             * 阅读量
             */
            private String readCount;
			
		            /**
             * 场景图片链接
             */
            private String scenePicUrl;
			
		            /**
             * 活动内容
             */
            private String content;
			
		    /**
             * 活动价格
             */
            private BigDecimal priceInfo;

            /**
             * 主题关联商品
             */
            @TableField(exist = false)
            private List<ShGoodsEntity> shGoodsEntities;

            public List<ShGoodsEntity> getShGoodsEntities() {
                return shGoodsEntities;
            }

            public void setShGoodsEntities(List<ShGoodsEntity> shGoodsEntities) {
                this.shGoodsEntities = shGoodsEntities;
            }

            /**
             * 设置：活动主题
             */
            public void setTitle(String title) {
                this.title = title;
            }
            /**
             * 获取：活动主题
             */
            public String getTitle() {
                return title;
            }
		
	                    /**
             * 设置：化名
             */
            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
            /**
             * 获取：化名
             */
            public String getAvatar() {
                return avatar;
            }
		
	                    /**
             * 设置：活动条例图片
             */
            public void setItemPicUrl(String itemPicUrl) {
                this.itemPicUrl = itemPicUrl;
            }
            /**
             * 获取：活动条例图片
             */
            public String getItemPicUrl() {
                return itemPicUrl;
            }
		
	                    /**
             * 设置：子标题
             */
            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
            /**
             * 获取：子标题
             */
            public String getSubtitle() {
                return subtitle;
            }
		
	                    /**
             * 设置：阅读量
             */
            public void setReadCount(String readCount) {
                this.readCount = readCount;
            }
            /**
             * 获取：阅读量
             */
            public String getReadCount() {
                return readCount;
            }
		
	                    /**
             * 设置：场景图片链接
             */
            public void setScenePicUrl(String scenePicUrl) {
                this.scenePicUrl = scenePicUrl;
            }
            /**
             * 获取：场景图片链接
             */
            public String getScenePicUrl() {
                return scenePicUrl;
            }
		
	                    /**
             * 设置：活动内容
             */
            public void setContent(String content) {
                this.content = content;
            }
            /**
             * 获取：活动内容
             */
            public String getContent() {
                return content;
            }
		

	                    /**
             * 设置：活动价格
             */
            public void setPriceInfo(BigDecimal priceInfo) {
                this.priceInfo = priceInfo;
            }
            /**
             * 获取：活动价格
             */
            public BigDecimal getPriceInfo() {
                return priceInfo;
            }
		

	}
