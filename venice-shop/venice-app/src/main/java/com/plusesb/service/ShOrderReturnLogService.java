package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShOrderReturnLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单退款日志
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-08 19:48:28
 */
public interface ShOrderReturnLogService extends BaseService<ShOrderReturnLogEntity>,IService<ShOrderReturnLogEntity> {

    /**
     * 通过returnId 查询操作日志
     * @param id
     * @return
     */
    List<ShOrderReturnLogEntity> findByOrderReturnId(Long id);
}

