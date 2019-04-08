package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.plusesb.dto.SearchFieldDTO;
import com.plusesb.dto.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShCartEntity;
import com.plusesb.service.ShCartService;
import com.plusesb.mapper.ShCartMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("shCartService")
public class ShCartServiceImpl extends BaseServiceImpl<ShCartMapper, ShCartEntity> implements ShCartService {


    @Override
    public List<ShCartEntity> findByUserId(Long userId) {

        Map mapParam = new HashMap();
        mapParam.put("user_id",userId);

        return baseMapper.findByUserId(mapParam);
    }

    @Override
    public void deleteByUserAndProductIds(Long userId, String[] productIds) {
        baseMapper.deleteByUserAndProductIds(userId,productIds);
    }

    @Override
    public void updateCheck(String[] productIds, Integer isChecked, Long userId) {
        baseMapper.updateCheck(productIds,isChecked,userId);
    }

    @Override
    public List<ShCartEntity> findByUserIdAndChecked(Long userId, int checked) {
        return baseMapper.selectList(new QueryWrapper<ShCartEntity>().eq("user_id",userId)
                .eq("checked",checked));
    }

//    @Override
//    public List<ShCartEntity> findAllBySimpleSearch(SearchDTO simpleSearchDTO) {
//        Map<String,Object> map = Maps.newHashMap();
//        List<SearchFieldDTO> searchFieldDTOS = simpleSearchDTO.getFields();
//        for(SearchFieldDTO searchFieldDTO : searchFieldDTOS){
//            map.put(searchFieldDTO.getFiled(),searchFieldDTO.getData());
//        }
//
//        return shCartMapper.findBySql(map);
//    }
}
