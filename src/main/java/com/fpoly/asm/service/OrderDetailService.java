package com.fpoly.asm.service;

import com.fpoly.asm.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetail> getAll();
    Optional<OrderDetail> getById(Integer id);
    List<OrderDetail> getByOrderId(Integer orderId);
    OrderDetail save(OrderDetail orderDetail);
    void delete(Integer id);
}
