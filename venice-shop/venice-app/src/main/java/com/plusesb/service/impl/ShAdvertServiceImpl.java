package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.utils.BaseUtils;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShAdvertEntity;
import com.plusesb.service.ShAdvertService;
import com.plusesb.mapper.ShAdvertMapper;

import java.util.Date;
import java.util.List;


@Service("shAdvertService")
public class ShAdvertServiceImpl extends BaseServiceImpl<ShAdvertMapper, ShAdvertEntity> implements ShAdvertService {


    @Override
    public List<ShAdvertEntity> findByIndex(String appid) {
        return baseMapper.selectList(new QueryWrapper<ShAdvertEntity>().eq("display","1")
                .gt("end_time",BaseUtils.getDatetimeString(new Date())).eq("appid",appid)
                .orderByDesc("sort"));
    }

    @Override
    public void updateStatus(ShAdvertEntity dto) {
        ShAdvertEntity entity = new ShAdvertEntity();
        if(BaseUtils.isNotEmpty(dto.getId())){
            entity = baseMapper.selectById(dto.getId());
        }

        if(BaseUtils.isNotEmpty(entity)){
            entity.setDisplay(dto.getDisplay());
        }
        baseMapper.updateById(entity);
    }
}
