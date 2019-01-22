package com.ying.weixin_order.repository;

import com.ying.weixin_order.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void test1(){
        repository.findById(1);
    }

    @Test
    public void test2(){
        ProductCategory productCategory = new ProductCategory("简餐",10);
        repository.save(productCategory);
    }

    @Test
    @Transactional    //放在test里 完全的回滚  不管有没有异常
    public void test3(){
        ProductCategory productCategory = new ProductCategory("饮料",30);
        productCategory.setCategoryId(1);
        repository.save(productCategory);
    }

    @Test
    public void test4(){
        ProductCategory productCategory = repository.findById(1).orElse(null);
        productCategory.setCategoryName("西餐");
        repository.save(productCategory);

    }


    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(10,30);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}