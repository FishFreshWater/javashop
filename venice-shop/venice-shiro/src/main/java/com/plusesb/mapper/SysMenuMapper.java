package com.plusesb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plusesb.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 菜单管理
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年9月18日 上午9:33:01
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
	
}
