package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.form.ShOrderSendDTO;
import com.plusesb.dto.form.ShOrderSubmitDTO;
import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.utils.R;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 订单信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
public interface ShOrderService extends BaseService<ShOrderEntity>,IService<ShOrderEntity> {

    PageDTO<ShOrderEntity> findPageBySql(SearchDTO simpleSearchDTO);

    /**
     * 获取支付请求参数
     * @param loginUser
     * @param orderId
     * @param appid
     * @param remoteHost
     * @return
     */
    R payPrepay(ShUserEntity loginUser, Long orderId, String appid, String remoteHost);

    /**
     *
     * @param orderNumber
     * @return
     */
    ShOrderEntity findByOrderNumber(String orderNumber);

    /**
     * 支付接口
     * @param orderInfo 订单详情
     * @param desc 描述
     * @param transaction_id 支付单号
     * @param out_refund_no 支付单号
     * @param totalFree 支付金额
     * @param remoteHost 回调IP
     * @param payType 用户支付方式
     */
    R payOrder(ShOrderEntity orderInfo, String desc, String transaction_id, String out_refund_no, BigDecimal totalFree, String remoteHost, String payType);


    /**
     *
     * @param submitDTO 订单提交DTO
     * @param loginUser 登陆用户
     * @param appid
     * @return
     */
    R submit(ShOrderSubmitDTO submitDTO, ShUserEntity loginUser, String appid);

    /**
     * 取消
     * @param order 订单
     * @param desc 描述
     * @param username 用户名称
     * @param userId 用户ID
     * @param remoteHost ip地址
     * @return
     */
    R cancelOrder(ShOrderEntity order, String desc, String username, Long userId, String remoteHost);

    /**
     * reids 消息过期回调
     * @param orderNumber 订单Id
     * @return
     */
    R cancelOrderByRedis(String orderNumber);
    /**
     * 完成订单
     * @param order 订单
     * @param username 用户名
     * @return
     */
    R completeOrder(ShOrderEntity order, String username);

    /**
     * 查询用户订单不包含在退款流程中的
     * @param page 当前页数
     * @param size 每页行数
     * @param userId 所属用户
     * @param status
     * @return
     */
    PageDTO<ShOrderEntity> findPageByUserId(Long page, Long size, Long userId, Integer status);

    /**
     * 发货
     * @param sendDTO 订单ID 以及 快递单号
     * @return
     */
    R sendOrder(ShOrderSendDTO sendDTO);

    ShOrderEntity changeOrderStatus(ShOrderEntity byId, String value, String value1, String value2, String comment, String loginUserName);

    /**
     * 统计用户不同状态订单数量
     * @param map 参数
     * @return
     */
    Integer countByUserIdAndStatusSql(Map map);

    /**
     * 今日订单总额
     * @param map
     * @return
     */
    BigDecimal sumTodayMoney(Map map);
}

