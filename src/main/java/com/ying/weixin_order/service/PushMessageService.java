package com.ying.weixin_order.service;

import com.ying.weixin_order.dto.OrderDTO;

/**
 * 推动消息
 * @Author sunmengying
 * @Date 2019/1/16 17:03
 */

public interface PushMessageService {
    /**
     * 订单状态变更信息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
