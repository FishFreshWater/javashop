package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.ShOrderReturnEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.utils.R;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单退款
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-08 19:48:28
 */
public interface ShOrderReturnService extends BaseService<ShOrderReturnEntity>,IService<ShOrderReturnEntity> {

    /**
     * 订单退款提交
     * @param loginUser 用户信息
     * @param comment 原因
     * @param order 订单信息
     * @return
     */
    ShOrderReturnEntity submitOrderRefund(ShUserEntity loginUser, String comment, ShOrderEntity order);

    /**
     * 订单退货提交
     * @param loginUser 用户信息
     * @param comment 原因
     * @param order 订单信息
     * @return
     */
    ShOrderReturnEntity submitOrderReturn(ShUserEntity loginUser, String comment, ShOrderEntity order);

    /**
     * 通过退货单号查询
     * @param returnNumber
     * @return
     */
    ShOrderReturnEntity findByReturnNumber(String returnNumber);

    /**
     * 查询当前程序中正在处理的退款订单
     * @param appid appid
     * @param status 退款状态
     * @return
     */
    List<ShOrderReturnEntity> findByAppidAndStatus(String appid, Integer status);

    /**
     * 保存用户退换货信息，并保存日志
     *
     * @param returnType
     * @param loginUserName 操作用户木你刚才
     * @param amount 金额
     * @param reason 用户日志操作
     * @param order 订单
     * @param loginUserId 登陆用户
     * @param comment 原因       @return
     */
    ShOrderReturnEntity createOrderReturnAndSaveOrderReturnLog(Integer returnType, String loginUserName,
                                                               BigDecimal amount, String reason, ShOrderEntity order, Long loginUserId, String comment);
    /**
     * 退款订单审核通过
     * @param comment 备注
     * @param id	编码
     * @param loginUserName 管理员名称
     * @param ip IP地址
     */

    void checkRefundCompelted(String comment, Long id, String loginUserName, String ip);

    /**
     * 审核不通过
     * @param comment 备注
     * @param id 编码
     * @param loginUserName 管理员名称
     */
    void checkCancel(String comment, Long id, String loginUserName);

    /**
     * 退货订单审核通过
     * @param comment 备注
     * @param id 编码
     * @param loginUserName 管理员名称
     */
    void checkReturnInit(String comment, Long id, String loginUserName);
    /**
     * 管理员设置成功审核确认退货
     * @param comment 日志备注
     * @param id  退货单号
     * @param loginUserName 操作员名称
     * @param amount 金额
     * @param ip IP地址
     */
    void checkReturnCompelted(String comment, Long id, String loginUserName, String amount, String ip);

    /**
     * 提交退货单号
     * @param orderReturnEntity 用户
     * @param username 用户名称
     * @return
     */
    R submitTracking(ShOrderReturnEntity orderReturnEntity, String username);
}

