package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShTopicGoodsEntity;
import com.plusesb.service.ShTopicGoodsService;
import com.plusesb.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShTopicEntity;
import com.plusesb.service.ShTopicService;
import com.plusesb.mapper.ShTopicMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("shTopicService")
public class ShTopicServiceImpl extends BaseServiceImpl<ShTopicMapper, ShTopicEntity> implements ShTopicService {

    @Override
    public List<ShGoodsEntity> findRelatedGoodsByTopicId(Long id) {
        return baseMapper.findRelatedGoodsByTopicId(id);
    }
}
