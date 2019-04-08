package com.plusesb.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.plusesb.config.WxPayProperties;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.dto.BuyGoodsDTO;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.form.ShOrderSendDTO;
import com.plusesb.dto.form.ShOrderSubmitDTO;
import com.plusesb.entity.*;
import com.plusesb.entity.enums.*;
import com.plusesb.exception.RRException;
import com.plusesb.mapper.ShOrderMapper;
import com.plusesb.service.*;
import com.plusesb.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("shOrderService")
@Slf4j
public class ShOrderServiceImpl extends BaseServiceImpl<ShOrderMapper, ShOrderEntity> implements ShOrderService {

    @Autowired
    private ShOrderGoodsService shOrderGoodsService;
    @Autowired
    private ShOrderPayService shOrderPayService;
    @Autowired
    private ShGoodsService shGoodsService;
    @Autowired
    private ShAddressService shAddressService;
    @Autowired
    private ShOrderLogService shOrderLogService;
    @Autowired
    private ShCartService shCartService;
    @Autowired
    private ShCouponService shCouponService;
    @Autowired
    private ShCouponUserService shCouponUserService;
    @Autowired
    private ShProductService shProductService ;
    @Autowired
    private ShOrderReturnService shOrderReturnService;
    @Autowired
    private ShMessageService shMessageService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WxPayProperties wxPayProperties;
    @Autowired
    private RedisUtils redisUtils;
    @Value("${wx.pay.notify_url}")
    private String backUrl;
    @Override
    public PageDTO<ShOrderEntity> findPageBySql(SearchDTO simpleSearchDTO) {

        Page<ShOrderEntity> page = new Page<>(simpleSearchDTO.getPageIndex(), simpleSearchDTO.getPageSize());
        Map map = new HashMap();
        map.put("appid",simpleSearchDTO.getDataByFiled("appid").toString());

        if (BaseUtils.isNotEmpty(simpleSearchDTO.getDataByFiled("number"))){
            map.put("order_number",simpleSearchDTO.getDataByFiled("number").toString());
        }
        if (BaseUtils.isNotEmpty(simpleSearchDTO.getDataByFiled("status"))){
            map = ShOrderUtils.getStatusBySingleOrderStatus(map,Integer.parseInt(simpleSearchDTO.getDataByFiled("status").toString()));
        }

        page.setRecords(this.baseMapper.findPageBySql(page, map));
        return new PageDTO<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R payPrepay(ShUserEntity loginUser, Long orderId, String appid, String remoteHost) {
        String nonceStr = BaseUtils.getRandomString(32);

        Map<Object, Object> resultObj = new TreeMap();
        ShOrderEntity orderInfo = baseMapper.selectById(orderId);
        String payNumber = ShOrderUtils.getPayNumberByOrderNumber(orderInfo.getOrderNumber());


        try {
            Map<Object, Object> parame = new TreeMap<Object, Object>();
            //订单的商品
            List<ShOrderGoodsEntity> orderGoods = shOrderGoodsService.findByOrderId(orderId);
            String body = "VENICE-";
            if (null != orderGoods) {

                for (ShOrderGoodsEntity goodsVo : orderGoods) {
                    ShGoodsEntity shGoodsEntity = shGoodsService.getById(goodsVo.getGoodsId());
                    body = body + shGoodsEntity.getName() + "、";
                }
                if (body.length() > 0) {
                    body = body.substring(0, body.length() - 1);
                }
                // 商品描述
                parame.put("body", body);
            }
            //支付金额
            Integer paymoney =orderInfo.getActualPrice().multiply(new BigDecimal(100)).intValue();

            String xml = MapUtils.convertMap2Xml(parame);
            log.info("xml:" + xml);
            WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder().outTradeNo(payNumber)
                    .notifyUrl(backUrl).totalFee(paymoney).body(body).tradeType("JSAPI")
                    .spbillCreateIp(remoteHost).openid(loginUser.getOpenid()).build();
            WxPayUnifiedOrderResult resultUn = wxPayService.unifiedOrder(request);
            // 响应报文
            //
            if (resultUn.getReturnCode().equals("FAIL")) {
                return R.error("支付失败," + resultUn.getReturnMsg());
            } else if (resultUn.getReturnCode().equals("SUCCESS")) {
                // 返回数据
                if (resultUn.getResultCode().equals("FAIL")) {
                    return R.error("支付失败," + resultUn.getErrCodeDes());
                } else if (resultUn.getResultCode().equals("SUCCESS")) {
                    // 先生成paySign 参考https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
                    resultObj.put("appId", wxPayProperties.getAppId());
                    resultObj.put("timeStamp", BaseUtils.getDatetimeString(new Date()));
                    resultObj.put("nonceStr", nonceStr);
                    resultObj.put("package", "prepay_id=" + resultUn.getPrepayId());
                    resultObj.put("signType", "MD5");
                    resultObj.put("paySign", resultUn.getSign());
                    shOrderPayService.saveOrderPayInfo(resultUn.getPrepayId(), orderInfo.getActualPrice(), OrderPayInfoStatus.UNPAY.getValue(), payNumber,orderInfo.getId());
                    return R.ok("微信统一订单下单成功").put("data",resultObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("下单失败,error=" + e.getMessage());
        }
        return R.error("下单失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R payOrder(ShOrderEntity order, String desc, String transactionId, String outTradeNo, BigDecimal totalFree, String remoteHost, String payType) {

        return changeStatusToPayed(order, OrderStatus.SUBMIT.getValue(),
                OrderPayStatus.PAYED.getValue(), OrderShippingStatus.UN_SEND.getValue(), OrderPayInfoStatus.PAYED.getValue(),
                desc,transactionId,
                outTradeNo,totalFree,payType);
    }


    private R changeStatusToPayed(ShOrderEntity order, String status, String payStatus, String shipmentStatus, Integer orderPayInfoStatus,
                                  String content, String transactionId, String payNumber, BigDecimal totalFree, String payType) {
        //微信支付宝回调会多次调用程序，如果订单状态位已支付则不在执行方法
        if (order.getOrderStatus().equals(status) && order.getPayStatus().equals(payStatus)
                && order.getShippingStatus().equals(shipmentStatus)) {
            return R.ok("订单状态已修改");
        } else {
            changeOrderStatus(order, status, payStatus, shipmentStatus, content, "微信登陆");
            log.info("payNumber:"+payNumber);
            if (!BaseUtils.isEmpty(payNumber)){
                ShOrderPayEntity orderPayInfo = shOrderPayService.findByPayNumber(payNumber);
                log.info("orderPayInfo:"+orderPayInfo.getId());
                if(BaseUtils.isNotEmpty(orderPayInfo)){
                    orderPayInfo.setStatus(orderPayInfoStatus);
                    orderPayInfo.setPayTime(new Date());
                    orderPayInfo.setThirdPayId(transactionId);
                    orderPayInfo.setPayType(payType);
                    shOrderPayService.updateById(orderPayInfo);
                }
                try {
                    checkOrderTotal(order,totalFree,orderPayInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    return R.error(e.getMessage());
                }
            }
        }
        order.setPayTime(new Date());
        //变更产品销量
        shGoodsService.addSellVolumeByOrderId(order.getId());
        baseMapper.updateById(order);
        shMessageService.sendMsgPayed(order);
        //删除redis中队列信息
        redisUtils.delete(ShSysConstant.ORDER_PENDING+order.getOrderNumber());
        return R.ok("订单支付成功");
    }
    /**
     * 校验订单支付金额
     * @param order
     * @param totalFree
     * @param opi
     */
    private void checkOrderTotal(ShOrderEntity order, BigDecimal totalFree,ShOrderPayEntity opi) {
        if (null != opi){
            if(opi.getPayBalance().compareTo(totalFree)!=0){
                throw new RRException("订单支付金额有误！");
            }
        }
        List<ShOrderPayEntity> orderPayInfos = shOrderPayService.findByOrderIdAndStatus(order.getId(),OrderPayInfoStatus.PAYED.getValue());
        BigDecimal total = BigDecimal.ZERO;
        for (ShOrderPayEntity orderPayInfo:orderPayInfos){
            total = total.add(orderPayInfo.getPayBalance());
        }
        if (order.getActualPrice().compareTo(total)!=0) {
            throw new RRException("订单支付总金额有误！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R submit(ShOrderSubmitDTO submitDTO, ShUserEntity loginUser, String appid) {

        Map<String, Object> resultObj = new HashMap<String, Object>();
        List<ShOrderGoodsEntity> shOrderGoodsEntities = new ArrayList<>();

        if (submitDTO.getType().equals("cart")) {
            // * 获取要购买的商品
            List<ShCartEntity> checkedGoodsList = shCartService.findByUserIdAndChecked(loginUser.getId(), 1);
            if (BaseUtils.isEmpty(checkedGoodsList)) {
                return R.error("请选择商品");
            }
            //统计商品总价
            for (ShCartEntity cartItem : checkedGoodsList) {
                ShOrderGoodsEntity dto = new ShOrderGoodsEntity();
                ShProductEntity productEntity = shProductService.getById(cartItem.getProductId());
                ShGoodsEntity goodsEntity = shGoodsService.getById(cartItem.getGoodsId());
                //判断库存
                if (productEntity.getStockNumber()<cartItem.getNumber()){
                    return R.error("库存不足");
                }
                dto.setGoodsId(cartItem.getGoodsId());
                dto.setGoodsName(goodsEntity.getName());
                dto.setSpecValue(productEntity.getSpecValue());
                dto.setProductId(cartItem.getProductId());
                dto.setThumbnailUrl(productEntity.getThumbnailUrl());
                dto.setNumber(cartItem.getNumber());
                dto.setMarketPrice(productEntity.getMarketPrice());
                dto.setRetailPrice(productEntity.getRetailPrice());
                shOrderGoodsEntities.add(dto);
                shCartService.removeById(cartItem.getId());
            }
        }else {
            BuyGoodsDTO goodsVo =  redisUtils.get(ShSysConstant.SHOP_CACHE_NAME+"_goods" + loginUser.getId(),BuyGoodsDTO.class);
            ShProductEntity productEntity = shProductService.getById(goodsVo.getProductId());
            ShGoodsEntity goodsEntity = shGoodsService.getById(goodsVo.getGoodsId());
            //判断库存
            if (productEntity.getStockNumber()<goodsVo.getNumber()){
                return R.error("库存不足");
            }
            ShOrderGoodsEntity dto = new ShOrderGoodsEntity();
            dto.setThumbnailUrl(productEntity.getThumbnailUrl());
            dto.setGoodsName(goodsEntity.getName());
            dto.setSpecValue(productEntity.getSpecValue());
            dto.setGoodsId(goodsVo.getGoodsId());
            dto.setProductId(goodsVo.getProductId());
            dto.setNumber(goodsVo.getNumber());
            dto.setMarketPrice(productEntity.getMarketPrice());
            dto.setRetailPrice(productEntity.getRetailPrice());
            shOrderGoodsEntities.add(dto);
            redisUtils.delete(ShSysConstant.SHOP_CACHE_NAME+"_goods" + loginUser.getId());
        }

        ShAddressEntity address = shAddressService.getById(submitDTO.getAddressId());

        ShOrderEntity order = createOrder(loginUser.getId());

        //收货地址和运费
        order.setConsignee(address.getUserName());
        order.setMobile(address.getTelNumber());
        order.setCountry(address.getNationalCode());
        order.setProvince(address.getProvinceName());
        order.setCity(address.getCityName());
        order.setArea(address.getCountyName());
        order.setAddress(address.getDetailInfo());
        order.setAppid(appid);

        //留言
        order.setPostscript(submitDTO.getPostscript());
        order.setAddTime(new Date());
        baseMapper.insert(order);
        if (BaseUtils.isEmpty(order.getId())) {
            return R.error("保存订单失败");
        }
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        //保存订单商品 并且计算商品金额
        for (ShOrderGoodsEntity dto:shOrderGoodsEntities){
            ShOrderGoodsEntity entity = new ShOrderGoodsEntity();
            entity.setNumber(dto.getNumber());
            entity.setProductId(dto.getProductId());
            entity.setGoodsId(dto.getGoodsId());
            entity.setSpecValue(dto.getSpecValue());
            entity.setGoodsName(dto.getGoodsName());
            entity.setOrderId(order.getId());
            entity.setThumbnailUrl(dto.getThumbnailUrl());
            entity.setRetailPrice(dto.getRetailPrice());
            entity.setMarketPrice(dto.getMarketPrice());
            entity.setAppid(appid);
            goodsTotalPrice = goodsTotalPrice.add(dto.getRetailPrice().multiply(new BigDecimal(dto.getNumber())));
            shOrderGoodsService.save(entity);
        }
        order.setGoodsPrice(goodsTotalPrice);

        //运费
        BigDecimal orderTotalPrice = goodsTotalPrice;
        //TODO免运费配置
//        ShConfigEntity config = shConfigService.findByAppid(appid);
//        if(goodsTotalPrice.compareTo(config.getFreeFreightPrice())>=0){
//            order.setFreight(BigDecimal.ZERO);
//        }else{
//            order.setFreight(config.getFreightPrice());
//            orderTotalPrice = goodsTotalPrice.add(config.getFreightPrice());
//        }
        order.setOrderPrice(orderTotalPrice);

        //使用的优惠券
        BigDecimal actualPrice = orderTotalPrice;
        if (BaseUtils.isNotEmpty(submitDTO.getCouponId())){
            order.setCouponId(submitDTO.getCouponId());
            ShCouponUserEntity couponUserEntity =shCouponUserService.getById(submitDTO.getCouponId());
            ShCouponEntity couponEntity = shCouponService.getById(couponUserEntity.getCouponId());
            order.setCouponId(couponUserEntity.getId());
            order.setCouponPrice(couponEntity.getCouponBalance());
            actualPrice = actualPrice.subtract(couponEntity.getCouponBalance());
            couponUserEntity.setCouponStatus(CouponStatue.USED.getValue());
            couponUserEntity.setOrderId(order.getId());
            shCouponUserService.updateById(couponUserEntity);
        }
        order.setActualPrice(actualPrice);
        order = this.changeOrderStatus(order, OrderStatus.SUBMIT.getValue(), OrderPayStatus.PENDING.getValue(),
                OrderShippingStatus.UN_SEND.getValue(), "新建待提交订单", loginUser.getUsername());
        baseMapper.updateById(order);
        //放置30分钟取消后到reids数据
        redisUtils.set(ShSysConstant.ORDER_PENDING+order.getOrderNumber(),order.getOrderNumber(),1800);

        return R.ok("订单提交成功").put("orderNumber",order.getOrderNumber());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R cancelOrder(ShOrderEntity order, String desc, String username, Long userId, String remoteHost) {


        if(order.getPayStatus().equals(OrderPayStatus.PAYED.getValue())){
            ShOrderReturnEntity orderReturn = shOrderReturnService.createOrderReturnAndSaveOrderReturnLog(ShOrderReturnType.MONEY.getValue(),username,order.getActualPrice(),"管理员关闭订单"
                    ,order,userId,"管理员关闭订单");
            shOrderReturnService.checkRefundCompelted(desc,orderReturn.getId(),username, remoteHost);
        }else {
            changeOrderStatus(order, OrderStatus.CANCEL.getValue(),OrderPayStatus.PENDING.getValue(), OrderShippingStatus.UN_SEND.getValue(),desc, username);
        }
        return R.ok("订单取消");
    }

    @Override
    public R cancelOrderByRedis(String orderNumber) {

        ShOrderEntity order = this.findByOrderNumber(orderNumber);
        if (BaseUtils.isNotEmpty(order)&&order.getPayStatus().equals(OrderPayStatus.PENDING.getValue())){
            changeOrderStatus(order, OrderStatus.CANCEL.getValue(),OrderPayStatus.PENDING.getValue(), OrderShippingStatus.UN_SEND.getValue(),"订单超过30分钟未支付", "redis");
            return R.ok("订单取消成功");
        }
        return R.ok("订单已支付");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R completeOrder(ShOrderEntity order, String username) {
        changeOrderStatus(order,OrderStatus.COMPLETE.getValue(),order.getPayStatus(),order.getShippingStatus(),"订单完成",username);
        order.setFinishTime(new Date());
        baseMapper.updateById(order);
        return R.ok("确认收货成功");
    }


    /**
     * 创建新订单
     * @param userId
     * @return
     */
    private ShOrderEntity createOrder(Long userId) {

        ShOrderEntity order = new ShOrderEntity();
        order.setOrderType(ShOrderType.DEF.getValue());
        order.setUserId(userId);
        order.setOrderNumber(ShOrderUtils.getOrderNumber(userId));
        return order;
    }

    /**
     * 改变订单状态
     * @param order
     * @param status
     * @param payStatus
     * @param shipmentStatus
     * @param content
     * @param userName
     * @return
     */
    @Override
    public ShOrderEntity changeOrderStatus(ShOrderEntity order, String status,
                                            String payStatus, String shipmentStatus, String content, String userName) {
        order.setUpdateTime(new Date());
        order.setOrderStatus(status);
        order.setPayStatus(payStatus);
        order.setShippingStatus(shipmentStatus);
        shOrderLogService.saveOrderLog(order.getId(),content,userName);
        baseMapper.updateById(order);
        return order;
    }

    @Override
    public BigDecimal sumTodayMoney(Map map) {
        return baseMapper.sumTodayMoney(map);
    }

    @Override
    public Integer countByUserIdAndStatusSql(Map map) {
        return baseMapper.countByUserIdAndStatusSql(map);
    }

    @Override
    public ShOrderEntity findByOrderNumber(String orderNumber) {
        return baseMapper.selectOne(new QueryWrapper<ShOrderEntity>().eq("order_number",orderNumber));
    }

    @Override
    public PageDTO<ShOrderEntity> findPageByUserId(Long page, Long size, Long userId, Integer status) {

        Page<ShOrderEntity> orderPage = new Page<>(page, size);
        Map map = new HashMap();
        map.put("userId",userId);
        if (status != 0){
            if (status == 1){
                map.put("order_status",OrderStatus.SUBMIT.getValue());
                map.put("pay_status",OrderPayStatus.PENDING.getValue());
                map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
            }else if(status == 2) {
                map.put("order_status",OrderStatus.SUBMIT.getValue());
                map.put("pay_status",OrderPayStatus.PAYED.getValue());
                map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
            }else if(status == 6) {
                map.put("order_status",OrderStatus.SUBMIT.getValue());
                map.put("pay_status",OrderPayStatus.PAYED.getValue());
                map.put("shipping_status",OrderShippingStatus.SEND.getValue());
            }else if(status == 8) {
                map.put("order_status",OrderStatus.COMPLETE.getValue());
                map.put("pay_status",OrderPayStatus.PAYED.getValue());
                map.put("shipping_status",OrderShippingStatus.SEND.getValue());
            }
        }
        orderPage.setRecords(this.baseMapper.findPageByUserId(orderPage, map));
        return new PageDTO<>(orderPage);
    }

    @Override
    public R sendOrder(ShOrderSendDTO sendDTO) {

        ShOrderEntity order = baseMapper.selectById(sendDTO.getOrderId());
        changeOrderStatus(order, OrderStatus.SUBMIT.getValue(),
                OrderPayStatus.PAYED.getValue(), OrderShippingStatus.SEND.getValue(), "管理员发货订单", "admin");
        order.setShippingNo(sendDTO.getShippingNo());
        order.setShippingTime(new Date());
        baseMapper.updateById(order);
        shMessageService.sendMsgSend(order);
        return R.ok();
    }
}
