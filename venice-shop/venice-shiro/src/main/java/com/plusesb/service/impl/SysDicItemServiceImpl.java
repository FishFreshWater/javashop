package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.SysDicItemEntity;
import com.plusesb.service.SysDicItemService;
import com.plusesb.mapper.SysDicItemMapper;

import java.util.List;


@Service("sysDicItemService")
public class SysDicItemServiceImpl extends BaseServiceImpl<SysDicItemMapper, SysDicItemEntity> implements SysDicItemService {


    @Override
    public List<SysDicItemEntity> findByTypeId(Long typeId) {
        return baseMapper.selectList(new QueryWrapper<SysDicItemEntity>().eq("type_id",typeId));
    }
}
