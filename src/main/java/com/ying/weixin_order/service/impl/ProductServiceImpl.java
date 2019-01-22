package com.ying.weixin_order.service.impl;

import com.ying.weixin_order.dto.CartDTO;
import com.ying.weixin_order.entity.ProductInfo;
import com.ying.weixin_order.enums.ProductStatusEnum;
import com.ying.weixin_order.enums.ResultEnum;
import com.ying.weixin_order.exception.SellException;
import com.ying.weixin_order.repository.ProductInfoRepository;
import com.ying.weixin_order.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author sunmengying
 * @Date 2018/12/18 16:50
 */

@Service
public class ProductServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;


    @Override
    public ProductInfo findById(String productId) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        Example<ProductInfo> example = Example.of(productInfo);

        return repository.findOne(example).orElse(null);

    }

    @Override
    public List<ProductInfo> findOnUpper() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElseThrow(() ->
                    new SellException(ResultEnum.PRODUCT_NOT_EXIST));

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElseThrow(() ->
            new SellException(ResultEnum.PRODUCT_NOT_EXIST));

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElseThrow(()->
                new SellException(ResultEnum.PRODUCT_NOT_EXIST));
        if(productInfo.getStatusEnum().equals(ProductStatusEnum.UP)){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return  repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElseThrow(()->
                new SellException(ResultEnum.PRODUCT_NOT_EXIST));
        if(productInfo.getStatusEnum().equals(ProductStatusEnum.DOWN)){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return  repository.save(productInfo);

    }
}
