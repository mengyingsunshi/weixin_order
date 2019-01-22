package com.ying.weixin_order.repository;

import com.ying.weixin_order.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void test1(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStatus(0);
        productInfo.setProductId("111111111");
        productInfo.setProductName("奶油蛋糕");
        productInfo.setProductPrice(new BigDecimal("13.5"));
        productInfo.setProductIcon("url:..");
        productInfo.setProductDescription("很好吃");
        productInfo.setProductStock(15);
        productInfo.setCategoryType(20);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,list.size());

    }


}