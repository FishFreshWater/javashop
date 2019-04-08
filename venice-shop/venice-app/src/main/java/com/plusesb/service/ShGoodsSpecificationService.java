package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShGoodsSpecificationEntity;
import com.plusesb.entity.ShSpecificationEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品规格
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-11 19:53:38
 */
public interface ShGoodsSpecificationService extends BaseService<ShGoodsSpecificationEntity>,IService<ShGoodsSpecificationEntity> {

    /**
     * 删除
     * @param goodsId 商品ID
     */
    void deleteByGoodsId(Long goodsId);


    /**
     * 通过商品ID和规格ID查询商品规格表
     * @param goodsId 商品ID
     * @param specId 规格ID
     * @return
     */
    List<ShGoodsSpecificationEntity> findByGoodsIdAndSpecId(Long goodsId, Long specId);
}

