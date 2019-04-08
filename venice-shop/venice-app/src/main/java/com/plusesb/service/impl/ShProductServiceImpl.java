package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShProductEntity;
import com.plusesb.service.ShProductService;
import com.plusesb.mapper.ShProductMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("shProductService")
public class ShProductServiceImpl extends BaseServiceImpl<ShProductMapper, ShProductEntity> implements ShProductService {


    @Override
    public List<ShProductEntity> findByGoodsIdAnIsDefault(Long id, int i) {
        return baseMapper.selectList(new QueryWrapper<ShProductEntity>().eq("goods_id",id).eq("is_default",i));
    }

    @Override
    public void deleteByGoodsId(Long goodsId) {

        Map map = new HashMap();
        map.put("goods_id",goodsId);
        baseMapper.deleteByMap(map);
    }
}
