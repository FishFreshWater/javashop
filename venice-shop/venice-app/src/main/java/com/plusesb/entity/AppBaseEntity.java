package com.plusesb.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 实体父类
 */
public class AppBaseEntity<T extends Model> extends BaseEntity<T> {

    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

}
