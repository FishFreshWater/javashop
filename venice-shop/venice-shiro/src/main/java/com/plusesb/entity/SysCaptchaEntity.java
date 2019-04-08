
package com.plusesb.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 系统验证码
 * @author linyuchi
 * @since 2.0.0 2018-02-10
 */
@TableName("sys_captcha")
public class SysCaptchaEntity extends BaseEntity {


    /**
     * uuid
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
