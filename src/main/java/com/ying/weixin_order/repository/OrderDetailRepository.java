package com.ying.weixin_order.repository;

import com.ying.weixin_order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author sunmengying
 * @Date 2019/1/10 14:40
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String orderId);
}
