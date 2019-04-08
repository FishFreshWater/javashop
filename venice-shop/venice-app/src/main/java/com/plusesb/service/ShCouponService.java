package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.entity.ShCouponUserEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 优惠券信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShCouponService extends BaseService<ShCouponEntity>,IService<ShCouponEntity> {


    /**
     * 查询优惠券列表
     * @param simpleSearchDTO
     * @return
     */
    PageDTO<ShCouponEntity> findPageByAppId(SearchDTO simpleSearchDTO);

    /**
     * 上下架优惠券
     * @param id
     * @param couponStatue
     */
    void updateStatusById(Long id, Integer couponStatue);
}

