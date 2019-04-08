package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShOrderGoodsEntity;
import com.plusesb.entity.ShOrderLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单日志
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShOrderLogService extends BaseService<ShOrderLogEntity>,IService<ShOrderLogEntity> {

    /**
     * 保存订单日志
     * @param id 订单ID
     * @param content 描述
     * @param userName 操作人员
     */
    void saveOrderLog(Long id, String content, String userName);

    /**
     * 通过订单ID查询订单日志
     * @param id
     * @return
     */
    List<ShOrderLogEntity> findByOrderId(Long id);
}

