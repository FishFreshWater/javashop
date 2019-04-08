
package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author linyuchi@heyit.cn
 * @since 2.0.0 2018-02-10
 */
public interface SysCaptchaService extends BaseService<SysCaptchaEntity> ,IService<SysCaptchaEntity> {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
