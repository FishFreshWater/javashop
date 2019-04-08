package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShOrderPayEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单支付信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
public interface ShOrderPayService extends BaseService<ShOrderPayEntity>,IService<ShOrderPayEntity> {

    /**
     * 保存支付信息
     * @param prepay_id
     * @param paymoney 支付金额
     * @param status 状态
     * @param payNumber 预支付码
     * @param id
     */
    void saveOrderPayInfo(String prepay_id, BigDecimal paymoney, Integer status, String payNumber, Long id);

    /**
     * 通过支付编码查询支付信息
     * @param payNumber
     * @return
     */
    ShOrderPayEntity findByPayNumber(String payNumber);

    /**
     * 查询订单支付信息列表
     * @param id 订单ID
     * @param value 订单状态
     * @return
     */
    List<ShOrderPayEntity> findByOrderIdAndStatus(Long id, Integer value);

    /**
     *
     * @param orderId 订单ID
     * @param status  订单状态
     * @param payType 支付类型
     * @return
     */
    ShOrderPayEntity findByOrderIdAndStatusAndPayType(Long orderId, Integer status, String payType);

    /**
     * 通过订单ID查询支付信息
     * @param id
     * @return
     */
    List<ShOrderPayEntity> findByOrderId(Long id);
}

