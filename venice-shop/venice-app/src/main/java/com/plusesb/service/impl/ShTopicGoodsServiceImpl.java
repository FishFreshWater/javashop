package com.plusesb.service.impl;

import com.google.common.collect.Maps;
import com.plusesb.entity.ShGoodsEntity;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShTopicGoodsEntity;
import com.plusesb.service.ShTopicGoodsService;
import com.plusesb.mapper.ShTopicGoodsMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("shTopicGoodsService")
public class ShTopicGoodsServiceImpl extends BaseServiceImpl<ShTopicGoodsMapper, ShTopicGoodsEntity> implements ShTopicGoodsService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<ShGoodsEntity> shGoodsEntities, Long id, String appid) {

        Map<String, Object> columnMap = Maps.newHashMap();
        columnMap.put("topic_id", id);
        baseMapper.deleteByMap(columnMap);
        shGoodsEntities.forEach(goodsEntity -> {
            ShTopicGoodsEntity topicGoodsEntity = new ShTopicGoodsEntity();
            topicGoodsEntity.setGoodsId(goodsEntity.getId());
            topicGoodsEntity.setTopicId(id);
            topicGoodsEntity.setAppid(appid);
            baseMapper.insert(topicGoodsEntity);
        });
    }

}
