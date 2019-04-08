package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.mapper.SysUserTokenMapper;
import com.plusesb.entity.SysUserTokenEntity;
import com.plusesb.service.SysUserTokenService;
import com.plusesb.utils.R;
import com.plusesb.utils.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserTokenMapper, SysUserTokenEntity> implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;


	@Override
	public SysUserTokenEntity queryByToken(String accessToken) {
		return baseMapper.selectOne(new QueryWrapper<SysUserTokenEntity>().eq("token",accessToken));
	}

	@Override
	public R createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		//控制token每个用户只能够在一处登陆。
		SysUserTokenEntity tokenEntity = baseMapper.selectOne(new QueryWrapper<SysUserTokenEntity>().eq("user_id",userId));
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//保存token
			baseMapper.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			baseMapper.updateById(tokenEntity);
		}
		return R.ok().put("token", token).put("expire", EXPIRE);
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//修改token
		SysUserTokenEntity tokenEntity = baseMapper.selectOne(new QueryWrapper<SysUserTokenEntity>().eq("user_id",userId));

		tokenEntity.setToken(token);
		baseMapper.updateById(tokenEntity);
	}
}