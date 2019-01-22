package com.ying.weixin_order.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.ying.weixin_order.dto.OrderDTO;
import com.ying.weixin_order.enums.ResultEnum;
import com.ying.weixin_order.exception.SellException;
import com.ying.weixin_order.service.OrderService;
import com.ying.weixin_order.service.PayService;
import com.ying.weixin_order.utils.JsonUtil;
import com.ying.weixin_order.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author sunmengying
 * @Date 2019/1/14 17:22
 */

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐订单";
    @Autowired // (@Bean注释产生的)
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付]发起支付，request={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付]发起支付，response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyDate) {

        //1.验证签名
        //2.支付的状态
        //3.支付金额
        //4.支付人（下单人==支付人）
        PayResponse payResponse = bestPayService.asyncNotify(notifyDate);
        log.info("[微信支付]异步通知，payResponse={}",JsonUtil.toJson(payResponse));

        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        if(orderDTO==null){
            log.error("[微信支付]异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())){
            log.error("[微信支付]异步通知，订单金额不一致，orderId={},微信通知金额={},系统金额={}"
                    ,payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        orderService.paid(orderDTO);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款]request={}",JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款]response={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
