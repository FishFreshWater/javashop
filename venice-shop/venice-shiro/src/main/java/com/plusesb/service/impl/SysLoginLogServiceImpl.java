package com.plusesb.service.impl;

import org.springframework.stereotype.Service;
import com.plusesb.entity.SysLoginLogEntity;
import com.plusesb.service.SysLoginLogService;
import com.plusesb.mapper.SysLoginLogMapper;


@Service("sysLoginLogService")
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogMapper, SysLoginLogEntity> implements SysLoginLogService {


    @Override
    public void saveLog(String remoteHost, String username, String msg, String status, String login) {

        SysLoginLogEntity sysLoginLogEntity = new SysLoginLogEntity();
        sysLoginLogEntity.setIp(remoteHost);
        sysLoginLogEntity.setType(login);
        sysLoginLogEntity.setUsername(username);
        sysLoginLogEntity.setStatus(status);
        sysLoginLogEntity.setException(msg);
        baseMapper.insert(sysLoginLogEntity);
    }
}
