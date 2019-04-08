package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysLoginLogEntity;

/**
 * 登陆日志
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-25 00:13:15
 */
public interface SysLoginLogService extends BaseService<SysLoginLogEntity>,IService<SysLoginLogEntity> {

    /**
     * 保存系统日志
     * @param remoteHost ip
     * @param username 用户名
     * @param msg 异常信息
     * @param status 状态
     * @param login
     */
    void saveLog(String remoteHost, String username, String msg, String status, String login);
}

