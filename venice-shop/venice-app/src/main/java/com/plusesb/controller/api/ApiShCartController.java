package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.annotation.LoginUser;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.dto.BuyGoodsDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.ShCouponDTO;
import com.plusesb.dto.form.CartForm;
import com.plusesb.entity.*;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import com.plusesb.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "购物车接口")
@RestController
@RequestMapping("api/sh_cart")
public class ApiShCartController extends ApiShAbstractController{
    @Autowired
    private ShCartService shCartService;
    @Autowired
    private ShCouponService shCouponService;
    @Autowired
    private ShCouponUserService shCouponUserService;
    @Autowired
    private ShGoodsService shGoodsService;
    @Autowired
    private ShProductService shProductService;
    @Autowired
    private ShAddressService shAddressService;
    @Autowired
    private RedisUtils redisUtils;

    private R getCart(ShUserEntity loginUser) {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("user_id","eq",loginUser.getId());

        List<ShCartEntity> cartList = shCartService.findByUserId(loginUser.getId());
        //获取购物车统计信息
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for (ShCartEntity cartItem : cartList) {
            goodsCount += cartItem.getNumber();
            ShProductEntity product = shProductService.getById(cartItem.getProductId());
            goodsAmount = goodsAmount.add(product.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                checkedGoodsCount += cartItem.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(product.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
            }
        }

        Map<String, Object> resultObj = new HashMap();

        Map<String, Object> cartTotal = new HashMap();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        resultObj.put("cartList", cartList);
        resultObj.put("cartTotal", cartTotal);
        return R.ok(resultObj);

    }
    /**
     * 获取购物车信息，所有对购物车的增删改操作，都要重新返回购物车的信息
     */
    @ApiOperation(value = "获取购物车信息")
    @PostMapping("index")
    public R index(@LoginUser ShUserEntity loginUser) {
        return getCart(loginUser);
    }

    /**
     * 添加商品到购物车
     */
    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("add")
    public R add(@LoginUser ShUserEntity loginUser, @RequestBody CartForm cartForm) {
        Long goodsId = cartForm.getGoodsId();
        Long productId = cartForm.getProductId();
        Integer number = cartForm.getNumber();
        //判断商品是否可以购买
        ShGoodsEntity goodsInfo = shGoodsService.getById(goodsId);
        if (null == goodsInfo || goodsInfo.getIsDelete() == 1 || goodsInfo.getIsOnSale()!=1) {
            return R.error(400,"商品已下架");
        }
        //取得规格的信息,判断规格库存
        ShProductEntity productInfo = shProductService.getById(productId);
        if (null == productInfo || productInfo.getStockNumber() < number) {
            return R.error(400,"库存不足");
        }

        SearchDTO cartParam = new SearchDTO();
        cartParam.addFiled("goods_id","eq",goodsId);
        cartParam.addFiled("product_id","eq",productId);
        cartParam.addFiled("user_id","eq",loginUser.getId());
        cartParam.addFiled("appid","eq",this.getAppid());

        List<ShCartEntity> cartInfoList = shCartService.findAllBySimpleSearch(cartParam);

        ShCartEntity cartInfo = null;
        if (BaseUtils.isNotEmpty(cartInfoList)){
            cartInfo = cartInfoList.get(0);
        }
        if (null == cartInfo) {
            cartInfo = new ShCartEntity();

            cartInfo.setGoodsId(goodsId);
            cartInfo.setProductId(productId);
            cartInfo.setNumber(number);
            cartInfo.setSpecValue(productInfo.getSpecValue());
            cartInfo.setThumbnailUrl(productInfo.getThumbnailUrl());
            cartInfo.setAppid(this.getAppid());
            cartInfo.setUserId(loginUser.getId());
            cartInfo.setChecked(1);
            shCartService.save(cartInfo);
        } else {
            //如果已经存在购物车中，则数量增加
            if (productInfo.getStockNumber() < (number + cartInfo.getNumber())) {
                return R.error(400,"库存不足");
            }
            cartInfo.setNumber(cartInfo.getNumber() + number);
            shCartService.updateById(cartInfo);
        }
        return getCart(loginUser);
    }

    /**
     * 减少商品到购物车
     */
    @ApiOperation(value = "减少商品到购物车")
    @PostMapping("minus")
    public R minus(@LoginUser ShUserEntity loginUser, @RequestBody CartForm cartForm) {

        Long goodsId = cartForm.getGoodsId();
        Long productId = cartForm.getProductId();
        Integer number = cartForm.getNumber();
        //判断购物车中是否存在此规格商品
        SearchDTO cartParam = new SearchDTO();
        cartParam.addFiled("goods_id","eq",goodsId);
        cartParam.addFiled("product_id","eq",productId);
        cartParam.addFiled("user_id","eq",loginUser.getId());
        cartParam.addFiled("appid","eq",this.getAppid());

        List<ShCartEntity> cartInfoList = shCartService.findAllBySimpleSearch(cartParam);

        ShCartEntity cartInfo = null;
        if (BaseUtils.isNotEmpty(cartInfoList)){
            cartInfo = cartInfoList.get(0);
        }
        int cart_num = 0;
        if (null != cartInfo) {
            if (cartInfo.getNumber() > number) {
                cartInfo.setNumber(cartInfo.getNumber() - number);
                shCartService.updateById(cartInfo);
                cart_num = cartInfo.getNumber();
            } else if (cartInfo.getNumber() == 1) {
                shCartService.removeById(cartInfo.getId());
                cart_num = 0;
            }
        }
        return R.ok().put("item",cart_num);
    }

    /**
     * 更新指定的购物车信息
     */
    @ApiOperation(value = "更新指定的购物车信息")
    @PostMapping("update")
    public R update(@LoginUser ShUserEntity loginUser, @RequestBody CartForm cartForm) {


        Long goodsId = cartForm.getGoodsId();
        Long productId = cartForm.getProductId();
        Integer number = cartForm.getNumber();
        Long id = cartForm.getId();
        //取得规格的信息,判断规格库存
        ShProductEntity productInfo = shProductService.getById(productId);
        if (null == productInfo || productInfo.getStockNumber() < number) {
            return R.error(400,"库存不足");
        }
        //判断是否已经存在product_id购物车商品
        ShCartEntity cartInfo = shCartService.getById(id);
        //只是更新number
        if (cartInfo.getProductId().equals(productId)) {
            cartInfo.setNumber(number);
            shCartService.updateById(cartInfo);
            return getCart(loginUser);
        }

        SearchDTO cartParam = new SearchDTO();
        cartParam.addFiled("goods_id","eq",goodsId);
        cartParam.addFiled("product_id","eq",productId);
        cartParam.addFiled("user_id","eq",loginUser.getId());
        cartParam.addFiled("appid","eq",this.getAppid());

        List<ShCartEntity> cartInfoList = shCartService.findAllBySimpleSearch(cartParam);
        ShCartEntity newcartInfo = null;
        if (BaseUtils.isNotEmpty(cartInfoList)){
            newcartInfo = cartInfoList.get(0);
        }
        if (null == newcartInfo) {
            cartInfo = new ShCartEntity();

            cartInfo.setGoodsId(goodsId);
            cartInfo.setProductId(productId);
            cartInfo.setNumber(number);
            cartInfo.setUserId(loginUser.getId());
            cartInfo.setChecked(1);
            shCartService.save(cartInfo);
        } else {
            //合并购物车已有的product信息，删除已有的数据
            //如果已经存在购物车中，则数量增加
            if (productInfo.getStockNumber() < (number + cartInfo.getNumber())) {
                return R.error(400,"库存不足");
            }
            cartInfo.setNumber(cartInfo.getNumber() + number);
            shCartService.updateById(cartInfo);
        }
        return getCart(loginUser);
    }



    /**
     * 是否选择商品，如果已经选择，则取消选择，批量操作
     */
    @ApiOperation(value = "是否选择商品")
    @PostMapping("checked")
    public R checked(@LoginUser ShUserEntity loginUser) {
        JSONObject jsonParam = getJsonRequest();
        String productIds = jsonParam.getString("productIds");
        Integer isChecked = jsonParam.getInteger("isChecked");
        if (BaseUtils.isEmpty(productIds)) {
            return R.error("选择商品异常");
        }
        String[] productIdArray = productIds.split(",");
        shCartService.updateCheck(productIdArray, isChecked, loginUser.getId());
        return getCart(loginUser);
    }

    //删除选中的购物车商品，批量删除
    @ApiOperation(value = "删除商品")
    @PostMapping("delete")
    public Object delete(@LoginUser ShUserEntity loginUser) {
        Long userId = loginUser.getId();

        JSONObject jsonObject = getJsonRequest();
        String productIds = jsonObject.getString("productIds");

        if (BaseUtils.isEmpty(productIds)) {
            return R.error("删除出错");
        }
        String[] productIdsArray = productIds.split(",");
        shCartService.deleteByUserAndProductIds(userId, productIdsArray);

        return getCart(loginUser);
    }

    @ApiOperation(value = "获取购物车商品的总件件数")
    @GetMapping("goodscount")
    public R goodscount(@LoginUser ShUserEntity loginUser) {

        if (null == loginUser || null == loginUser.getId()) {
            return R.error("未登录");
        }
        //查询列表数据
        SearchDTO param = new SearchDTO();
        param.addFiled("user_id","eq",loginUser.getId());
        param.addFiled("appid","eq",this.getAppid());
        List<ShCartEntity> cartList = shCartService.findAllBySimpleSearch(param);
        //获取购物车统计信息
        Integer goodsCount = 0;
        for (ShCartEntity cartItem : cartList) {
            goodsCount += cartItem.getNumber();
        }
        Map<String, Object> resultObj = new HashMap();
        resultObj.put("cartList", cartList);
        resultObj.put("goodsCount", goodsCount);
        return R.ok(resultObj);
    }

    /**
     * 订单提交前的检验和填写相关订单信息
     */
    @ApiOperation(value = "订单提交前的检验和填写相关订单信息")
    @PostMapping("checkout")
    public R checkout(@LoginUser ShUserEntity loginUser) {
        Map<String, Object> resultObj = new HashMap();
        JSONObject jsonParam = getJsonRequest();
        Long couponId = jsonParam.getLong("couponId");
        String type = jsonParam.getString("type");

        //根据收货地址计算运费
        BigDecimal freightPrice = new BigDecimal(0.00);
        //默认收货地址
        SearchDTO param = new SearchDTO();
        param.addFiled("user_id","eq",loginUser.getId());
        List<ShAddressEntity> addressEntities = shAddressService.findAllBySimpleSearch(param);

        if (null == addressEntities || addressEntities.size() == 0) {
            resultObj.put("checkedAddress", null);
        } else {
            resultObj.put("checkedAddress", addressEntities.get(0));
        }
        // * 获取要购买的商品和总价
        ArrayList checkedGoodsList = new ArrayList();
        BigDecimal goodsTotalPrice;
        if (type.equals("cart")) {
            Map<String, Object> cartData = (Map<String, Object>) this.getCart(loginUser).get("data");

            for (ShCartEntity cartEntity : (List<ShCartEntity>) cartData.get("cartList")) {
                if (cartEntity.getChecked() == 1) {
                    checkedGoodsList.add(cartEntity);
                }
            }
            goodsTotalPrice = (BigDecimal) ((HashMap) cartData.get("cartTotal")).get("checkedGoodsAmount");
        } else { // 是直接购买的
            BuyGoodsDTO goodsVO = redisUtils.get(ShSysConstant.SHOP_CACHE_NAME+"_goods" + loginUser.getId(),BuyGoodsDTO.class);
            ShProductEntity productInfo = shProductService.getById(goodsVO.getProductId());
            ShGoodsEntity shGoods = shGoodsService.getById(goodsVO.getGoodsId());
            //计算订单的费用
            //商品总价
            goodsTotalPrice = productInfo.getRetailPrice().multiply(new BigDecimal(goodsVO.getNumber()));

            ShCartEntity cartVo = new ShCartEntity();
            cartVo.setGoodsId(goodsVO.getGoodsId());
            cartVo.setProductId(goodsVO.getProductId());
            cartVo.setNumber(goodsVO.getNumber());
            cartVo.setUserId(loginUser.getId());
            cartVo.setNumber(goodsVO.getNumber());
            cartVo.setThumbnailUrl(productInfo.getThumbnailUrl());
            cartVo.setSpecValue(productInfo.getSpecValue());
            cartVo.setRetailPrice(productInfo.getRetailPrice());
            cartVo.setGoodsName(shGoods.getName());
            checkedGoodsList.add(cartVo);
        }


        //获取可用的优惠券信息
        BigDecimal couponPrice = new BigDecimal(0.00);
        if (BaseUtils.isNotEmpty(couponId) && couponId != 0) {
            ShCouponDTO couponVo = shCouponUserService.findByUserIdAndCouponId(loginUser.getId(),couponId);
            if (BaseUtils.isNotEmpty(couponVo)) {
                couponPrice = couponVo.getCouponBalance();
            }
        }

        //订单的总价
        //运费
        BigDecimal orderTotalPrice = goodsTotalPrice;
        //TODO满多少包邮
//        ShConfigEntity config = shConfigService.findByAppid(this.getAppid());
//        if(goodsTotalPrice.compareTo(config.getFreeFreightPrice())<0){
//            orderTotalPrice = goodsTotalPrice.add(config.getFreightPrice());
//            freightPrice = config.getFreightPrice();
//        }

        //订单总价
        BigDecimal actualPrice = orderTotalPrice.subtract(couponPrice);
        resultObj.put("freightPrice", freightPrice);
        resultObj.put("couponPrice", couponPrice);
        resultObj.put("checkedGoodsList", checkedGoodsList);
        resultObj.put("goodsTotalPrice", goodsTotalPrice);
        resultObj.put("orderTotalPrice", orderTotalPrice);
        resultObj.put("actualPrice", actualPrice);
        return R.ok(resultObj);
    }

    /**
     * 选择优惠券列表
     */
    @ApiOperation(value = "选择优惠券列表")
    @PostMapping("checkedCouponList")
    public R checkedCouponList(@LoginUser ShUserEntity loginUser) {
        //
        Map param = new HashMap();
        param.put("user_id", loginUser.getId());
        List<ShCouponUserEntity> couponVos = shCouponUserService.queryUserCouponList(param);
        if (null != couponVos && couponVos.size() > 0) {
            // 获取要购买的商品
            Map<String, Object> cartData = (Map<String, Object>) this.getCart(loginUser);
            List<ShCartEntity> checkedGoodsList = new ArrayList();
            List<Long> checkedGoodsIds = new ArrayList();
            for (ShCartEntity cartEntity : (List<ShCartEntity>) cartData.get("cartList")) {
                if (cartEntity.getChecked() == 1) {
                    checkedGoodsList.add(cartEntity);
                    checkedGoodsIds.add(cartEntity.getId());
                }
            }
            // 计算订单的费用
            BigDecimal goodsTotalPrice = (BigDecimal) ((HashMap) cartData.get("cartTotal")).get("checkedGoodsAmount");  //商品总价

            for (ShCouponUserEntity couponUser : couponVos) {
                ShCouponEntity shCouponEntity = shCouponService.getById(couponUser.getCouponId());
                if (shCouponEntity.getLimitBalance().compareTo(goodsTotalPrice) <= 0) {
                    couponUser.setEnabled(1);
                }
            }
        }
        return R.ok().put("item",couponVos);
    }

}
