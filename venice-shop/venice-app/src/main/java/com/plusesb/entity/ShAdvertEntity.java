package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


/**
 * 广告
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-16 16:06:24
 */
@SuppressWarnings("serial")
@TableName("sh_advert")
public class ShAdvertEntity extends AppBaseEntity {
                            /**
         * 名字
         */
        private String name;
                /**
         * 链接
         */
        private String link;
                /**
         * 内容
         */
        private String content;
                /**
         * 排序
         */
        private Integer sort;
                /**
         * 媒体类型
         */
        private Integer mediaType;

        /**
         *图片地址
         */
        private String imageUrl;

                /**
         * 失效时间
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;
        /**
         * 启用
         */
        private Integer display;


        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

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
    
            /**
         * 设置：链接
         */
        public void setLink(String link) {
            this.link = link;
        }
        /**
         * 获取：链接
         */
        public String getLink() {
            return link;
        }
    
            /**
         * 设置：内容
         */
        public void setContent(String content) {
            this.content = content;
        }
        /**
         * 获取：内容
         */
        public String getContent() {
            return content;
        }
    
            /**
         * 设置：排序
         */
        public void setSort(Integer sort) {
            this.sort = sort;
        }
        /**
         * 获取：排序
         */
        public Integer getSort() {
            return sort;
        }
    
            /**
         * 设置：媒体类型
         */
        public void setMediaType(Integer mediaType) {
            this.mediaType = mediaType;
        }
        /**
         * 获取：媒体类型
         */
        public Integer getMediaType() {
            return mediaType;
        }
    
            /**
         * 设置：失效时间
         */
        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
        /**
         * 获取：失效时间
         */
        public Date getEndTime() {
            return endTime;
        }
    
            /**
         * 设置：启用
         */
        public Integer getDisplay() {
            return display;
        }

        public void setDisplay(Integer display) {
            this.display = display;
        }
}
