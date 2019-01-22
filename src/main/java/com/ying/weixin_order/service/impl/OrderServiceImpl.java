package com.ying.weixin_order.service.impl;

import com.ying.weixin_order.convert.OrderMaster2OrderDTOConverter;
import com.ying.weixin_order.dto.CartDTO;
import com.ying.weixin_order.dto.OrderDTO;
import com.ying.weixin_order.entity.OrderDetail;
import com.ying.weixin_order.entity.OrderMaster;
import com.ying.weixin_order.entity.ProductInfo;
import com.ying.weixin_order.enums.OrderStatusEnum;
import com.ying.weixin_order.enums.PayStatusEnum;
import com.ying.weixin_order.enums.ResultEnum;
import com.ying.weixin_order.exception.SellException;
import com.ying.weixin_order.repository.OrderDetailRepository;
import com.ying.weixin_order.repository.OrderMasterRepository;
import com.ying.weixin_order.service.*;
import com.ying.weixin_order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author sunmengying
 * @Date 2019/1/9 18:59
 */

@Service
@Slf4j

public class OrderServiceImpl implements OrderService {

    //private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);使用slf4j注解

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private PayService payService;
    @Autowired
    private PushMessageService pushMessageService;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //创建订单
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        //发送websocket信息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId)
                .orElseThrow(() -> new SellException(ResultEnum.ORDER_NOT_EXIST));
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单]订单状态不正确，orderId={},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster );
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if(orderMaster == null){
            log.error("[取消订单]更新失败，orderMaster={}",orderMaster);
        }
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单中无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //退款----------------------------
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refund(orderDTO);
        }
        return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster );
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(orderMaster == null){
            log.error("[完结订单]更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板信息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付完成】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】支付状态不正确，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster );
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【订单支付完成】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterpage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterpage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterpage.getTotalElements());
    }
}
