package com.plusesb.service.impl;

import org.springframework.stereotype.Service;
import com.plusesb.entity.ShSearchHistoryEntity;
import com.plusesb.service.ShSearchHistoryService;
import com.plusesb.mapper.ShSearchHistoryMapper;

import java.util.HashMap;
import java.util.Map;


@Service("shSearchHistoryService")
public class ShSearchHistoryServiceImpl extends BaseServiceImpl<ShSearchHistoryMapper, ShSearchHistoryEntity> implements ShSearchHistoryService {

    @Override
    public void deleteByUserId(Long userid) {

        Map map = new HashMap();
        map.put("user_id",userid);
        baseMapper.deleteByMap(map);

    }
}
