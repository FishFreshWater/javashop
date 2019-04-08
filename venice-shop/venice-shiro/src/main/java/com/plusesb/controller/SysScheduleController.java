package com.plusesb.controller;

import com.plusesb.annotation.SysLog;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysScheduleEntity;
import com.plusesb.service.SysScheduleService;
import com.plusesb.utils.R;
import com.plusesb.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 *
 * @author linyuchi@heyit.cn
 * @since 2018-12-10
 */
@RestController
@RequestMapping("/sys/schedule")
public class SysScheduleController extends AbstractController{
	@Autowired
	private SysScheduleService sysJobService;
	
	/**
	 * 定时任务列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public R list(){

		SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
		PageDTO<SysScheduleEntity> page = sysJobService.findPageBySimpleSearch(simpleSearchDTO);

		return R.ok().put("page", page);
	}
	
	/**
	 * 定时任务信息
	 */
	@GetMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public R info(@PathVariable("jobId") Long jobId){
		SysScheduleEntity schedule = sysJobService.getById(jobId);
		
		return R.ok().put("schedule", schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@PostMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public R save(@RequestBody SysScheduleEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		
		sysJobService.saveJob(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@PostMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public R update(@RequestBody SysScheduleEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
				
		sysJobService.updateJob(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@PostMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public R delete(@RequestBody Long[] jobIds){
		sysJobService.deleteBatch(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@PostMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public R run(@RequestBody Long[] jobIds){
		sysJobService.run(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@PostMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public R pause(@RequestBody Long[] jobIds){
		sysJobService.pause(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@PostMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public R resume(@RequestBody Long[] jobIds){
		sysJobService.resume(jobIds);
		
		return R.ok();
	}

}
