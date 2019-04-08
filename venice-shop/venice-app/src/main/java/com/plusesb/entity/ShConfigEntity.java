package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.math.BigDecimal;

/**
 * 商城配置
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-29 04:54:00
 */
@SuppressWarnings("serial")
@TableName("sh_config")
public class ShConfigEntity extends AppBaseEntity {
                            /**
         * 商户名称
         */
        private String mchId;
        /**
         * 微信ID
         */
        @TableField(exist = false)
        private String wxAppid;
        /**
         * 微信支付秘钥
         */
        private String paySignKey;
                /**
         * 证书存放地址
         */
        private String certPath;

        /**
         *订单运费
         */
        private BigDecimal freightPrice;

        /**
         * 满多少包邮
         */
        private BigDecimal freeFreightPrice;
        /**
         * 满额减少
         */
        private BigDecimal fullCutPrice;
                /**
         * 减额
         */
        private BigDecimal cutPrice;

        /**
         *支付成功提醒模版
         */
        private String payedNotice;
        /**
         *订单发货提醒提醒模版
         */
        private String sendNotice;
        /**
         *退款申请提醒
         */
        private String refundNotice;
        /**
         *退货申请提醒
         */
        private String returnNotice;

        @TableField(exist = false)
        private SSLConnectionSocketFactory sslcsf;

        public SSLConnectionSocketFactory getSslcsf() {
            return sslcsf;
        }

        public void setSslcsf(SSLConnectionSocketFactory sslcsf) {
            this.sslcsf = sslcsf;
        }

        public String getWxAppid() {
            return wxAppid;
        }

        public void setWxAppid(String wxAppid) {
            this.wxAppid = wxAppid;
        }

        public String getPayedNotice() {
            return payedNotice;
        }

        public void setPayedNotice(String payedNotice) {
            this.payedNotice = payedNotice;
        }

        public String getSendNotice() {
            return sendNotice;
        }

        public void setSendNotice(String sendNotice) {
            this.sendNotice = sendNotice;
        }

        public String getRefundNotice() {
            return refundNotice;
        }

        public void setRefundNotice(String refundNotice) {
            this.refundNotice = refundNotice;
        }

        public String getReturnNotice() {
            return returnNotice;
        }

        public void setReturnNotice(String returnNotice) {
            this.returnNotice = returnNotice;
        }

        /**
         * 设置：商户名称
         */
        public void setMchId(String mchId) {
            this.mchId = mchId;
        }
        /**
         * 获取：商户名称
         */
        public String getMchId() {
            return mchId;
        }
    
            /**
         * 设置：微信支付秘钥
         */
        public void setPaySignKey(String paySignKey) {
            this.paySignKey = paySignKey;
        }
        /**
         * 获取：微信支付秘钥
         */
        public String getPaySignKey() {
            return paySignKey;
        }
    
            /**
         * 设置：证书存放地址
         */
        public void setCertPath(String certPath) {
            this.certPath = certPath;
        }
        /**
         * 获取：证书存放地址
         */
        public String getCertPath() {
            return certPath;
        }

        public BigDecimal getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(BigDecimal freightPrice) {
            this.freightPrice = freightPrice;
        }

        public BigDecimal getFreeFreightPrice() {
            return freeFreightPrice;
        }

        public void setFreeFreightPrice(BigDecimal freeFreightPrice) {
            this.freeFreightPrice = freeFreightPrice;
        }

        /**
         * 设置：满额减少
         */
        public void setFullCutPrice(BigDecimal fullCutPrice) {
            this.fullCutPrice = fullCutPrice;
        }
        /**
         * 获取：满额减少
         */
        public BigDecimal getFullCutPrice() {
            return fullCutPrice;
        }
    
            /**
         * 设置：减额
         */
        public void setCutPrice(BigDecimal cutPrice) {
            this.cutPrice = cutPrice;
        }
        /**
         * 获取：减额
         */
        public BigDecimal getCutPrice() {
            return cutPrice;
        }
    
}
