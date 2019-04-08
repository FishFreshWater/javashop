
package com.plusesb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusesb.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年12月4日 下午6:46:16
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
