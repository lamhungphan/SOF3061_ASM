package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.OrderRequest;
import com.fpoly.asm.controller.response.OrderResponse;
import com.fpoly.asm.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<Order, Integer, OrderRequest> {
    public OrderResponse processPaymentAndCreateOrder(OrderRequest request);
    List<Order> getOrdersByUserId(Integer userId);
}
