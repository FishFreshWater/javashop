package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShGoodsEntity;

import java.util.List;
import java.util.Map;


/**
 * 商品信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
public interface ShGoodsService extends BaseService<ShGoodsEntity>,IService<ShGoodsEntity> {


    ShGoodsEntity updateIsOnSaleById(ShGoodsEntity shGoodsEntity);

    /**
     * 多表关联查询商品
     * @param map
     * @return
     */
    PageDTO<ShGoodsEntity> findBySql(Map<String,Object> map);



    /**
     * 类别下的商品
     * @param childCategoryIds 子类别
     * @param appId
     * @return
     */
    List<ShGoodsEntity> findByCategoryIdsAndAppid(List<Long> childCategoryIds, String appId);


    /**
     * 保存商品和产品
     * @param shGoods
     */
    void saveAndProduct(ShGoodsEntity shGoods);

    void updateIsDeleteByIds(List<Long> longs);

    /**
     * 支付成功后增加销量
     * @param id
     */
    void addSellVolumeByOrderId(Long id);
}

