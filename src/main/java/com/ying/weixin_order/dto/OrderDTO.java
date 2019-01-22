package com.ying.weixin_order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ying.weixin_order.entity.OrderDetail;
import com.ying.weixin_order.enums.OrderStatusEnum;
import com.ying.weixin_order.enums.PayStatusEnum;
import com.ying.weixin_order.utils.EnumUtil;
import com.ying.weixin_order.utils.serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author sunmengying
 * @Date 2019/1/9 16:04
 */
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;
    /** 订单总金额  */
    private BigDecimal orderAmount;
    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;
    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private  Date updateTime;

    List<OrderDetail> orderDetailList;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return  EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
