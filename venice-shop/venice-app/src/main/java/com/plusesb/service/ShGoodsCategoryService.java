package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShGoodsCategoryEntity;

import java.util.List;

/**
 * 导航
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShGoodsCategoryService extends BaseService<ShGoodsCategoryEntity>,IService<ShGoodsCategoryEntity> {

    /**
     * 查询父类别分类
     * @param appId
     * @param parentId
     * @return
     */
    List<ShGoodsCategoryEntity> findByAppidAndParentId(String appId, long parentId);

    /**
     * 查询父类别分类
     * @param appid appid
     * @param parentId 父节点ID
     * @param showIndex 展示在首页
     * @return
     */
    List<ShGoodsCategoryEntity> findByAppidAndParentIdAndShowIndex(String appid, Long parentId, int showIndex);
}

