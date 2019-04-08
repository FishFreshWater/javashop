package com.plusesb.controller.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.dto.FullUserInfoDTO;
import com.plusesb.dto.UserInfo;
import com.plusesb.dto.form.LoginForm;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.ShUserService;
import com.plusesb.service.ShWxService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.JwtUtils;
import com.plusesb.utils.R;
import com.plusesb.validator.Assert;
import com.plusesb.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 会员信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "API登录授权接口")
@RestController
@RequestMapping("api/sh_auth")
public class ApiShAuthController extends ApiShAbstractController{
    @Autowired
    private ShUserService shUserService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ShWxService shWxService;
    /**
     * 登录
     */
    @IgnoreAuth
    @PostMapping("login")
    @ApiOperation(value = "登录接口")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        ShUserEntity user = shUserService.findByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");

        //密码错误
        if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
            throw new RRException("手机号或密码错误");
        }

        //生成token
        String token = jwtUtils.generateToken(user.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);

    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @IgnoreAuth
    @PostMapping("login_by_weixin")
    public R loginByWeixin() {
        JSONObject jsonParam = this.getJsonRequest();
        FullUserInfoDTO fullUserInfo = null;
        String code = "";
        if (!BaseUtils.isEmpty(jsonParam.getString("code"))) {
            code = jsonParam.getString("code");
        }
        if (!BaseUtils.isEmpty(jsonParam.get("userInfo"))) {
            fullUserInfo = jsonParam.getObject("userInfo", FullUserInfoDTO.class);
        }
        logger.info("登陆用户信息：fullUserInfo+"+fullUserInfo+"   code:"+code);

        Map<String, Object> resultObj = new HashMap<String, Object>();

        UserInfo userInfo = fullUserInfo.getUserInfo();
        final WxMaService wxService = shWxService.getWxMaService();

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            ShUserEntity shUser = shUserService.findByOpenId(session.getOpenid());
            if (BaseUtils.isEmpty( shUser)) {
                shUser = new ShUserEntity();
                shUser.setUsername("微信用户" + BaseUtils.getRandomString(12));
                shUser.setPassword(session.getOpenid());
                shUser.setLastLoginTime(new Date());
                shUser.setLastLoginIp(this.getClientIp());
                shUser.setOpenid(session.getOpenid());
                shUser.setAvatarUrl(userInfo.getAvatarUrl());
                shUser.setGender(userInfo.getGender());
                shUser.setCity(userInfo.getCity());
                shUser.setProvince(userInfo.getProvince());
                shUser.setNickname(userInfo.getNickName());
                shUserService.save(shUser);
            } else {
                shUser.setLastLoginTime(new Date());
                shUser.setLastLoginIp(this.getClientIp());
                shUserService.updateById(shUser);
            }

            //生成token
            String token = jwtUtils.generateToken(shUser.getId());

            if (BaseUtils.isEmpty(userInfo) || BaseUtils.isEmpty(token)) {
                return R.error("登录失败");
            }
            resultObj.put("token", token);
            resultObj.put("userInfo", userInfo);
            resultObj.put("userId", shUser.getId());
            resultObj.put("mobile", shUser.getMobile());
            return R.ok(resultObj);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return R.error("登陆失败！");
        }

    }


}
