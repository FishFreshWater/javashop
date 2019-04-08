package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.ShOrderDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.form.ShOrderSubmitDTO;
import com.plusesb.entity.*;
import com.plusesb.service.ShGoodsService;
import com.plusesb.service.ShOrderGoodsService;
import com.plusesb.service.ShOrderReturnService;
import com.plusesb.service.ShOrderService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import com.plusesb.utils.RequestHelper;
import com.plusesb.utils.ShOrderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 售后服务接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "售后服务接口")
@RestController
@RequestMapping("api/sh_return")
public class ApiShOrderReturnController extends ApiShAbstractController{

    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private ShOrderReturnService shOrderReturnService;
    @Autowired
    private ShOrderGoodsService shOrderGoodsService;

    /**
     * 售后服务 -- >订单详情
     */
    @ApiOperation(value = "售后服务-订单详情")
    @GetMapping("detail")
    public R detail(@LoginUser ShUserEntity loginUser,String returnNumber) {

        Map map = new HashMap();
        ShOrderReturnEntity orderReturn = shOrderReturnService.findByReturnNumber(returnNumber);
        ShOrderEntity orderInfo = shOrderService.getById(orderReturn.getOrderId());
        List<ShOrderGoodsEntity> orderGoods = shOrderGoodsService.findByOrderId(orderInfo.getId());
        map.put("orderReturn",orderReturn);
        map.put("orderGoods",orderGoods);
        map.put("orderInfo",orderInfo);
        return  R.ok(map);
    }
    /**
     * 售后服务 - 获取订单列表
     */
    @ApiOperation(value = "售后服务-获取订单列表")
    @GetMapping("list")
    public R list(@LoginUser ShUserEntity loginUser,
                       @RequestParam(value = "page", defaultValue = "1") Long page,
                       @RequestParam(value = "size", defaultValue = "10") Long size) {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setPageIndex(page);
        simpleSearchDTO.setPageSize(size);
        simpleSearchDTO.addFiled("user_id","eq",loginUser.getId());
        simpleSearchDTO.addSort("create_time",false);

        PageDTO<ShOrderReturnEntity> pageDTO = shOrderReturnService.findPageBySimpleSearch(simpleSearchDTO);

        for (ShOrderReturnEntity orderReturnEntity : pageDTO.getList()) {
            ShOrderEntity item = shOrderService.getById(orderReturnEntity.getOrderId());
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
            orderReturnEntity.setShOrderEntity(item);
        }

        return R.ok().put("data",pageDTO);
    }
    /**
     *  退款退货上传货单
     * @param orderReturn
     * @return
     */
    @ApiOperation(value = "售后服务-退款退货上传货单")
    @PostMapping("tracking")
    public R tracking(@LoginUser ShUserEntity loginUser,@RequestBody ShOrderReturnEntity orderReturn) {


        ShOrderReturnEntity orderReturnEntity = shOrderReturnService.getById(orderReturn.getId());
        ShOrderEntity order = shOrderService.getById(orderReturnEntity.getOrderId());
        ShOrderUtils.checkStatusSend(order,loginUser.getId());
        orderReturnEntity.setTracking(orderReturn.getTracking());
        orderReturnEntity.setExpressCrop(orderReturn.getExpressCrop());
        orderReturnEntity.setExpressDesc(orderReturn.getExpressDesc());
        return shOrderReturnService.submitTracking(orderReturnEntity,loginUser.getUsername());
    }

}
