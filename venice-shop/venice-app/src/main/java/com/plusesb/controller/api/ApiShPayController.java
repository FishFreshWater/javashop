package com.plusesb.controller.api;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.plusesb.annotation.LoginUser;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.dto.ShOrderDTO;
import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.ShOrderPayEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.entity.enums.OrderPayInfoStatus;
import com.plusesb.entity.enums.OrderPayStatus;
import com.plusesb.entity.enums.OrderPayType;
import com.plusesb.service.ShOrderPayService;
import com.plusesb.service.ShOrderService;
import com.plusesb.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


/**
 * 支付接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Slf4j
@Api(tags = "支付接口")
@RestController
@RequestMapping("api/sh_pay")
public class ApiShPayController extends ApiShAbstractController{

    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ShOrderPayService shOrderPayService;
    @Autowired
    private WxPayService wxPayService;

    /**
     *
     */
    @ApiOperation(value = "跳转")
    @PostMapping("index")
    public R index() {
        return R.ok();
    }
    /**
     * 获取支付的请求参数
     */
    @ApiOperation(value = "获取支付的请求参数")
    @PostMapping("prepay")
    public R payPrepay(@LoginUser ShUserEntity loginUser, @RequestBody ShOrderDTO orderDTO) {
        //
        ShOrderEntity orderInfo = shOrderService.findByOrderNumber(orderDTO.getOrderNumber());

        if (null == orderInfo) {
            return R.error(400, "订单已取消");
        }

        if (!orderInfo.getPayStatus().equals(OrderPayStatus.PENDING.getValue()) ){
            return R.error(400, "订单已支付，请不要重复操作");
        }
        return shOrderService.payPrepay(loginUser,orderInfo.getId(),this.getAppid(), RequestHelper.getRemoteHost(RequestHelper.getCurrentRequest()));
    }


    /**
     * 微信查询订单状态
     */
    @ApiOperation(value = "查询订单状态")
    @PostMapping("query")
    public R orderQuery(@LoginUser ShUserEntity loginUser, @RequestBody ShOrderDTO orderDTO) {

        String orderNumber = orderDTO.getOrderNumber();
        if (orderNumber == null) {
            return R.error("订单不存在");
        }
        ShOrderEntity orderInfo = shOrderService.findByOrderNumber(orderNumber);
        if (!orderInfo.getPayStatus().equals(OrderPayStatus.PENDING.getValue())) {
            return  R.ok("订单已经支付");
        }
        ShOrderPayEntity shOrderPayEntity = shOrderPayService.findByOrderIdAndStatusAndPayType(orderInfo.getId(), OrderPayInfoStatus.UNPAY.getValue(),OrderPayType.WX.getValue());
        WxPayOrderQueryRequest wxPayOrderQueryRequest = WxPayOrderQueryRequest.newBuilder().outTradeNo(shOrderPayEntity.getPayNumber()).build();
        try {
            WxPayOrderQueryResult  queryResult = wxPayService.queryOrder(wxPayOrderQueryRequest);
            if (!"SUCCESS".equals(queryResult.getReturnCode())) {
                return R.error("查询失败,error=" + queryResult.getReturnMsg());
            }
            if ("SUCCESS".equals(queryResult.getTradeState())){
                log.error("订单" + shOrderPayEntity.getPayNumber() + "支付成功");

                BigDecimal totalFree = new BigDecimal(queryResult.getTotalFee()).
                        divide(new BigDecimal(100));
                return  shOrderService.payOrder(orderInfo, "用户使用微信支付", queryResult.getTransactionId(),
                        shOrderPayEntity.getPayNumber(),
                        totalFree, RequestHelper.getRemoteHost(request),OrderPayType.WX.getValue());
            }else if ("USERPAYING".equals(queryResult.getTradeState())) {
                // 重新查询 正在支付中
                String queryRepeatNum = redisUtils.get(ShSysConstant.SHOP_CACHE_NAME+"_queryRepeatNum" + orderNumber + "");
                if (BaseUtils.isEmpty(queryRepeatNum)) {
                    redisUtils.set(ShSysConstant.SHOP_CACHE_NAME+"_queryRepeatNum" + orderNumber + "", "1",60);
                    this.orderQuery(loginUser, orderDTO);
                } else {
                    Integer num = Integer.parseInt(queryRepeatNum);
                    if (num <= 3 ){
                        redisUtils.set(ShSysConstant.SHOP_CACHE_NAME+"_queryRepeatNum" + orderNumber + "", num+1+"",60);
                        this.orderQuery(loginUser, orderDTO);
                    }else {
                        return R.error("查询失败,error=" + queryResult.getTradeState());
                    }
                }
            } else {
                // 失败
                return R.error("查询失败,error=" + queryResult.getTradeState());
            }
        } catch (WxPayException e) {
            e.printStackTrace();
            return R.error("查询失败，未知错误");
        }


        return R.error("查询失败，未知错误");
    }

    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/notify")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);

        if (("FAIL").equals(notifyResult.getReturnCode())) {
            //订单编号
            String out_trade_no = notifyResult.getOutTradeNo();
            log.error("订单" + out_trade_no + "支付失败");
        } else if (("SUCCESS").equals(notifyResult.getReturnCode())) {
            //订单编号
            String out_trade_no = notifyResult.getOutTradeNo();
            log.error("订单" + out_trade_no + "支付成功");
            String orderNumber = ShOrderUtils.subOrderNumber(out_trade_no);
            // 业务处理
            ShOrderEntity orderInfo = shOrderService.findByOrderNumber(orderNumber);
            BigDecimal totalFree = new BigDecimal(notifyResult.getTotalFee()).
                    divide(new BigDecimal(100));
            shOrderService.payOrder(orderInfo, "用户使用微信支付", notifyResult.getTransactionId(),
                    out_trade_no,
                    totalFree, RequestHelper.getRemoteHost(request),OrderPayType.WX.getValue());
        }

        return WxPayNotifyResponse.success("成功");
    }



}
