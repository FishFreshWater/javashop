package com.plusesb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.entity.ShCouponUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 优惠券信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@Mapper
public interface ShCouponMapper extends SuperMapper<ShCouponEntity> {


    List<ShCouponEntity> findPageByAppId(Page<ShCouponEntity> page, @Param("userId") Long userId, @Param("appid") String appid);

    void updateStatusById(@Param("id") Long id, @Param("status") Integer couponStatue);
}
