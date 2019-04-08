
package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusesb.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author linyuchi@heyit.cn
 * @since 3.1.0 2018-02-10
 */
@Mapper
public interface SysCaptchaMapper extends BaseMapper<SysCaptchaEntity> {

}
