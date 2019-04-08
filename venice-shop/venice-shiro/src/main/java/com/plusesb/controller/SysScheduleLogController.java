
package com.plusesb.controller;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysScheduleLogEntity;
import com.plusesb.service.SysScheduleLogService;
import com.plusesb.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务日志
 *
 * @author linyuchi@heyit.cn
 * @since 2018-12-10
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class SysScheduleLogController extends AbstractController{
	@Autowired
	private SysScheduleLogService sysJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(){

		SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
		PageDTO<SysScheduleLogEntity> page = sysJobLogService.findPageBySimpleSearch(simpleSearchDTO);

		return R.ok().put("page", page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		SysScheduleLogEntity log = sysJobLogService.getById(logId);
		
		return R.ok().put("log", log);
	}
}
