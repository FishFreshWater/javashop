package com.plusesb.mapper;

import com.plusesb.entity.ShCollectEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-29 02:59:11
 */
@Mapper
public interface ShCollectMapper extends SuperMapper<ShCollectEntity> {

    List<ShCollectEntity> findBySql(Map<String,Object> param);
}
