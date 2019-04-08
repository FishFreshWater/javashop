package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysUserTokenEntity;
import com.plusesb.utils.R;

/**
 * 用户Token
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService extends BaseService<SysUserTokenEntity> , IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);
	/**
	 * 通过token查询数据
	 * @param accessToken
	 * @return
	 */
	SysUserTokenEntity queryByToken(String accessToken);
}
