package com.plusesb.entity;



import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年9月18日 上午9:28:13
 */
@TableName("sys_role_menu")
public class SysRoleMenuEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;

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
	
	/**
	 * 设置：菜单ID
	 * @param menuId 菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取：菜单ID
	 * @return Long
	 */
	public Long getMenuId() {
		return menuId;
	}
	
}
