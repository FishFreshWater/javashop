package com.plusesb.mapper;

import com.plusesb.entity.ShAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户地址信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@Mapper
public interface ShAddressMapper extends SuperMapper<ShAddressEntity> {

    void updateNotDefault(@Param("id") Long id, @Param("userId") Long userId);
}
