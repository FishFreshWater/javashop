package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShBrandEntity;
import com.plusesb.service.ShBrandService;
import com.plusesb.mapper.ShBrandMapper;

import java.util.List;


@Service("shBrandService")
public class ShBrandServiceImpl extends BaseServiceImpl<ShBrandMapper, ShBrandEntity> implements ShBrandService {


    @Override
    public List<ShBrandEntity> findByIndex(String appId) {
        return baseMapper.selectList(new QueryWrapper<ShBrandEntity>().eq("is_new","1").orderByDesc("sort_order"));
    }
}
