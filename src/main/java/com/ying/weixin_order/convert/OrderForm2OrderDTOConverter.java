package com.ying.weixin_order.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ying.weixin_order.dto.OrderDTO;
import com.ying.weixin_order.entity.OrderDetail;
import com.ying.weixin_order.form.OrderForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author sunmengying
 * @Date 2019/1/21 14:16
 */
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();

        orderDetailList = gson.fromJson(orderForm.getItems(),
                new TypeToken<List<OrderDetail>>() {}.getType());  //匿名内部类
        HashMap

    }
}
