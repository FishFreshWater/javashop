package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShProductEntity;

import java.util.List;

/**
 * 产品表
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShProductService extends BaseService<ShProductEntity>,IService<ShProductEntity> {

    /**
     * 获得默认产品
     * @param goodsId 商品
     * @param isDefault 是否默认 1默认 0 不默认
     * @return
     */
    List<ShProductEntity> findByGoodsIdAnIsDefault(Long goodsId, int isDefault);

    /**
     * 清空产品关联商品
     * @param goodsId 商品id
     */
    void deleteByGoodsId(Long goodsId);
}

