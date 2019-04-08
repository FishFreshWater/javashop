package com.plusesb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShCouponUserEntity;
import com.plusesb.entity.ShOrderEntity;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.service.ShCouponService;
import com.plusesb.mapper.ShCouponMapper;

import java.util.List;
import java.util.Map;


@Service("shCouponService")
public class ShCouponServiceImpl extends BaseServiceImpl<ShCouponMapper, ShCouponEntity> implements ShCouponService {


    @Override
    public PageDTO<ShCouponEntity> findPageByAppId(SearchDTO simpleSearchDTO) {

        Page<ShCouponEntity> page = new Page<>(simpleSearchDTO.getPageIndex(), simpleSearchDTO.getPageSize());
        page.setRecords(this.baseMapper.findPageByAppId(page, Long.parseLong(simpleSearchDTO.getDataByFiled("userId").toString()),simpleSearchDTO.getDataByFiled("appid").toString()));
        return new PageDTO<>(page);
    }

    @Override
    public void updateStatusById(Long id, Integer couponStatue) {
        baseMapper.updateStatusById(id,couponStatue);
    }
}
