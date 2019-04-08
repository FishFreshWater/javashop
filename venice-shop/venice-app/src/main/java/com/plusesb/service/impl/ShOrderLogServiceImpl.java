package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShOrderLogEntity;
import com.plusesb.service.ShOrderLogService;
import com.plusesb.mapper.ShOrderLogMapper;

import java.util.Date;
import java.util.List;


@Service("shOrderLogService")
public class ShOrderLogServiceImpl extends BaseServiceImpl<ShOrderLogMapper, ShOrderLogEntity> implements ShOrderLogService {


    @Override
    public void saveOrderLog(Long id, String content, String userName) {

        ShOrderLogEntity orderLog = new ShOrderLogEntity();
        orderLog.setOrderId(id);
        orderLog.setContent(content);
        orderLog.setUserName(userName);
        orderLog.setCreateTime(new Date());
        baseMapper.insert(orderLog);
    }

    @Override
    public List<ShOrderLogEntity> findByOrderId(Long id) {
        return baseMapper.selectList(new QueryWrapper<ShOrderLogEntity>()
                .eq("order_id",id).orderByDesc("create_time"));
    }
}
