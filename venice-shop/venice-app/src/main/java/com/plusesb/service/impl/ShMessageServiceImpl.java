package com.plusesb.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.plusesb.constant.ShSysConstant;
import com.plusesb.entity.*;
import com.plusesb.entity.enums.OrderPayInfoStatus;
import com.plusesb.entity.enums.OrderPayType;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("shMessageService")
public class ShMessageServiceImpl implements ShMessageService {
    @Autowired
    private ShUserService shUserService;
    @Autowired
    private ShOrderGoodsService shOrderGoodsService;
    @Autowired
    private ShOrderPayService shOrderPayService;
    @Autowired
    private ShWxService shWxService;
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void sendMsgRefund(ShOrderEntity order, ShOrderReturnEntity orderReturn) {
        try{
            if (BaseUtils.isNotEmpty(sysConfigService.getValue(ShSysConstant.REFUND_NOTICE))){
                ShOrderPayEntity shOrderPayEntity = shOrderPayService.findByOrderIdAndStatusAndPayType(order.getId(),
                        OrderPayInfoStatus.RETURN.getValue(), OrderPayType.WX.getValue());
                String prepay_id = shOrderPayEntity.getPrepayId();
                ShUserEntity userEntity = shUserService.getById(order.getUserId());

                WxMaTemplateMessage wxMaTemplateMessage= new WxMaTemplateMessage();
                wxMaTemplateMessage.setToUser(userEntity.getOpenid());
                wxMaTemplateMessage.setFormId(prepay_id);
                wxMaTemplateMessage.setPage("pages/ucenter/afterSalesDetail/afterSalesDetail?returnNumber="+orderReturn.getNumber());
                wxMaTemplateMessage.setTemplateId(sysConfigService.getValue(ShSysConstant.REFUND_NOTICE));

                WxMaTemplateData keyword1 = new WxMaTemplateData("keyword1",order.getOrderNumber());
                WxMaTemplateData keyword2 = new WxMaTemplateData("keyword2",order.getActualPrice().toString());
                WxMaTemplateData keyword3 = new WxMaTemplateData("keyword3",BaseUtils.getDate());
                WxMaTemplateData keyword4 = new WxMaTemplateData("keyword4","已退款");

                wxMaTemplateMessage.addData(keyword1);
                wxMaTemplateMessage.addData(keyword2);
                wxMaTemplateMessage.addData(keyword3);
                wxMaTemplateMessage.addData(keyword4);
                shWxService.getWxMaService().getMsgService().sendTemplateMsg(wxMaTemplateMessage);
                log.info("商家已退款通知发送成功");
            }else {
                log.info("商家没有配置已退款通知模版");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsgReturn(ShOrderEntity order, ShOrderReturnEntity orderReturn) {
        try{
            if (BaseUtils.isNotEmpty(sysConfigService.getValue(ShSysConstant.RETURN_NOTICE))){
                ShOrderPayEntity shOrderPayEntity = shOrderPayService.findByOrderIdAndStatusAndPayType(order.getId(), OrderPayInfoStatus.RETURN.getValue(), OrderPayType.WX.getValue());
                String prepay_id = shOrderPayEntity.getPrepayId();
                ShUserEntity userEntity = shUserService.getById(order.getUserId());
                List<ShOrderGoodsEntity> orderGoodsEntities = shOrderGoodsService.findByOrderId(order.getId());
                WxMaTemplateMessage wxMaTemplateMessage= new WxMaTemplateMessage();
                wxMaTemplateMessage.setToUser(userEntity.getOpenid());
                wxMaTemplateMessage.setFormId(prepay_id);
                wxMaTemplateMessage.setPage("pages/ucenter/afterSalesDetail/afterSalesDetail?returnNumber="+orderReturn.getNumber());
                wxMaTemplateMessage.setTemplateId(sysConfigService.getValue(ShSysConstant.RETURN_NOTICE));

                WxMaTemplateData keyword1 = new WxMaTemplateData("keyword1",order.getOrderNumber());
                WxMaTemplateData keyword2 = new WxMaTemplateData("keyword2",orderGoodsEntities.stream().map(ShOrderGoodsEntity::getGoodsName).collect(Collectors.joining(",")));
                WxMaTemplateData keyword3 = new WxMaTemplateData("keyword3",BaseUtils.getDate());
                WxMaTemplateData keyword4 = new WxMaTemplateData("keyword4","同意退货");

                wxMaTemplateMessage.addData(keyword1);
                wxMaTemplateMessage.addData(keyword2);
                wxMaTemplateMessage.addData(keyword3);
                wxMaTemplateMessage.addData(keyword4);
                shWxService.getWxMaService().getMsgService().sendTemplateMsg(wxMaTemplateMessage);
                log.info("商家同意退货通知发送成功");

            }else {
                log.info("商家没有配置同意退货通知模版");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsgSend(ShOrderEntity order) {
        try{
            if (BaseUtils.isNotEmpty(sysConfigService.getValue(ShSysConstant.SEND_NOTICE))){
                ShOrderPayEntity shOrderPayEntity = shOrderPayService.findByOrderIdAndStatusAndPayType(order.getId(),
                        OrderPayInfoStatus.PAYED.getValue(), OrderPayType.WX.getValue());
                String prepay_id = shOrderPayEntity.getPrepayId();
                ShUserEntity userEntity = shUserService.getById(order.getUserId());

                WxMaTemplateMessage wxMaTemplateMessage= new WxMaTemplateMessage();
                wxMaTemplateMessage.setToUser(userEntity.getOpenid());
                wxMaTemplateMessage.setFormId(prepay_id);
                wxMaTemplateMessage.setPage("pages/ucenter/orderDetail/orderDetail?orderNumber="+order.getOrderNumber());
                wxMaTemplateMessage.setTemplateId(sysConfigService.getValue(ShSysConstant.SEND_NOTICE));

                WxMaTemplateData keyword1 = new WxMaTemplateData("keyword1",order.getOrderNumber());
                WxMaTemplateData keyword2 = new WxMaTemplateData("keyword2",order.getShippingName());
                WxMaTemplateData keyword3 = new WxMaTemplateData("keyword3",order.getShippingNo());
                WxMaTemplateData keyword4 = new WxMaTemplateData("keyword4","同意退货");

                wxMaTemplateMessage.addData(keyword1);
                wxMaTemplateMessage.addData(keyword2);
                wxMaTemplateMessage.addData(keyword3);
                wxMaTemplateMessage.addData(keyword4);

                shWxService.getWxMaService().getMsgService().sendTemplateMsg(wxMaTemplateMessage);
                log.info("商家同意退货通知成功");
            }else {
                log.info("商家没有配置通知模版");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsgPayed(ShOrderEntity order) {
        try{
            if (BaseUtils.isNotEmpty(sysConfigService.getValue(ShSysConstant.PAYED_NOTICE))){

                ShOrderPayEntity shOrderPayEntity = shOrderPayService.findByOrderIdAndStatusAndPayType(order.getId(),
                        OrderPayInfoStatus.PAYED.getValue(), OrderPayType.WX.getValue());
                String prepay_id = shOrderPayEntity.getPrepayId();
                ShUserEntity userEntity = shUserService.getById(order.getUserId());
                List<ShOrderGoodsEntity> orderGoodsEntities = shOrderGoodsService.findByOrderId(order.getId());
                WxMaTemplateMessage wxMaTemplateMessage= new WxMaTemplateMessage();
                wxMaTemplateMessage.setToUser(userEntity.getOpenid());
                wxMaTemplateMessage.setFormId(prepay_id);
                wxMaTemplateMessage.setPage("pages/ucenter/orderDetail/orderDetail?orderNumber="+order.getOrderNumber());
                wxMaTemplateMessage.setTemplateId(sysConfigService.getValue(ShSysConstant.PAYED_NOTICE));

                WxMaTemplateData keyword1 = new WxMaTemplateData("keyword1",order.getOrderNumber());
                WxMaTemplateData keyword2 = new WxMaTemplateData("keyword2",orderGoodsEntities.stream().map(ShOrderGoodsEntity::getGoodsName).collect(Collectors.joining(",")));
                WxMaTemplateData keyword3 = new WxMaTemplateData("keyword3",BaseUtils.getDate());
                WxMaTemplateData keyword4 = new WxMaTemplateData("keyword4","同意退货");

                wxMaTemplateMessage.addData(keyword1);
                wxMaTemplateMessage.addData(keyword2);
                wxMaTemplateMessage.addData(keyword3);
                wxMaTemplateMessage.addData(keyword4);

                shWxService.getWxMaService().getMsgService().sendTemplateMsg(wxMaTemplateMessage);

                log.info("商家下单通知成功");
            }else {
                log.info("商家没有配置下单通知模版");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


