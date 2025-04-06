package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.OrderDetailRequest;
import com.fpoly.asm.entity.OrderDetail;

public interface OrderDetailService extends BaseService<OrderDetail, Integer, OrderDetailRequest> {
    OrderDetail createOrderDetail(OrderDetailRequest request);
}
