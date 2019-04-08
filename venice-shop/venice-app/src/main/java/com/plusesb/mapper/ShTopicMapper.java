package com.plusesb.mapper;

import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShTopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@Mapper
public interface ShTopicMapper extends SuperMapper<ShTopicEntity> {

    List<ShGoodsEntity> findRelatedGoodsByTopicId(@Param("id") Long id);
}
