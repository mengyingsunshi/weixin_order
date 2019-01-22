package com.ying.weixin_order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author sunmengying
 * @Date 2019/1/17 19:29
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;
    @NotEmpty(message = "手机号必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid必填")
    private String openid;
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
