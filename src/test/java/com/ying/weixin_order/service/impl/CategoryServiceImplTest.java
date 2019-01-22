package com.ying.weixin_order.service.impl;

import com.ying.weixin_order.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> typeList = Arrays.asList(10,30);
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(typeList);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("甜点",20);
        ProductCategory result = categoryService.save(category);
        Assert.assertNotEquals(null,result);
    }
}