package com.plusesb.service.impl;

import com.plusesb.entity.SysLogEntity;
import com.plusesb.service.AsyncService;
import com.plusesb.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private SysLogService sysLogService;

    @Override
    @Async("asyncServiceExecutor")
    public void asyncSaveLog(SysLogEntity sysLogEntity) {
        log.info("start asyncSageLog");
        try{
            sysLogService.save(sysLogEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end asyncSageLog");
    }

    @Override
    public void asyncUpdateLog(SysLogEntity sysLog) {
        log.info("start asyncSageLog");
        try{
            sysLogService.updateById(sysLog);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end asyncSageLog");
    }

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }
}