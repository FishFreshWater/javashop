package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.ShCouponDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.entity.ShCouponUserEntity;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.enums.CouponStatue;
import com.plusesb.mapper.ShCouponUserMapper;
import com.plusesb.service.ShCouponService;
import com.plusesb.service.ShCouponUserService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.DateUtils;
import com.plusesb.utils.R;
import com.plusesb.utils.ShOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("shCouponUserService")
public class ShCouponUserServiceImpl extends BaseServiceImpl<ShCouponUserMapper, ShCouponUserEntity> implements ShCouponUserService {

    @Autowired
    private ShCouponService shCouponService;

    @Override
    public List<ShCouponUserEntity> queryUserCouponList(Map param) {
        return baseMapper.queryUserCouponList(param);
    }

    @Override
    public void updateTimeOutStatus(Long userId) {
        baseMapper.updateTimeOutStatus(userId);
    }

    @Override
    public R exchange(Long userId, Long couponId) {

        List<ShCouponUserEntity> shCouponUserEntities = baseMapper.selectList(new QueryWrapper<ShCouponUserEntity>()
                .eq("user_id",userId).eq("coupon_id",couponId));
        if (shCouponUserEntities.size()>0){
            return R.error("用户已经领取过该优惠券");
        }
        ShCouponEntity shCouponEntity = shCouponService.getById(couponId);

        ShCouponUserEntity shCouponUserEntity = new ShCouponUserEntity();
        shCouponUserEntity.setAppid(shCouponEntity.getAppid());
        shCouponUserEntity.setCouponStatus(CouponStatue.USEABLE.getValue());
        shCouponUserEntity.setEnabled(1);
        shCouponUserEntity.setUserId(userId);
        shCouponUserEntity.setEffectTime(new Date());
        shCouponUserEntity.setExpirTime(DateUtils.addDateDays(new Date(),shCouponEntity.getDays()));
        shCouponUserEntity.setCouponId(couponId);
        shCouponUserEntity.setCouponNumber(ShOrderUtils.getCouponNumber(userId));
        baseMapper.insert(shCouponUserEntity);
        return R.ok();
    }

    @Override
    public PageDTO<ShCouponDTO> findPageByUserIdAndStatus(SearchDTO simpleSearchDTO) {

        Page<ShCouponDTO> page = new Page<>(simpleSearchDTO.getPageIndex(), simpleSearchDTO.getPageSize());
        Map map = new HashMap();
        map.put("userId",simpleSearchDTO.getDataByFiled("userId"));
        map.put("status",Integer.parseInt(simpleSearchDTO.getDataByFiled("status").toString()));
        page.setRecords(this.baseMapper.findPageByUserIdAndStatus(page, map));
        return new PageDTO<>(page);
    }

    @Override
    public ShCouponDTO findByUserIdAndCouponId(Long userId, Long userCouponId) {

        Map map = new HashMap();
        map.put("userId",userId);
        map.put("userCouponId",userCouponId);
        map.put("status",CouponStatue.USEABLE.getValue());
        List<ShCouponDTO> list = baseMapper.findByUserIdAndCouponStatus(map);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<ShCouponDTO> findByUserIdAndCouponStatus(Long userId, int status) {
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("status",status);
        return baseMapper.findByUserIdAndCouponStatus(map);
    }
}

