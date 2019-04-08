package com.plusesb.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.plusesb.entity.*;
import com.plusesb.entity.enums.*;
import com.plusesb.exception.RRException;
import com.plusesb.mapper.ShOrderReturnMapper;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import com.plusesb.utils.ShOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service("shOrderReturnService")
public class ShOrderReturnServiceImpl extends BaseServiceImpl<ShOrderReturnMapper, ShOrderReturnEntity> implements ShOrderReturnService {


    @Autowired
    private ShOrderReturnLogService shOrderReturnLogService;
    @Autowired
    private ShOrderLogService shOrderLogService;
    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private ShOrderPayService shOrderPayService;
    @Autowired
    private ShMessageService shMessageService;
    @Autowired
    private WxPayService wxPayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShOrderReturnEntity submitOrderRefund(ShUserEntity loginUser, String comment, ShOrderEntity order) {

        return createOrderReturnAndSaveOrderReturnLog(ShOrderReturnType.MONEY.getValue(),
                loginUser.getUsername(),order.getActualPrice(),
                comment,order,loginUser.getId(),"用户提交退款申请");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShOrderReturnEntity submitOrderReturn(ShUserEntity loginUser, String comment, ShOrderEntity order) {

        return createOrderReturnAndSaveOrderReturnLog(ShOrderReturnType.MONEYPRODUCT.getValue(),
                loginUser.getUsername(),order.getActualPrice(),
                "用户提交退货申请",order,loginUser.getId(),"用户提交退货申请");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShOrderReturnEntity createOrderReturnAndSaveOrderReturnLog(Integer returnType, String loginUserName,
                                                                      BigDecimal amount, String reason, ShOrderEntity order, Long loginUserId, String comment) {

        ShOrderReturnEntity orderReturn = new ShOrderReturnEntity();
        orderReturn.setAmount(amount);
        orderReturn.setUserId(loginUserId);
        orderReturn.setReturnType(returnType);
        orderReturn.setOperator(comment);
        orderReturn.setReason(reason);
        orderReturn.setAppid(order.getAppid());
        orderReturn.setOrderId(order.getId());
        orderReturn.setNumber(ShOrderUtils.getOrderReturnNumber(loginUserId));
        baseMapper.insert(orderReturn);
        orderReturn = changeStatusAndSaveOrderReturnLog("",comment,orderReturn.getId(),
                loginUserName,0,0,amount.toString());
        return orderReturn;
    }
    /**
     * 变更退款订单状态并保存数据
     * @param checkComment 审核备注
     * @param comment 操作备注
     * @param id 退款退货编号
     * @param loginUserName 管理员名称
     * @param checkStatus 审核状态
     * @param status 订单状态
     * @param amount 退款金额
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ShOrderReturnEntity changeStatusAndSaveOrderReturnLog(String checkComment, String comment, Long id,
                                                                 String loginUserName, Integer checkStatus, Integer status, String amount) {

        ShOrderReturnEntity orderReturn = baseMapper.selectById(id);
        if (BaseUtils.isNotEmpty(amount)){
            BigDecimal count = new BigDecimal(amount);
            orderReturn.setAmount(count);
        }
        if (BaseUtils.isNotEmpty(checkComment)){
            orderReturn.setCheckComment(checkComment);
        }
        orderReturn.setCheckTime(new Date());
        orderReturn.setCheckStatus(checkStatus);
        orderReturn.setStatus(status);
        orderReturn.setOperator(loginUserName);
        saveOrderReturnLog(id,comment,loginUserName);
        baseMapper.updateById(orderReturn);
        shOrderLogService.saveOrderLog(orderReturn.getOrderId(),comment,loginUserName);
        return orderReturn;
    }
    /**
     * 保存日志
     * @param orderReturnId
     * @param comment
     * @param loginUserName
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderReturnLog(Long orderReturnId, String comment,
                                   String loginUserName ){

        ShOrderReturnLogEntity orderReturnLog = new ShOrderReturnLogEntity();
        orderReturnLog.setOrderReturnId(orderReturnId);
        if(BaseUtils.isNotEmpty(comment)){
            orderReturnLog.setContent(comment);
        }
        orderReturnLog.setOperator(loginUserName);
        orderReturnLog.setCreateTime(new Date());
        shOrderReturnLogService.save(orderReturnLog);
    }

    /**
     * 发起退款请求
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendRefund(ShOrderEntity order, ShOrderReturnEntity orderReturn, String comment,String loginUserName,String ip ) {

        List<ShOrderPayEntity> orderPayInfos = shOrderPayService.findByOrderIdAndStatus(order.getId()
                ,OrderPayInfoStatus.PAYED.getValue());
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal amount = orderReturn.getAmount();
        for (ShOrderPayEntity orderPayInfo : orderPayInfos){
            total = total.add(orderPayInfo.getPayBalance());
        }
        if (amount.compareTo(total)>0){
            throw new ServiceException("退款金额异常");
        }
        if (amount.compareTo(BigDecimal.ZERO)<=0){
            return;
        }
        //在退款其他方式
        for (ShOrderPayEntity orderPayInfo : orderPayInfos){
            if (amount.compareTo(BigDecimal.ZERO)<=0){
                break;
            }
            if (orderPayInfo.getPayType().equals(OrderPayType.WX.getValue())){
                wxRefund(orderPayInfo,orderReturn,comment,amount);
                amount = amount.subtract(orderPayInfo.getPayBalance());
            }
        }
    }

    /**
     * 微信退款
     * @param orderPayInfo
     * @param orderReturn
     * @param comment
     * @param amount
     */
    private void wxRefund(ShOrderPayEntity orderPayInfo, ShOrderReturnEntity orderReturn, String comment, BigDecimal amount) {

        WxPayRefundRequest request = WxPayRefundRequest.newBuilder().outRefundNo(orderReturn.getNumber()).transactionId(orderPayInfo.getThirdPayId())
                .totalFee(amount.multiply(new BigDecimal(100)).intValue()).refundFee(orderPayInfo.getPayBalance().multiply(new BigDecimal(100)).intValue())
                .refundDesc(comment).build();
        try {
            WxPayRefundResult  refundResult = wxPayService.refund(request);
            if ("SUCCESS".equals(refundResult.getReturnCode())){
                if ("FAIL".equals(refundResult.getResultCode())){
                    throw  new RRException("退款异常");
                }
                orderPayInfo.setGatewayResCode(refundResult.getResultCode());
                orderPayInfo.setGatewayResBody(refundResult.toString());
                orderPayInfo.setStatus(OrderPayInfoStatus.RETURN.getValue());
                shOrderPayService.updateById(orderPayInfo);
            }
        } catch (WxPayException e) {
            e.printStackTrace();
            throw  new RRException("退款异常");
        }
    }

    /**
     * 退款审核通过
     * @param comment 备注
     * @param id	编码
     * @param loginUserName 管理员名称
     * @param ip IP地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkRefundCompelted(String comment, Long id, String loginUserName,String ip) {
        ShOrderReturnEntity orderReturn = changeStatusAndSaveOrderReturnLog(comment,comment,id,loginUserName, OrderReturnCheckStatus.CHECKED.getValue(),
                OrderReturnStatus.COMPLETED.getValue(),"");
        ShOrderEntity order = shOrderService.changeOrderStatus(shOrderService.getById(orderReturn.getOrderId()), OrderStatus.SUBMIT.getValue(),
                OrderPayStatus.REFUND.getValue(),OrderShippingStatus.UN_SEND.getValue(),comment,loginUserName);
        sendRefund(order, orderReturn, comment,loginUserName,ip);
        shMessageService.sendMsgRefund(order,orderReturn);
    }
    /**
     * 卖家确认收到退货设置为成功
     * @param comment 日志备注
     * @param id  退货单号
     * @param loginUserName 操作员名称
     * @param amount 金额
     * @param ip
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkReturnCompelted(String comment, Long id, String loginUserName, String amount, String ip) {
        ShOrderReturnEntity orderReturn =  changeStatusAndSaveOrderReturnLog("",comment,id,loginUserName,OrderReturnCheckStatus.CHECKED.getValue(),
                OrderReturnStatus.COMPLETED.getValue(),amount);
        ShOrderEntity order = shOrderService.changeOrderStatus(shOrderService.getById(orderReturn.getOrderId()), OrderStatus.SUBMIT.getValue(),
                OrderPayStatus.REFUND.getValue(),OrderShippingStatus.RETURN.getValue(),comment,loginUserName);
        sendRefund(order,orderReturn,comment,loginUserName,ip);
        shMessageService.sendMsgReturn(order,orderReturn);

    }

    /**
     * 审核不通过
     * @param comment 备注
     * @param id 编码
     * @param loginUserName 管理员名称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkCancel(String comment, Long id, String loginUserName) {
        changeStatusAndSaveOrderReturnLog(comment,comment,id,loginUserName,OrderReturnCheckStatus.FAILURE.getValue(),
                OrderReturnStatus.CANCEL.getValue(),"");
    }

    /**
     * 审核通过
     * @param comment 备注
     * @param id 编码
     * @param loginUserName 管理员名称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkReturnInit(String comment, Long id, String loginUserName) {
        changeStatusAndSaveOrderReturnLog(comment,comment,id,loginUserName,OrderReturnCheckStatus.CHECKED.getValue(),
                OrderReturnStatus.INIT.getValue(),"");
    }

    @Override
    public List<ShOrderReturnEntity> findByAppidAndStatus(String appid, Integer status) {
        return baseMapper.selectList(new QueryWrapper<ShOrderReturnEntity>().eq("appid",appid).eq("status",status));
    }

    @Override
    public ShOrderReturnEntity findByReturnNumber(String returnNumber) {
        return baseMapper.selectOne(new QueryWrapper<ShOrderReturnEntity>().eq("number",returnNumber));
    }

    @Override
    public R submitTracking(ShOrderReturnEntity orderReturnEntity, String username) {
        baseMapper.updateById(orderReturnEntity);
        this.saveOrderReturnLog(orderReturnEntity.getId(),"提交退货物流单号",username);
        return R.ok("提交成功");
    }
}
