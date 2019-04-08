package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.entity.ShGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品信息
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@Mapper
public interface ShGoodsMapper extends SuperMapper<ShGoodsEntity> {

    List<ShGoodsEntity> findPageBySql(Page<ShGoodsEntity> page, @Param("map")Map<String, Object> map);


    ShGoodsEntity listInfoById(@Param("id") Long id);

    List<ShGoodsEntity> findAllByQueryWrapper(@Param("ew")QueryWrapper queryWrapper);

    Integer queryTotal(@Param("ew")QueryWrapper queryWrapper);

    void updateIsDeleteByIds(@Param("ids") List<Long> ids);

    void updateIsOnSaleById(@Param("id")Long id,@Param("isOnSale")Integer isOnSale);
}
