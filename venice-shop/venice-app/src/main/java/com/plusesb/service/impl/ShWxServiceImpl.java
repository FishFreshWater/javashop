package com.plusesb.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.plusesb.config.WxMaConfiguration;
import com.plusesb.config.WxMaProperties;
import com.plusesb.service.ShWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: linyuchi
 * Date: 2019/1/6
 * Time: 19:22
 * Description: No Description
 */
@Service("shWxService")
public class ShWxServiceImpl implements ShWxService {

    @Autowired
    WxMaProperties wxMaProperties;


    @Override
    public WxMaService getWxMaService() {

        /**
         * 单用户appid;
         */
        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        return WxMaConfiguration.getMaService(appid);
    }
}
