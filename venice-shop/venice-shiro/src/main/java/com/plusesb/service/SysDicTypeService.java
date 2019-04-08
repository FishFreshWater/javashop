package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysDicTypeEntity;
import com.plusesb.utils.R;

import java.util.List;

/**
 * 字典类型表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:54
 */
public interface SysDicTypeService extends BaseService<SysDicTypeEntity>,IService<SysDicTypeEntity> {

    R removeByIdsNoItem(Long[] ids);

    List<SysDicTypeEntity> findByCode(String code);
}

