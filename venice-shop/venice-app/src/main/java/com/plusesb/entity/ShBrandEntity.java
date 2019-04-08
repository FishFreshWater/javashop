package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品品牌
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-19 19:09:24
 */
@SuppressWarnings("serial")
@TableName("sh_brand")
public class ShBrandEntity extends AppBaseEntity<ShBrandEntity> {
        /**
         * 名字
         */
        private String name;
        /**
         * 缩略图图片
         */
        private String thumbnailUrl;
        /**
         * 内容
         */
        private String simpleDesc;
         /**
         * 图片
         */
        private String picUrl;
        /**
         * 排序
         */
        private Integer sortOrder;
                /**
         * 启用
         */
        private Integer isShow;
                /**
         * 失效时间
         */
        private BigDecimal floorPrice;
                /**
         * 启用
         */
        private Integer isNew;

            /**
         * 设置：名字
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * 获取：名字
         */
        public String getName() {
            return name;
        }


        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        /**
         * 设置：内容
         */
        public void setSimpleDesc(String simpleDesc) {
            this.simpleDesc = simpleDesc;
        }
        /**
         * 获取：内容
         */
        public String getSimpleDesc() {
            return simpleDesc;
        }
    
            /**
         * 设置：图片
         */
        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
        /**
         * 获取：图片
         */
        public String getPicUrl() {
            return picUrl;
        }
    
            /**
         * 设置：失效时间
         */
        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }
        /**
         * 获取：失效时间
         */
        public Integer getSortOrder() {
            return sortOrder;
        }
    
            /**
         * 设置：启用
         */
        public void setIsShow(Integer isShow) {
            this.isShow = isShow;
        }
        /**
         * 获取：启用
         */
        public Integer getIsShow() {
            return isShow;
        }
    
            /**
         * 设置：失效时间
         */
        public void setFloorPrice(BigDecimal floorPrice) {
            this.floorPrice = floorPrice;
        }
        /**
         * 获取：失效时间
         */
        public BigDecimal getFloorPrice() {
            return floorPrice;
        }
    
            /**
         * 设置：启用
         */
        public void setIsNew(Integer isNew) {
            this.isNew = isNew;
        }
        /**
         * 获取：启用
         */
        public Integer getIsNew() {
            return isNew;
        }
    
}
