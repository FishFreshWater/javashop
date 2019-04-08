package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShAdvertEntity;
import com.plusesb.entity.ShBrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品品牌
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-19 19:09:24
 */
public interface ShBrandService extends BaseService<ShBrandEntity>,IService<ShBrandEntity> {

    /**
     * 查询首页展示的品牌列表
     * @param appId
     * @return
     */
    List<ShBrandEntity> findByIndex(String appId);
}

