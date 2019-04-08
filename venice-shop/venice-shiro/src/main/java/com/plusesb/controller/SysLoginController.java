package com.plusesb.controller;

import com.plusesb.annotation.SysLog;
import com.plusesb.dto.SysLoginForm;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.SysCaptchaService;
import com.plusesb.service.SysLoginLogService;
import com.plusesb.service.SysUserService;
import com.plusesb.service.SysUserTokenService;
import com.plusesb.utils.R;
import com.plusesb.utils.RequestHelper;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private SysLoginLogService sysLoginLogService;

	/**
	 * 验证码
	 */
	@SysLog("验证码")
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}


	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form, HttpServletRequest request) {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			sysLoginLogService.saveLog(RequestHelper.getRemoteHost(request),form.getUsername(),"验证码不正确","error","login");
			return R.error("验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			sysLoginLogService.saveLog(RequestHelper.getRemoteHost(request),form.getUsername(),"账号或密码不正确","error","login");
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			sysLoginLogService.saveLog(RequestHelper.getRemoteHost(request),form.getUsername(),"账号已被锁定,请联系管理员","error","login");
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		sysLoginLogService.saveLog(RequestHelper.getRemoteHost(request),form.getUsername(),"登陆成功","success","login");
		R r = sysUserTokenService.createToken(user.getId());

		return r;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout(HttpServletRequest request) {
		sysLoginLogService.saveLog(RequestHelper.getRemoteHost(request),this.getUser().getUsername(),"退出登陆","success","logout");
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}
	
}
