package com.ying.weixin_order.convert;

import com.ying.weixin_order.dto.OrderDTO;
import com.ying.weixin_order.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author sunmengying
 * @Date 2019/1/14 16:25
 */
public class OrderMaster2OrderDTOConverter {


    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
