package com.ying.weixin_order.service.impl;

import com.ying.weixin_order.entity.ProductCategory;
import com.ying.weixin_order.repository.ProductCategoryRepository;
import com.ying.weixin_order.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;
    @Override
    public ProductCategory findOne(Integer categoryId) {

        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        return repository.save(category);
    }
}
