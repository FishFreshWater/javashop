package com.plusesb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.entity.ShFootprintEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户足迹
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-17 10:37:34
 */
@Mapper
public interface ShFootprintMapper extends SuperMapper<ShFootprintEntity> {

    List<ShFootprintEntity> findPageBySql(Page<ShFootprintEntity> page, @Param("map")Map<String, Object> params);
}
