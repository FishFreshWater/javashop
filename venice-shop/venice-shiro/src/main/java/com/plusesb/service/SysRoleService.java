package com.plusesb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends BaseService<SysRoleEntity> ,IService<SysRoleEntity> {

	void saveRole(SysRoleEntity role);

	void updateRole(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
