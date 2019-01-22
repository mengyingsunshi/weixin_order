package com.ying.weixin_order.repository;

import com.ying.weixin_order.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer status);
}
