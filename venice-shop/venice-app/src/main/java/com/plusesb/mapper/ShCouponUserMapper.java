package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.dto.ShCouponDTO;
import com.plusesb.entity.ShCouponUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 优惠券客户关系
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@Mapper
public interface ShCouponUserMapper extends SuperMapper<ShCouponUserEntity> {

    List<ShCouponUserEntity> queryUserCouponList(Map param);

    void updateTimeOutStatus(Long userId);

    List<ShCouponDTO> findByUserIdAndCouponStatus(@Param("param") Map param);

    List<ShCouponDTO> findPageByUserIdAndStatus(Page page, @Param("param")Map param);

}
