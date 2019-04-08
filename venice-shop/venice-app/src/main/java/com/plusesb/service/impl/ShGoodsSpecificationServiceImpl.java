package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.omg.CORBA.BAD_CONTEXT;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShGoodsSpecificationEntity;
import com.plusesb.service.ShGoodsSpecificationService;
import com.plusesb.mapper.ShGoodsSpecificationMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("shGoodsSpecificationService")
public class ShGoodsSpecificationServiceImpl extends BaseServiceImpl<ShGoodsSpecificationMapper, ShGoodsSpecificationEntity> implements ShGoodsSpecificationService {


    @Override
    public void deleteByGoodsId(Long goodsId) {

        Map map = new HashMap();
        map.put("goods_id",goodsId);
        baseMapper.deleteByMap(map);
    }

    @Override
    public List<ShGoodsSpecificationEntity> findByGoodsIdAndSpecId(Long goodsId, Long specId) {
        return baseMapper.selectList(new QueryWrapper<ShGoodsSpecificationEntity>().eq("goods_id",goodsId).eq("specification_id",specId));
    }
}
