package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.ShCouponDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.entity.ShCouponUserEntity;
import com.plusesb.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 优惠券客户关系
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShCouponUserService extends BaseService<ShCouponUserEntity>,IService<ShCouponUserEntity> {

    /**
     * 查询用户优惠券
     * @param param
     * @return
     */
    List<ShCouponUserEntity> queryUserCouponList(Map param);

    /**
     * 更新优惠券过期数据
     * @param id
     */
    void updateTimeOutStatus(Long id);


    /**
     * 领取优惠券
     * @param userId
     * @param couponId
     * @return
     */
    R exchange(Long userId, Long couponId);

    /**
     * 查询用户优惠券
     * @param userId 用户
     * @param status 状态
     * @return
     */
    List<ShCouponDTO> findByUserIdAndCouponStatus(Long userId, int status);
    /**
     * 查询用户优惠券
     * @return
     */
    PageDTO<ShCouponDTO> findPageByUserIdAndStatus(SearchDTO simpleSearchDTO);

    /**
     * 用户选择优惠券数据
     * @param id
     * @param couponId
     * @return
     */
    ShCouponDTO findByUserIdAndCouponId(Long id, Long couponId);
}

