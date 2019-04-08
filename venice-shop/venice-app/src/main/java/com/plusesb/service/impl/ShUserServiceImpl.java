package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShUserService;
import com.plusesb.mapper.ShUserMapper;


@Service("shUserService")
public class ShUserServiceImpl extends BaseServiceImpl<ShUserMapper, ShUserEntity> implements ShUserService {
    @Override
    public ShUserEntity findByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<ShUserEntity>().eq("mobile",mobile));
    }

    @Override
    public ShUserEntity findByOpenId(String openid) {
        return baseMapper.selectOne(new QueryWrapper<ShUserEntity>().eq("openid",openid));
    }
}
