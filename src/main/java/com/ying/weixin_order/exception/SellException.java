package com.ying.weixin_order.exception;

import com.ying.weixin_order.enums.ResultEnum;

/**
 * @Author sunmengying
 * @Date 2019/1/10 14:30
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
