package com.plusesb.service;


import com.plusesb.entity.SysUserEntity;
import com.plusesb.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);


}
