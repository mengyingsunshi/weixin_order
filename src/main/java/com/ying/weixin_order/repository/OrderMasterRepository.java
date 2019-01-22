package com.ying.weixin_order.repository;

import com.ying.weixin_order.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author sunmengying
 * @Date 2019/1/9 14:44
 */
public interface OrderMasterRepository extends JpaRepository <OrderMaster,String>{
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}

