package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusesb.entity.SysScheduleLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author linyuchi@heyit.cn
 * @since 2018-12-10
 */
@Mapper
public interface SysScheduleLogMapper extends BaseMapper<SysScheduleLogEntity> {
	
}
