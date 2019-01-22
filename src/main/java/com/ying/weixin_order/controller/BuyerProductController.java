package com.ying.weixin_order.controller;

import com.ying.weixin_order.VO.ProductInfoVO;
import com.ying.weixin_order.VO.ProductVO;
import com.ying.weixin_order.VO.ResultVO;
import com.ying.weixin_order.entity.ProductCategory;
import com.ying.weixin_order.entity.ProductInfo;
import com.ying.weixin_order.service.CategoryService;
import com.ying.weixin_order.service.ProductInfoService;
import com.ying.weixin_order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author sunmengying
 * @Date 2018/12/18 18:12
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResultVO list(){
        List<ProductInfo> productInfos = productInfoService.findOnUpper();
//        List<Integer> categorys = new ArrayList<>();
//        for(ProductInfo productInfo : productInfos){
//            categorys.add(productInfo.getCategoryType());
//        }

        List<Integer> categoryTypeList = productInfos.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


        //数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfos){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
