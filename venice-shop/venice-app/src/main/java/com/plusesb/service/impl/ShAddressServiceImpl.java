package com.plusesb.service.impl;

import org.springframework.stereotype.Service;
import com.plusesb.entity.ShAddressEntity;
import com.plusesb.service.ShAddressService;
import com.plusesb.mapper.ShAddressMapper;


@Service("shAddressService")
public class ShAddressServiceImpl extends BaseServiceImpl<ShAddressMapper, ShAddressEntity> implements ShAddressService {


    @Override
    public void updateNotDefault(Long id, Long userId) {
        baseMapper.updateNotDefault(id,userId);
    }
}
