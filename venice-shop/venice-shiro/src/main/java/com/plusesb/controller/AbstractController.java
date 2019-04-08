package com.plusesb.controller;

import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.utils.QueryUtils;
import com.plusesb.utils.RequestHelper;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getId();
	}
	/**
	 * 将request组装为SearchDTO并返回
	 * @return
	 */
	public SearchDTO getSearchDtoFromJqGrid(){
		return QueryUtils.buildSearchDtoFromJqGrid(RequestHelper.getCurrentRequest());
	}
}
