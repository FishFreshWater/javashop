package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.annotation.LoginUser;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.dto.BuyGoodsDTO;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.utils.R;
import com.plusesb.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商城首页
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "直接购买接口")
@RestController
@RequestMapping("api/sh_buy")
public class ApiShBuyController extends ApiShAbstractController{

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "商品添加")
    @PostMapping("/add")
    public R addBuy(@LoginUser ShUserEntity loginUser) {
        JSONObject jsonParam = getJsonRequest();
        Long goodsId = jsonParam.getLongValue("goodsId");
        Long productId = jsonParam.getLongValue("productId");
        Integer number = jsonParam.getInteger("number");
        BuyGoodsDTO goodsVo = new BuyGoodsDTO();
        goodsVo.setGoodsId(goodsId);
        goodsVo.setProductId(productId);
        goodsVo.setNumber(number);
        redisUtils.set(ShSysConstant.SHOP_CACHE_NAME+"_goods" + loginUser.getId(), goodsVo,600);
        return R.ok("添加成功");
    }
}
