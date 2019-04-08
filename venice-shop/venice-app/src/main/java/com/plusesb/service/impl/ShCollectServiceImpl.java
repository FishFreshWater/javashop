package com.plusesb.service.impl;

import com.plusesb.entity.ShCollectEntity;
import com.plusesb.mapper.ShCollectMapper;
import com.plusesb.service.ShCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("shCollectService")
public class ShCollectServiceImpl extends BaseServiceImpl<ShCollectMapper, ShCollectEntity> implements ShCollectService {

    @Autowired
    private ShCollectMapper shCollectMapper;


    public List<ShCollectEntity> findAllByMap(Map<String,Object> map) {
        return shCollectMapper.findBySql(map);
    }


}
