
package com.plusesb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusesb.entity.SysScheduleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author linyuchi@heyit.cn
 * @since 2018-12-10
 */
@Mapper
public interface SysScheduleMapper extends BaseMapper<SysScheduleEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
