package com.ying.weixin_order.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.ying.weixin_order.dto.OrderDTO;

/**
 * @Author sunmengying
 * @Date 2019/1/14 17:07
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyDate);

    RefundResponse refund(OrderDTO orderDTO);

}
