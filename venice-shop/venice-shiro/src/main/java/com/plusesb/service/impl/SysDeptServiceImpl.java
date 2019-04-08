package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.SysDeptEntity;
import com.plusesb.service.SysDeptService;
import com.plusesb.mapper.SysDeptMapper;

import java.util.List;


@Service("sysDeptService")
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {

    @Override
    public List<SysDeptEntity> findByParentId(Long deptId) {
        return baseMapper.selectList(new QueryWrapper<SysDeptEntity>().eq("parent_id",deptId));
    }
}
