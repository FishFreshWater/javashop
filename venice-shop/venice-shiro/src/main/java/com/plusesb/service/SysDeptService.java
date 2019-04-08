package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 14:45:44
 */
public interface SysDeptService extends BaseService<SysDeptEntity>,IService<SysDeptEntity> {

    /**
     * 查询子部门
     * @param deptId
     * @return
     */
    List<SysDeptEntity> findByParentId(Long deptId);
}

