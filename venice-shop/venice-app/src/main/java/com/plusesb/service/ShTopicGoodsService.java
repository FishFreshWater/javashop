package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShTopicGoodsEntity;

import java.util.List;

/**
 * 主题管理商品
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-09 11:27:53
 */
public interface ShTopicGoodsService extends BaseService<ShTopicGoodsEntity>,IService<ShTopicGoodsEntity> {
    /**
     * 保存对照表
     * @param shGoodsEntities
     * @param id
     * @param appid
     */
    void saveList(List<ShGoodsEntity> shGoodsEntities, Long id, String appid);

}

