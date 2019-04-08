package com.plusesb.controller.api;

import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.ShOrderDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.form.ShOrderSubmitDTO;
import com.plusesb.entity.*;
import com.plusesb.entity.enums.OrderPayStatus;
import com.plusesb.entity.enums.OrderReturnStatus;
import com.plusesb.entity.enums.OrderShippingStatus;
import com.plusesb.entity.enums.OrderStatus;
import com.plusesb.service.ShOrderGoodsService;
import com.plusesb.service.ShOrderReturnService;
import com.plusesb.service.ShOrderService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import com.plusesb.utils.RequestHelper;
import com.plusesb.utils.ShOrderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 订单接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("api/sh_order")
public class ApiShOrderController extends ApiShAbstractController{

    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private ShOrderGoodsService shOrderGoodsService;
    @Autowired
    private ShOrderReturnService shOrderReturnService;

    /**
     *订单首页
     */
    @ApiOperation(value = "订单首页")
    @PostMapping("index")
    public R index(@LoginUser ShUserEntity loginUser) {

        Map<String, Object> resultObj = new HashMap();

        Map map = new HashMap();
        //查询待付款数量
        map.put("user_id",loginUser.getId());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PENDING.getValue());
        map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
        Integer pending = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("pending",pending);

        //查询待发货数量
        map = new HashMap();
        map.put("user_id",loginUser.getId());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
        Integer payed = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("payed",payed);

        //查询待收货数量
        map = new HashMap();
        map.put("user_id",loginUser.getId());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.SEND.getValue());
        Integer send = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("send",send);

        //查询售后服务数量
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("user_id","eq",loginUser.getId());
        simpleSearchDTO.addFiled("status","eq", OrderReturnStatus.INIT.getValue());
        Integer refund = shOrderReturnService.countBySimpleSearch(simpleSearchDTO);
        resultObj.put("refund",refund);

        return R.ok(resultObj);
    }

    /**
     * 获取订单列表
     */
    @ApiOperation(value = "获取订单列表")
    @GetMapping("list")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "integer", name = "status", value = "1：代付款  2：待发货  6：待收货  8:已完成") })
    public R list(@LoginUser ShUserEntity loginUser,
                       @RequestParam(value = "status", defaultValue = "0") Integer status,
                       @RequestParam(value = "page", defaultValue = "1") Long page,
                       @RequestParam(value = "size", defaultValue = "10") Long size) {

        PageDTO<ShOrderEntity> pageDTO = shOrderService.findPageByUserId(page,size,loginUser.getId(),status);

        for (ShOrderEntity item : pageDTO.getList()) {
            SearchDTO simpleSearchDTO2 = new SearchDTO();
            simpleSearchDTO2.addFiled("order_id","eq",item.getId());
            //订单的商品
            List<ShOrderGoodsEntity> goodsList = shOrderGoodsService.findAllBySimpleSearch(simpleSearchDTO2);

            Integer goodsCount = 0;
            for (ShOrderGoodsEntity orderGoodsEntity : goodsList) {
                goodsCount += orderGoodsEntity.getNumber();
            }
            item.setGoodsCount(goodsCount);
            item.setShOrderGoodsEntityList(goodsList);
        }

        return R.ok().put("data",pageDTO);
    }

    /**
     * 获取订单详情
     */
    @ApiOperation(value = "获取订单详情")
    @PostMapping("detail")
    public R detail(@RequestBody ShOrderDTO shOrderDTO) {

        ShOrderEntity orderInfo = shOrderService.findByOrderNumber(shOrderDTO.getOrderNumber());
        if (BaseUtils.isEmpty(orderInfo)) {
            return R.error(400, "订单不存在");
        }
        //订单的商品
        List<ShOrderGoodsEntity> orderGoods = shOrderGoodsService.findByOrderId(orderInfo.getId());
        //订单可操作的选择,删除，支付，收货，评论，退换货
        Map handleOption = ShOrderUtils.getHandleOption(orderInfo);
        Map<String, Object> resultObj = new HashMap();
        resultObj.put("orderInfo", orderInfo);
        resultObj.put("orderGoods", orderGoods);
        resultObj.put("handleOption", handleOption);
        return R.ok(resultObj);
    }


    /**
     * 获取订单列表
     */
    @ApiOperation(value = "订单提交")
    @PostMapping("submit")
    public R submit(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderSubmitDTO submitDTO) {
        try {
            return shOrderService.submit(submitDTO, loginUser,this.getAppid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("提交失败");
    }

    /**
     * 获取订单列表
     */
    @ApiOperation(value = "取消订单")
    @PostMapping("cancelOrder")
    public R cancelOrder(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderDTO orderDTO) {
        try {
            ShOrderEntity order = shOrderService.findByOrderNumber(orderDTO.getOrderNumber());
            if (!ShOrderUtils.checkStatusPending(order,loginUser.getId())) {
                return R.error("订单状态异常");
            }
            return  shOrderService.cancelOrder(order,"用户取消订单", loginUser.getUsername(), loginUser.getId(),RequestHelper.getRemoteHost(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("订单取消失败");
    }

    /**
     * 确认收货
     */
    @ApiOperation(value = "确认收货")
    @PostMapping("confirmOrder")
    public R confirmOrder(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderDTO orderDTO) {
        try {
            ShOrderEntity order = shOrderService.findByOrderNumber(orderDTO.getOrderNumber());
            if (!ShOrderUtils.checkStatusPending(order,loginUser.getId())) {
                return R.error("订单状态异常");
            }
            return shOrderService.completeOrder(order,loginUser.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("确认收货失败");
    }
    /**
     * 订单退货
     */
    @ApiOperation(value = "订单退货")
    @PostMapping("returnOrder")
    public R returnOrder(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderDTO orderDTO) {
        try {
            ShOrderEntity order = shOrderService.findByOrderNumber(orderDTO.getOrderNumber());
            if(!ShOrderUtils.checkStatusSend(order,loginUser.getId())){
                return R.error("订单状态异常");
            }
            ShOrderReturnEntity orderReturnEntity =  shOrderReturnService.submitOrderReturn(loginUser,orderDTO.getComment(),order);
            return  R.ok().put("returnNumber",orderReturnEntity.getNumber());
        } catch (Exception e) {
            return  R.error("订单退货提交失败");
        }
    }
    /**
     * 订单退款
     */
    @ApiOperation(value = "订单退款")
    @PostMapping("refundOrder")
    public R refundOrder(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderDTO orderDTO) {
        try {
            ShOrderEntity order = shOrderService.findByOrderNumber(orderDTO.getOrderNumber());
            if(!ShOrderUtils.checkStatusPayed(order,loginUser.getId())){
                return R.error("订单状态异常");
            }
            ShOrderReturnEntity orderReturnEntity = shOrderReturnService.submitOrderRefund(loginUser,orderDTO.getComment(),order);
            return  R.ok().put("returnNumber",orderReturnEntity.getNumber());
        } catch (Exception e) {
            return  R.error("订单退款提交失败");
        }
    }

}
