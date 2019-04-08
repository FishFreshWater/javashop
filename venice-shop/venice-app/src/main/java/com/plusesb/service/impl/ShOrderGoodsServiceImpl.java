package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShOrderGoodsEntity;
import com.plusesb.service.ShOrderGoodsService;
import com.plusesb.mapper.ShOrderGoodsMapper;

import java.util.List;


@Service("shOrderGoodsService")
public class ShOrderGoodsServiceImpl extends BaseServiceImpl<ShOrderGoodsMapper, ShOrderGoodsEntity> implements ShOrderGoodsService {


    @Override
    public List<ShOrderGoodsEntity> findByOrderId(Long orderId) {
        return baseMapper.selectList(new QueryWrapper<ShOrderGoodsEntity>().eq("order_id",orderId));
    }
}
