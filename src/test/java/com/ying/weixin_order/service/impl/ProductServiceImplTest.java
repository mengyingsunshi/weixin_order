package com.ying.weixin_order.service.impl;

import com.ying.weixin_order.entity.ProductInfo;
import com.ying.weixin_order.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author sunmengying
 * @Date 2018/12/18 16:52
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl service;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> list =  service.findOnUpper();
        Assert.assertNotEquals(0,list.size());
        System.out.println(list);
    }

    @Test
    public void findById() {
        ProductInfo productInfo = service.findById("111111111");
        Assert.assertNotNull(productInfo);
    }



    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> page = service.findAll(request);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getStatusCode());
        productInfo.setProductId("3333333333");
        productInfo.setProductName("意面");
        productInfo.setProductPrice(new BigDecimal("35.8"));
        productInfo.setProductIcon("url:..");
        productInfo.setProductDescription("很香");
        productInfo.setProductStock(39);
        productInfo.setCategoryType(30);

        ProductInfo result = service.save(productInfo);
        Assert.assertNotEquals(null,result);
    }
}