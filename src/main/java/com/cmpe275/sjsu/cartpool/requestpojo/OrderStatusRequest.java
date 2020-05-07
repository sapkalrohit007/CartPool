package com.cmpe275.sjsu.cartpool.requestpojo;

import com.cmpe275.sjsu.cartpool.model.OrderStatus;

public class OrderStatusRequest
{
    Integer id;
    OrderStatus orderStatus;

    public OrderStatusRequest() {

    }

    public OrderStatusRequest(Integer id, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
