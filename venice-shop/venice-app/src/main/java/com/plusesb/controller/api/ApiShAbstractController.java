package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * api Controller公共组件
 * 
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018/03/18
 */
public abstract class ApiShAbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 得到request对象
	 */
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private JwtUtils jwtUtils;

	protected String getAppid() {
		//从header中获取appid
		return request.getHeader("appid");
	}
	public JSONObject getJsonRequest() {
		JSONObject result = null;
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = request.getReader();) {
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
			result = JSONObject.parseObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * 获取请求的用户Id
	 *
	 * @return 客户端Ip
	 */
	public Long getUserId() {
		String token = request.getHeader(jwtUtils.getHeader());

		Claims claims = jwtUtils.getClaimByToken(token);
		if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
			return null;
		}
		return Long.parseLong(claims.getSubject());
	}
	/**
	 * 获取请求方IP
	 *
	 * @return 客户端Ip
	 */
	public String getClientIp() {
		String xff = request.getHeader("x-forwarded-for");
		if (xff == null) {
			return "8.8.8.8";
		}
		return xff;
	}
}
