package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShOrderReturnLogEntity;
import com.plusesb.service.ShOrderReturnLogService;
import com.plusesb.mapper.ShOrderReturnLogMapper;

import java.util.List;


@Service("shOrderReturnLogService")
public class ShOrderReturnLogServiceImpl extends BaseServiceImpl<ShOrderReturnLogMapper, ShOrderReturnLogEntity> implements ShOrderReturnLogService {


    @Override
    public List<ShOrderReturnLogEntity> findByOrderReturnId(Long returnId) {
        return baseMapper.selectList(new QueryWrapper<ShOrderReturnLogEntity>().eq("order_return_id",returnId));
    }
}
