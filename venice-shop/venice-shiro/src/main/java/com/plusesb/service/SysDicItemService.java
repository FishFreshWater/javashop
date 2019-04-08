package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysDicItemEntity;
import com.plusesb.entity.SysDicTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:55
 */
public interface SysDicItemService extends BaseService<SysDicItemEntity>,IService<SysDicItemEntity> {

    /**
     * 通过typeId 查询对应字典
     * @param id
     * @return
     */
    List<SysDicItemEntity> findByTypeId(Long id);
}

