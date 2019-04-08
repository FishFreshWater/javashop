package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.entity.enums.OrderPayInfoStatus;
import com.plusesb.entity.enums.OrderPayType;
import com.plusesb.service.ShOrderService;
import com.plusesb.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShOrderPayEntity;
import com.plusesb.service.ShOrderPayService;
import com.plusesb.mapper.ShOrderPayMapper;

import java.math.BigDecimal;
import java.util.List;


@Service("shOrderPayService")
public class ShOrderPayServiceImpl extends BaseServiceImpl<ShOrderPayMapper, ShOrderPayEntity> implements ShOrderPayService {

    @Autowired
    private ShOrderService shOrderService;

    @Override
    public ShOrderPayEntity findByPayNumber(String payNumber) {
        return  baseMapper.selectOne(new QueryWrapper<ShOrderPayEntity>().eq("pay_number",payNumber));
    }

    @Override
    public void saveOrderPayInfo(String prepay_id, BigDecimal paymoney, Integer status, String payNumber, Long orderId) {

        ShOrderPayEntity orderPayInfo = this.findByOrderIdAndStatusAndPayType(orderId, OrderPayInfoStatus.UNPAY.getValue(),OrderPayType.WX.getValue());
        if (BaseUtils.isEmpty(orderPayInfo)) {
            orderPayInfo = new ShOrderPayEntity();
            orderPayInfo.setOrderId(orderId);
        }
        orderPayInfo.setPrepayId(prepay_id);
        orderPayInfo.setPayType(OrderPayType.WX.getValue());
        orderPayInfo.setPayBalance(paymoney);
        orderPayInfo.setStatus(status);
        orderPayInfo.setPayNumber(payNumber);
        this.saveOrUpdate(orderPayInfo);
    }

    @Override
    public List<ShOrderPayEntity> findByOrderIdAndStatus(Long id, Integer value) {
        return baseMapper.selectList(new QueryWrapper<ShOrderPayEntity>()
                .eq("order_id",id).eq("status",value));
    }

    @Override
    public ShOrderPayEntity findByOrderIdAndStatusAndPayType(Long orderId, Integer status, String payType) {
        return baseMapper.selectOne(new QueryWrapper<ShOrderPayEntity>()
                .eq("order_id",orderId).eq("status",status).eq("pay_type",payType));
    }

    @Override
    public List<ShOrderPayEntity> findByOrderId(Long id) {
        return baseMapper.selectList(new QueryWrapper<ShOrderPayEntity>()
                .eq("order_id",id).orderByDesc("create_time"));
    }
}
