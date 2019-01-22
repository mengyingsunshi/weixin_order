package com.ying.weixin_order.enums;

/**
 * @Author sunmengying
 * @Date 2018/12/18 17:21
 */
public enum ProductStatusEnum implements CodeEnum {
    UP(0,"上架"),
    DOWN(1,"下架");


    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
