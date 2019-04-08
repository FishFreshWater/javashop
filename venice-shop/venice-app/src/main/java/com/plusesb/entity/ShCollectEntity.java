package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


/**
 * 用户收藏
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-29 02:59:11
 */
@SuppressWarnings("serial")
@TableName("sh_collect")
public class ShCollectEntity extends AppBaseEntity {
                            /**
         * 用户ID
         */
        private Long userId;
                /**
         * 商品ID
         */
        private Long goodsId;
                /**
         * 添加时间
         */
        private Date addTime;



       @TableField(exist = false)
        private String goodsName;

       @TableField(exist = false)
        private String goodsBrief;

        @TableField(exist = false)
        private String retailPrice;

        @TableField(exist = false)
        private String nickname;

        @TableField(exist = false)
        private String primaryPicUrl;


    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
         * 设置：名字
         */
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        /**
         * 获取：名字
         */
        public Long getUserId() {
            return userId;
        }
    
            /**
         * 设置：链接
         */
        public void setGoodsId(Long goodsId) {
            this.goodsId = goodsId;
        }
        /**
         * 获取：链接
         */
        public Long getGoodsId() {
            return goodsId;
        }
    
            /**
         * 设置：内容
         */
        public void setAddTime(Date addTime) {
            this.addTime = addTime;
        }
        /**
         * 获取：内容
         */
        public Date getAddTime() {
            return addTime;
        }
    
}
