package com.plusesb.utils;

import com.plusesb.entity.ShConfigEntity;
import com.plusesb.entity.ShOrderEntity;
import com.plusesb.entity.enums.OrderPayStatus;
import com.plusesb.entity.enums.OrderShippingStatus;
import com.plusesb.entity.enums.OrderSingleStatus;
import com.plusesb.entity.enums.OrderStatus;
import com.plusesb.exception.RRException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * Created by linyuchi on 2017/6/12.
 */
public class ShOrderUtils {
    /**
     * 订单号,规则(年月日时分秒+用户ID后4位)=16位   年两位如17,用户id4位补0,如0899
     * @param userId 用户ID
     * @return
     */
    public static String getOrderNumber(Long userId ){
        String strDate = BaseUtils.getDateString(new Date(),new SimpleDateFormat("yyMMddHHmmss"));
        String orderId = getSubOrderId(userId.toString());
        return  strDate+orderId;
    }


    private static String getSubOrderId(String orderId) {
        if(orderId.length()<4){
            orderId = org.apache.commons.lang3.StringUtils.leftPad(orderId,4,"0");
        }else{
            orderId = orderId.substring(orderId.length()-4);
        }
        return orderId;
    }

    /**
     * 退款退货订单号,规则(年月日时分秒+用户ID后4位)=16位   年两位如17,用户id4位补0,如0899
     * @param userId 用户ID
     * @return
     */
    public static String getOrderReturnNumber(Long userId ){
        String strDate = BaseUtils.getDateString(new Date(),new SimpleDateFormat("yyMMddHHmmss"));
        String orderId = getSubOrderId(userId.toString());
        return  "OR"+strDate+orderId;
    }
    /**
     * 优惠券编码,规则(年月日时分秒+用户ID后4位)=16位   年两位如17,用户id4位补0,如0899
     * @param userId 用户ID
     * @return
     */
    public static String getCouponNumber(Long userId) {
        String strDate = BaseUtils.getDateString(new Date(),new SimpleDateFormat("yyMMddHHmmss"));
        String orderId = getSubOrderId(userId.toString());
        return  "CO"+strDate+orderId;
    }
    /**
     * @param cartIds 页面传入参数
     * @return id的数组
     */
    public static Long[] getIds(String cartIds){
        Long[] ids = null;
        if(cartIds.indexOf(";")!=-1){
            String[] strIds = cartIds.split(";");
            ids = new Long[strIds.length];
            for (int i = 0; i < strIds.length; i++) {
                ids[i]=Long.parseLong(strIds[i]);
            }
        }else{
            ids = new Long[]{Long.parseLong(cartIds)};
        }
        return ids;
    }

    /**
     * 支付订单的订单号为订单编号+A+4位数的随机数
     * 例如  单号 ：1706151733150015  支付单号：1706151733150015A1023
     * @param orderNumber
     * @return
     */
    public static String getPayNumberByOrderNumber(String orderNumber){
        Random random = new Random();
        int s = random.nextInt(9000) + 1000;
        return  orderNumber+"A"+s;
    }

    /**
     * 通过支付订单ID获得截取订单ID
     * @param orderNumber 订单ID
     * @return
     */
    public static String subOrderNumber(String orderNumber){
        return orderNumber.substring(0,orderNumber.length()-5);
    }


    /**
     *  1--（待付款）已提交待支付 2--（已取消）已取消 3--（待发货）已提交已支付待发货
     *  4--（已退款）已退款  5--（部分发货）已提交已支付部分发货 6--（待收货）已提交已支付全部发货  7--(已退货)已提交已支付已退货 8--（已完成）已完成
     * @param status
     * @param payStatus
     * @param shipmentStatus
     */
    public static Integer getSingleOrderStatus(String status, String payStatus, String shipmentStatus) {
        //8已完成
        if (status.equals(OrderStatus.COMPLETE.getValue())){
            return OrderSingleStatus.COMPLETE.getCode();
        }//7已退货
        else if (shipmentStatus.equals(OrderShippingStatus.RETURN.getValue())) {
            return OrderSingleStatus.RETURN.getCode();
        }//6待收货
        else if (shipmentStatus.equals(OrderShippingStatus.SEND.getValue())&&status.equals(OrderStatus.SUBMIT.getValue())) {
            return OrderSingleStatus.SEND.getCode();
        }//5部分发货
        else if (shipmentStatus.equals(OrderShippingStatus.PART_SEND.getValue())&&status.equals(OrderStatus.SUBMIT.getValue())) {
            return OrderSingleStatus.PART_SEND.getCode();
        }//4已退款
        else if (payStatus.equals(OrderPayStatus.REFUND.getValue())) {
            return OrderSingleStatus.REFUND.getCode();
        }//3已支付
        else if (status.equals(OrderStatus.SUBMIT.getValue())&&payStatus.equals(OrderPayStatus.PAYED.getValue())&&shipmentStatus.equals(OrderShippingStatus.UN_SEND.getValue())) {
            return OrderSingleStatus.PAYED.getCode();
        }//2已取消
        else if (status.equals(OrderStatus.CANCEL.getValue())) {
            return OrderSingleStatus.CANCEL.getCode();
        }//1待付款
        else if (status.equals(OrderStatus.SUBMIT.getValue())&&payStatus.equals(OrderPayStatus.PENDING.getValue())){
            return OrderSingleStatus.PENDING.getCode();
        }
        return 0;
    }

    /**
     * @param order
     * @param status  UNSUBMIT=未提交 SUBMIT=已提交 COMPLETE=已完成 CANCEL= 已关闭
     * @param payStatus PENDING=待支付 PAYED=已支付 REFUND=已退款
     * @param shipmentStatus UNAPPROVAL("UNAPPROVAL"), //APPROVAL("101"), //101核保UNDERWRITE("102"), //102承保CANCEL("201"), //撤单/* REFUSE_RETURN("301"), //301拒保退费 REFUND_RETURN("302"), //退保退费
     * @param userId 用户
     * 校验订单状态
     */
    public static Boolean checkBuyUserAndStatus(ShOrderEntity order, String status, String payStatus,
                                                String shipmentStatus, Long userId) {
        if(!order.getUserId().equals(userId)){
            return false;
        }
        if(BaseUtils.isNotEmpty(status)&&!order.getOrderStatus().equals(status)){
            return false;
        }
        if(BaseUtils.isNotEmpty(payStatus)&&!order.getPayStatus().equals(payStatus)){
            return false;
        }
        if(BaseUtils.isNotEmpty(shipmentStatus)&&!order.getShippingStatus().equals(shipmentStatus)){
            return false;
        }
        return true;
    }

    public static BigDecimal scaleRandDownTotalPrice(BigDecimal totalPrice) {
        BigDecimal payTotal  = totalPrice.setScale(2,BigDecimal.ROUND_DOWN);
        return payTotal;
    }

    /**
     * 拆分数组
     * @param counts
     * @return
     */
    public static Integer[] getCounts(String counts) {
        Integer[] count = null;
        if(counts.indexOf(";")!=-1){
            String[] strIds = counts.split(";");
            count = new Integer[strIds.length];
            for (int i = 0; i < strIds.length; i++) {
                if (Integer.parseInt((strIds[i]))==0) {
                    throw  new RRException("访问异常");
                }
                count[i]=Integer.parseInt((strIds[i]));
            }
        }else{
            if (Integer.parseInt(counts)==0) {
                throw  new RRException("访问异常");
            }
            count = new Integer[]{Integer.parseInt((counts))};
        }
        return count;
    }

    /**
     * 校验订单已经付款
     * @param order
     * @param loginUserId
     */
    public static Boolean checkStatusPayed(ShOrderEntity order, Long loginUserId) {
        return  checkBuyUserAndStatus(order, OrderStatus.SUBMIT.getValue(), OrderPayStatus.PAYED.getValue(), OrderShippingStatus.UN_SEND.getValue(), loginUserId);
    }

    /**
     * 校验订单是否为已退款
     * @param order
     * @param loginUserId
     */
    public static void checkStatusReturn(ShOrderEntity order, Long loginUserId) {

    }



    /**
     * 校验订单购买用户
     * @param order
     * @param loginUserId
     */
    public static Boolean checkBuyUser(ShOrderEntity order, Long loginUserId) {
        return checkBuyUserAndStatus(order, null,null,null, loginUserId);
    }

    /**
     * 校验订单已发送
     * @param order
     * @param loginUserId
     */
    public static Boolean checkStatusSend(ShOrderEntity order, Long loginUserId) {
        return checkBuyUserAndStatus(order, OrderStatus.SUBMIT.getValue(), OrderPayStatus.PAYED.getValue(), OrderShippingStatus.SEND.getValue(), loginUserId);
    }

    /**
     * 校验订单待支付
     * @param order
     * @param loginUserId
     */
    public static Boolean checkStatusPending(ShOrderEntity order, Long loginUserId) {
        return checkBuyUserAndStatus(order, OrderStatus.SUBMIT.getValue(), OrderPayStatus.PENDING.getValue(), OrderShippingStatus.UN_SEND.getValue(), loginUserId);
    }

    /**
     * 校验订单已退款
     * @param order
     * @param loginUserId
     */
    public static void checkStautsRefund(ShOrderEntity order, Long loginUserId) {

    }

    /**
     * 校验订单待提交
     * @param order
     * @param loginUserId
     */
    public static Boolean checkStatusUnsubmit(ShOrderEntity order, Long loginUserId) {
        return checkBuyUserAndStatus(order,  OrderStatus.UNSUBMIT.getValue(), OrderPayStatus.PENDING.getValue(), OrderShippingStatus.UN_SEND.getValue(), loginUserId);
    }

    public static Map getHandleOption(ShOrderEntity orderInfo) {

            Map handleOption = new HashMap();
            //取消操作
            handleOption.put("cancel", false);
            //删除操作
            handleOption.put("delete", false);
            //支付操作
            handleOption.put("pay", false);
            //退款操作
            handleOption.put("refund",false);
            //评论操作
            handleOption.put("comment", false);
            //确认收货操作
            handleOption.put("delivery", false);
            //完成订单操作
            handleOption.put("confirm", false);
            //退换货操作
            handleOption.put("return", false);
            //再次购买
            handleOption.put("buy", false);

//         *  1--（待付款）已提交待支付 2--（已取消）已取消 3--（待发货）已提交已支付待发货
//         *  4--（已退款）已退款  5--（部分发货）已提交已支付部分发货 6--（待收货）已提交已支付全部发货  7--(已退货)已提交已支付已退货 8--（已完成）已完成

            //如果订单已经取消或是已完成，则可删除和再次购买
            int status = ShOrderUtils.getSingleOrderStatus(orderInfo.getOrderStatus(),orderInfo.getPayStatus(),orderInfo.getShippingStatus());
            if (status == 2 ) {
//            handleOption.put("delete", true);
                handleOption.put("buy", true);
            }

            //如果订单没有被取消，且没有支付，则可支付，可取消
            if (status == 1) {
//                handleOption.put("cancel", true);
                handleOption.put("pay", true);
            }

            //如果订单已付款，没有发货，则可退款操作
            if (status == 3) {
                handleOption.put("refund", true);
            }

            //如果订单已经发货，没有收货，则可收货操作和退款、退货操作
            if (status == 6) {
                handleOption.put("confirm", true);
//                handleOption.put("return", true);
            }

            //如果订单已经支付，且已经收货，则可完成交易、评论和再次购买
            if (status == 8) {
                handleOption.put("comment", true);
//                handleOption.put("buy", true);
            }
            return handleOption;
    }

    /**
     * 通过 singleOrderStatus 分解参数
     *  1--（待付款）已提交待支付 2--（已取消）已取消 3--（待发货）已提交已支付待发货
     *  4--（已退款）已退款  5--（部分发货）已提交已支付部分发货 6--（待收货）已提交已支付全部发货  7--(已退货)已提交已支付已退货 8--（已完成）已完成
     * @param map
     * @param singleOrderStatus
     * @return
     */
    public static Map getStatusBySingleOrderStatus(Map map, int singleOrderStatus) {


        if (singleOrderStatus == OrderSingleStatus.COMPLETE.getCode()){
            map.put("order_status",OrderStatus.COMPLETE.getValue());
        }else if(singleOrderStatus == OrderSingleStatus.RETURN.getCode()){
            map.put("shipping_status",OrderStatus.COMPLETE.getValue());
        }else if(singleOrderStatus == OrderSingleStatus.SEND.getCode() ){
            map.put("shipping_status",OrderShippingStatus.SEND.getValue());
            map.put("order_status",OrderStatus.SUBMIT.getValue());
        }else if(singleOrderStatus == OrderSingleStatus.REFUND.getCode() ){
            map.put("pay_status",OrderPayStatus.REFUND.getValue());
        }else if(singleOrderStatus == OrderSingleStatus.PAYED.getCode() ){
            map.put("shipping_status",OrderShippingStatus.UN_SEND.getValue());
            map.put("order_status",OrderStatus.SUBMIT.getValue());
            map.put("pay_status",OrderPayStatus.PAYED.getValue());

        }else if(singleOrderStatus == OrderSingleStatus.CANCEL.getCode() ){
            map.put("order_status",OrderStatus.CANCEL.getValue());
        }else if(singleOrderStatus == OrderSingleStatus.PENDING.getCode() ){
            map.put("order_status",OrderStatus.SUBMIT.getValue());
            map.put("pay_status",OrderPayStatus.PENDING.getValue());
        }
        return  map;
    }
}
