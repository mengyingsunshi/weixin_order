package com.ying.weixin_order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Author sunmengying
 * @Date 2019/1/9 17:21
 */

@Entity
public class OrderDetail {
    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;
    /** 商品单价  */
    private BigDecimal productPrice;
    /** 商品数量  */
    private Integer productQuantity;
    /** 商品小图 */
    private String productIcon;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }
}
