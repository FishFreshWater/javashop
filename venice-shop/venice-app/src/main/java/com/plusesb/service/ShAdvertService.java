package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShAdvertEntity;

import java.util.List;

/**
 * 广告
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-16 16:06:24
 */
public interface ShAdvertService extends BaseService<ShAdvertEntity>,IService<ShAdvertEntity> {

    /**
     * 更新是否显示
     * @param msCaseInfo
     */
    void updateStatus(ShAdvertEntity msCaseInfo);

    /**
     * 首页显示广告
     * @return
     * @param appid
     */
    List<ShAdvertEntity> findByIndex(String appid);
}

