
package com.plusesb.controller;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysLogEntity;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.service.SysLogService;
import com.plusesb.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 系统日志
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController extends AbstractController{
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(){
		SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
		PageDTO<SysLogEntity> page = sysLogService.findPageBySimpleSearch(simpleSearchDTO);
		return R.ok().put("page", page);
	}
	
}
