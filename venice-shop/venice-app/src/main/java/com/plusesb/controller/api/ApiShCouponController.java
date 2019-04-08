package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.annotation.LoginUser;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.dto.BuyGoodsDTO;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.ShCouponDTO;
import com.plusesb.entity.ShCartEntity;
import com.plusesb.entity.ShCouponEntity;
import com.plusesb.entity.ShProductEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.entity.enums.CouponStatue;
import com.plusesb.service.ShCartService;
import com.plusesb.service.ShCouponService;
import com.plusesb.service.ShCouponUserService;
import com.plusesb.service.ShProductService;
import com.plusesb.utils.R;
import com.plusesb.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 优惠券接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "优惠券接口")
@RestController
@RequestMapping("api/sh_coupon")
public class ApiShCouponController extends ApiShAbstractController{

    @Autowired
    private ShCouponUserService shCouponUserService;
    @Autowired
    private ShCouponService shCouponService;
    @Autowired
    private ShCartService shCartService;
    @Autowired
    private ShProductService shProductService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取用户优惠券列表
     */
    @ApiOperation(value = "获取用户优惠券列表")
    @GetMapping("user_list")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "integer", name = "status", value = "1. 可用 2. 已用 3. 过期") })
    public R userList(@LoginUser ShUserEntity loginUser,
                       @RequestParam(value = "status", defaultValue = "0") Integer status,
                       @RequestParam(value = "page", defaultValue = "1") Long page,
                       @RequestParam(value = "size", defaultValue = "10") Long size) {

        //更新优惠券过期数据
        shCouponUserService.updateTimeOutStatus(loginUser.getId());

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("userId","eq",loginUser.getId());
        simpleSearchDTO.addFiled("status","eq",status);
        simpleSearchDTO.setPageSize(size);
        simpleSearchDTO.setPageIndex(page);
        PageDTO<ShCouponDTO> pageDTO = shCouponUserService.findPageByUserIdAndStatus(simpleSearchDTO);

        Map restMap = new HashMap();
        restMap.put("page",pageDTO);

        //统计不同状态数据
        //可用数量
        SearchDTO params = new SearchDTO();
        params.addFiled("user_id","eq",loginUser.getId());
        params.addFiled("coupon_status","eq", CouponStatue.USEABLE.getValue());
        Integer useable = shCouponUserService.countBySimpleSearch(params);

        //不可用数量
        params.deleteByFiled("coupon_status");
        params.addFiled("coupon_status","eq", CouponStatue.USED.getValue());
        Integer used = shCouponUserService.countBySimpleSearch(params);

        //已过期
        params.deleteByFiled("coupon_status");
        params.addFiled("coupon_status","eq", CouponStatue.EXPIR.getValue());
        Integer expir = shCouponUserService.countBySimpleSearch(params);

        restMap.put("useable",useable);
        restMap.put("used",used);
        restMap.put("expir",expir);

        return R.ok(restMap);
    }
    /**
     * 获取优惠券列表
     */
    @ApiOperation(value = "获取优惠券列表")
    @GetMapping("list")
    public R list(@LoginUser ShUserEntity loginUser,
                  @RequestParam(value = "page", defaultValue = "1") Long page,
                  @RequestParam(value = "size", defaultValue = "10") Long size) {


        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setPageSize(size);
        simpleSearchDTO.setPageIndex(page);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("userId","eq",loginUser.getId());
        PageDTO<ShCouponEntity> pageDTO = shCouponService.findPageByAppId(simpleSearchDTO);
        return R.ok().put("data",pageDTO);
    }

    /**
     * 领取优惠券
     */
    @ApiOperation(value = "领取优惠券")
    @PostMapping("exchange")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "integer", name = "id", value = "优惠券id") })
    public R exchange(@LoginUser ShUserEntity loginUser) {

        JSONObject jsonParam = this.getJsonRequest();
        Long id = jsonParam.getLongValue("id");

        return shCouponUserService.exchange(loginUser.getId(),id);
    }

    /**
     * 根据商品获取可用优惠券列表
     */
    @ApiOperation(value = "根据商品获取可用优惠券列表")
    @GetMapping("/listByGoods")
    public R listByGoods(@RequestParam(defaultValue = "cart") String type, @LoginUser ShUserEntity
            loginUser) {
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        if (type.equals("cart")) {
            List<ShCartEntity> cartList = shCartService.findByUserId(loginUser.getId());
            //获取购物车统计信息
            for (ShCartEntity cartItem : cartList) {
                if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                    goodsTotalPrice = goodsTotalPrice.add(cartItem.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
                }
            }
        } else {
            // 是直接购买的
            BuyGoodsDTO goodsVO = redisUtils.get(ShSysConstant.SHOP_CACHE_NAME+"_goods" + loginUser.getId(),BuyGoodsDTO.class);
            ShProductEntity productInfo = shProductService.getById(goodsVO.getProductId());
            //商品总价
            goodsTotalPrice = productInfo.getRetailPrice().multiply(new BigDecimal(goodsVO.getNumber()));
        }

        // 获取可用优惠券
        shCouponUserService.updateTimeOutStatus(loginUser.getId());
        List<ShCouponDTO> couponVos = shCouponUserService.findByUserIdAndCouponStatus(loginUser.getId(),1);
        List<ShCouponDTO> useCoupons = new ArrayList<>();
        List<ShCouponDTO> notUseCoupons = new ArrayList<>();
        for (ShCouponDTO couponVo : couponVos) {
            if (goodsTotalPrice.compareTo(couponVo.getLimitBalance()) >= 0) {
                couponVo.setEnabled(1);
                useCoupons.add(couponVo);
            } else {
                couponVo.setEnabled(0);
                notUseCoupons.add(couponVo);
            }
        }
        Map restMap = new HashMap();
        restMap.put("useCoupons",useCoupons);
        restMap.put("notUseCoupons",notUseCoupons);
        return R.ok(restMap);
    }

}
