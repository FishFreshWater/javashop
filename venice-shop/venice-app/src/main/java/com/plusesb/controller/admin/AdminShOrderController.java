package com.plusesb.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.plusesb.dto.form.ShOrderSendDTO;
import com.plusesb.entity.*;
import com.plusesb.entity.enums.OrderReturnCheckStatus;
import com.plusesb.entity.enums.OrderReturnStatus;
import com.plusesb.service.*;
import com.plusesb.utils.RequestHelper;
import com.plusesb.utils.ShOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.plusesb.controller.AbstractController;
import com.plusesb.dto.PageDTO;;
import com.plusesb.dto.SearchDTO;;
import com.plusesb.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@RestController
@RequestMapping("miniapp/shorder")
public class AdminShOrderController extends AdminShAbstractController{
    @Autowired
    private ShOrderService shOrderService;
    @Autowired
    private ShUserService shUserService;
    @Autowired
    private ShOrderGoodsService shOrderGoodsService;
    @Autowired
    private ShOrderPayService shOrderPayService;
    @Autowired
    private ShOrderLogService shOrderLogService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShOrderEntity> page = shOrderService.findPageBySql(simpleSearchDTO);
        for (ShOrderEntity item : page.getList()) {
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
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ShOrderEntity shOrder = shOrderService.getById(id);
        shOrder.setShUser(shUserService.getById(shOrder.getUserId()));
        List<ShOrderGoodsEntity> shOrderGoodsEntityList = shOrderGoodsService.findByOrderId(shOrder.getId());
        shOrder.setShOrderGoodsEntityList(shOrderGoodsEntityList);

        List<ShOrderPayEntity> orderPayList = shOrderPayService.findByOrderId(id);

        List<ShOrderLogEntity> orderLogList =shOrderLogService.findByOrderId(id);

        return R.ok().put("shOrder", shOrder).put("orderPayList",orderPayList).put("orderLogList",orderLogList);
    }
    /**
     * 发货
     */
    @PostMapping("/send")
    public R send(@RequestBody ShOrderSendDTO sendDTO){

        shOrderService.sendOrder(sendDTO);
        return R.ok();
    }
    /**
     * 发货
     */
    @PostMapping("/cancel/{id}")
    public R send(@PathVariable("id") Long id, HttpServletRequest request){

        ShOrderEntity order = shOrderService.getById(id);

        return  shOrderService.cancelOrder(order,"管理员取消订单", this.getUser().getUsername(), this.getUserId(), RequestHelper.getRemoteHost(request));
    }


}
