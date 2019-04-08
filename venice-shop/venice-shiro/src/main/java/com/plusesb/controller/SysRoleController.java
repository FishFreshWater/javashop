package com.plusesb.controller;
import com.plusesb.annotation.SysLog;
import com.plusesb.constant.SysConstant;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysRoleEntity;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.service.SysRoleMenuService;
import com.plusesb.service.SysRoleService;
import com.plusesb.utils.R;
import com.plusesb.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(){
		//如果不是超级管理员，则只查询自己创建的角色列表
		SearchDTO searchDTO = getSearchDtoFromJqGrid();
		if(getUserId() != SysConstant.SUPER_ADMIN){
			searchDTO.addFiled("create_user_id", "eq",getUserId());
		}

		PageDTO<SysRoleEntity> page = sysRoleService.findPageBySimpleSearch(searchDTO);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		SearchDTO searchDTO = getSearchDtoFromJqGrid();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != SysConstant.SUPER_ADMIN){
			searchDTO.addFiled("create_user_id", "eq",getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.findAllBySimpleSearch(searchDTO);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.saveRole(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.updateRole(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){

		sysRoleService.removeByIds(Arrays.asList(roleIds));
		return R.ok();
	}
}
