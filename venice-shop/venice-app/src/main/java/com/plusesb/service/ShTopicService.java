package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShTopicEntity;

import java.util.List;
import java.util.Map;

/**
 * 活动
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
public interface ShTopicService extends BaseService<ShTopicEntity>,IService<ShTopicEntity> {

    /**
     * 查询话题关联商品
     * @param id
     * @return
     */
    List<ShGoodsEntity> findRelatedGoodsByTopicId(Long id);
}

