package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShSpecificationEntity;
import com.plusesb.service.ShSpecificationService;
import com.plusesb.mapper.ShSpecificationMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("shSpecificationService")
public class ShSpecificationServiceImpl extends BaseServiceImpl<ShSpecificationMapper, ShSpecificationEntity> implements ShSpecificationService {


    @Override
    public void deleteByGoodsId(Long id) {

        Map map = new HashMap();
        map.put("goods_id",id);
        baseMapper.deleteByMap(map);
    }

    @Override
    public List<ShSpecificationEntity> findByGoodsId(Long id) {
        return baseMapper.selectList(new QueryWrapper<ShSpecificationEntity>().eq("goods_id",id));
    }
}
