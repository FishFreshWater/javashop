package com.plusesb.service;

import com.plusesb.entity.SysLogEntity;

public interface AsyncService {

    /**
     * 执行异步任务
     */
    void executeAsync();
    /**
     * 异步保存日志
     */
    void asyncSaveLog(SysLogEntity sysLogEntity);

    /**
     * 异步记录日志
     * @param sysLog
     */
    void asyncUpdateLog(SysLogEntity sysLog);
}