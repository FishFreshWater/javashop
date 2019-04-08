package com.plusesb.entity;



import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年9月18日 上午9:28:39
 */
@TableName("sys_user_role")
public class SysUserRoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;


	/**
	 * 设置：用户ID
	 * @param userId 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：角色ID
	 * @param roleId 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：角色ID
	 * @return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
}
