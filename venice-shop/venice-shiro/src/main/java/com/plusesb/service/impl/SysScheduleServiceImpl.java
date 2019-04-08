
package com.plusesb.service.impl;

import com.plusesb.constant.status.ScheduleStatus;
import com.plusesb.entity.SysScheduleEntity;
import com.plusesb.mapper.SysScheduleMapper;
import com.plusesb.service.SysScheduleService;
import com.plusesb.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service("sysScheduleService")
public class SysScheduleServiceImpl extends BaseServiceImpl<SysScheduleMapper, SysScheduleEntity> implements SysScheduleService {
	@Autowired
    private Scheduler scheduler;


	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<SysScheduleEntity> scheduleJobList = this.findAll();
		for(SysScheduleEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJob(SysScheduleEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getKey());
        baseMapper.insert(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJob(SysScheduleEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                
        this.updateById(scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	baseMapper.deleteBatchIds(Arrays.asList(jobIds));
	}

	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", Arrays.asList(jobIds));
    	map.put("status", status);
    	return baseMapper.updateBatch(map);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, baseMapper.selectById(jobId));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, ScheduleStatus.PAUSE.getKey());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatus.NORMAL.getKey());
    }
    
}
