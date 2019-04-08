package com.plusesb.controller.admin;

import com.plusesb.controller.AbstractController;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.enums.OrderPayStatus;
import com.plusesb.entity.enums.OrderReturnStatus;
import com.plusesb.entity.enums.OrderShippingStatus;
import com.plusesb.entity.enums.OrderStatus;
import com.plusesb.service.ShOrderReturnService;
import com.plusesb.service.ShOrderService;
import com.plusesb.service.ShUserService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 会员信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@RestController
@RequestMapping("miniapp/sh_index")
public class AdminShIndexController extends AdminShAbstractController{


    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private ShOrderReturnService shOrderReturnService;
    @Autowired
    private ShUserService shUserService;

    /**
     * 商城首页
     */
    @ApiOperation(value = "商城首页获取商城数据" ,notes = "商城首页获取商城数据")
    @RequestMapping("/index")
    public R index(){


        Map<String, Object> resultObj = new HashMap();
        Map map = new HashMap();
        //今日待发货订单
        map.put("appid",getAppid());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
        map.put("pay_time", BaseUtils.getDateString(new Date()));
        Integer todayOrder = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("todayOrder",todayOrder);

        //查询待发货数量
        map = new HashMap();
        map.put("appid",getAppid());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
        Integer payed = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("payed",payed);

        //查询待收货数量
        map = new HashMap();
        map.put("appid",getAppid());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.SEND.getValue());
        Integer send = shOrderService.countByUserIdAndStatusSql(map);
        resultObj.put("send",send);

        //查询售后服务数量
        SearchDTO simpleSearchDTO = new SearchDTO();
        map.put("appid",getAppid());
        simpleSearchDTO.addFiled("status","eq", OrderReturnStatus.INIT.getValue());
        Integer refund = shOrderReturnService.countBySimpleSearch(simpleSearchDTO);
        resultObj.put("refund",refund);

        //今日新增用户
        simpleSearchDTO = new SearchDTO();
        map.put("appid",getAppid());
        simpleSearchDTO.addFiled("create_time","ge", BaseUtils.getDateString(new Date()));
        Integer newUser = shUserService.countBySimpleSearch(simpleSearchDTO);
        resultObj.put("newUser",newUser);

        //查询待发货数量
        map = new HashMap();
        map.put("appid",getAppid());
        map.put("order_status",OrderStatus.SUBMIT.getValue());
        map.put("pay_status",OrderPayStatus.PAYED.getValue());
        map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
        map.put("pay_time", BaseUtils.getDateString(new Date()));
        BigDecimal todayMoney = shOrderService.sumTodayMoney(map);
        resultObj.put("todayMoney",todayMoney);

        return R.ok(resultObj);
    }




}
