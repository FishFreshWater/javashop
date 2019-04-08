package com.plusesb.service;

import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.ShOrderReturnEntity;

/**
 * 消息发送接口
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
public interface ShMessageService  {

    /**
     * 订单支付提醒
     * @param order
     */
    void sendMsgPayed(ShOrderEntity order);

    /**
     * 订单发货提醒
     * @param order
     */
    void sendMsgSend(ShOrderEntity order);

    /**
     * 退款提醒
     * @param order
     * @param orderReturn
     */
    void sendMsgRefund(ShOrderEntity order, ShOrderReturnEntity orderReturn);

    /**
     * 退货提醒
     * @param order
     * @param orderReturn
     */
    void sendMsgReturn(ShOrderEntity order, ShOrderReturnEntity orderReturn);
}

