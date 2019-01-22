package com.ying.weixin_order.service;

import com.ying.weixin_order.dto.CartDTO;
import com.ying.weixin_order.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author sunmengying
 * @Date 2018/12/18 16:49
 * 商品
 */


public interface ProductInfoService {

    ProductInfo findById(String productId);

    /**
     * 查询所有上架商品
     * @param
     * @return
     */
    List<ProductInfo> findOnUpper();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
    /** 加库存  */
    void increaseStock(List<CartDTO> cartDTOList);
    /** 减库存  */
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);


}
