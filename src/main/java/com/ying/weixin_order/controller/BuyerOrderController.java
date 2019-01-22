package com.ying.weixin_order.controller;

import com.ying.weixin_order.VO.ResultVO;
import com.ying.weixin_order.dto.OrderDTO;
import com.ying.weixin_order.enums.ResultEnum;
import com.ying.weixin_order.exception.SellException;
import com.ying.weixin_order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author sunmengying
 * @Date 2019/1/17 19:00
 */
@RestController
@Slf4j
@RequestMapping(value = "/buyer/order")
public class BuyerOrderController {
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFor
    }
}
