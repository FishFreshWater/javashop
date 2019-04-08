package com.plusesb.controller.admin;

import com.plusesb.controller.AbstractController;
import com.plusesb.exception.RRException;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.RequestHelper;

import javax.servlet.http.Cookie;

/**
 * Controller公共组件
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018/03/18
 */
public abstract class AdminShAbstractController extends AbstractController {
	protected String getAppid() {
		//从header中获取appid
		String appid = RequestHelper.getCurrentRequest().getHeader("appid");

		//如果header中不存在appid，则从参数中获取token
		if (org.apache.commons.lang.StringUtils.isBlank(appid)) {
			appid = RequestHelper.getCurrentRequest().getParameter("appid");
		}
		//如果参数中没有就从appid中取
		if (org.apache.commons.lang.StringUtils.isBlank(appid)) {
			Cookie[] cookies = RequestHelper.getCurrentRequest().getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("appid")) {
					appid = cookie.getValue();
					break;
				}
			}
		}
		if (BaseUtils.isEmpty(appid)){
			throw new RRException("不存在APPID");
		}
		return appid;
	}
}
