package com.plusesb.mapper;

import com.plusesb.entity.ShCartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@Mapper
public interface ShCartMapper extends SuperMapper<ShCartEntity> {

    void updateCheck(@Param("productIds") String[] productIds,
                     @Param("isChecked") Integer isChecked, @Param("userId") Long userId);

    void deleteByUserAndProductIds(@Param("userId")Long userId, @Param("productIds") String[] productIds);


    List<ShCartEntity> findBySql(Map<String,Object> map);

    List<ShCartEntity> findByUserId(Map mapParam);
}
