
package com.plusesb.service.impl;

import com.plusesb.mapper.SysLogMapper;
import com.plusesb.entity.SysLogEntity;
import com.plusesb.service.SysLogService;
import org.springframework.stereotype.Service;


@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

}
