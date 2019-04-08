package com.plusesb.controller.admin;

import com.plusesb.config.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * 用于处理关于ueditor插件相关的请求
 * @author linyuchi
 * @date 2017-11-29
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController {

	@RequestMapping(value = "/config")
	@ResponseBody
	public String exec(HttpServletRequest request) throws UnsupportedEncodingException{ 
		request.setCharacterEncoding("utf-8");
		return new ActionEnter( request ).exec();
	}
	
}
