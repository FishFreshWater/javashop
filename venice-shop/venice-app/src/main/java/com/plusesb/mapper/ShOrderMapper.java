package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.entity.ShOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@Mapper
public interface ShOrderMapper extends SuperMapper<ShOrderEntity> {

    List<ShOrderEntity> findPageBySql(Page page, @Param("params") Map map);

    List<ShOrderEntity> findPageByUserId(Page page, @Param("params") Map map);

    Integer countByUserIdAndStatusSql( @Param("params")Map map);

    BigDecimal sumTodayMoney(@Param("params")Map map);
}
