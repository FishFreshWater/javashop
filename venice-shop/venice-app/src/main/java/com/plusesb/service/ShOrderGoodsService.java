package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShOrderGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单商品关系
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShOrderGoodsService extends BaseService<ShOrderGoodsEntity>,IService<ShOrderGoodsEntity> {

    /**
     * 通过订单ID查询订单商品
     * @param orderId
     * @return
     */
    List<ShOrderGoodsEntity> findByOrderId(Long orderId);
}

