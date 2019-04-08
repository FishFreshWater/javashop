package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShUserEntity;

import java.util.Map;

/**
 * 会员信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
public interface ShUserService extends BaseService<ShUserEntity>,IService<ShUserEntity> {

    /**
     * 通过手机号码查询用户数据
     * @param mobile
     * @return
     */
    ShUserEntity findByMobile(String mobile);

    /**
     * 通过opentId查询数据
     * @param openid
     * @return
     */
    ShUserEntity findByOpenId(String openid);
}

