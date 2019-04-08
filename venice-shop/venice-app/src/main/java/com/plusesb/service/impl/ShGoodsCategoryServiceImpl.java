package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.entity.ShGoodsCategoryEntity;
import com.plusesb.mapper.ShGoodsCategoryMapper;
import com.plusesb.service.ShGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("shGoodsCategoryService")
public class ShGoodsCategoryServiceImpl extends BaseServiceImpl<ShGoodsCategoryMapper, ShGoodsCategoryEntity> implements ShGoodsCategoryService {

    @Override
    public List<ShGoodsCategoryEntity> findByAppidAndParentId(String appId, long l) {
        return baseMapper.selectList(new QueryWrapper<ShGoodsCategoryEntity>()
                .eq("appid",appId).eq("parent_id",l));
    }

    @Override
    public List<ShGoodsCategoryEntity> findByAppidAndParentIdAndShowIndex(String appid, Long parentId, int showIndex) {
        return baseMapper.selectList(new QueryWrapper<ShGoodsCategoryEntity>()
                .eq("appid",appid).eq("parent_id",parentId).eq("show_index",showIndex));
    }
}
