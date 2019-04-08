
package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.plusesb.mapper.SysConfigMapper;
import com.plusesb.entity.SysConfigEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.SysConfigService;
import com.plusesb.utils.SysConfigRedis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public void saveConfig(SysConfigEntity config) {
		baseMapper.insert(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateConfig(SysConfigEntity config) {
		this.updateById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.getById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		baseMapper.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.selectOne(new QueryWrapper<SysConfigEntity>().eq("param_key",key));
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
