package com.plusesb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.utils.R;

import java.util.List;


/**
 * 系统用户
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends BaseService<SysUserEntity> ,IService<SysUserEntity> {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 保存用户
	 */
	R saveUser(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	R updateUser(SysUserEntity user);
	

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
}
