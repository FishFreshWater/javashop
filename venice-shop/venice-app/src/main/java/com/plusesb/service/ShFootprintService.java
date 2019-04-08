package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.dto.PageDTO;
import com.plusesb.entity.ShFootprintEntity;

import java.util.Map;

/**
 * 用户足迹
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-17 10:37:34
 */
public interface ShFootprintService extends BaseService<ShFootprintEntity>,IService<ShFootprintEntity> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageDTO<ShFootprintEntity> findBySql(Map<String, Object> params);
}

