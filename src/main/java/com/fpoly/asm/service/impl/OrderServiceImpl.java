package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.OrderRequest;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.mapper.OrderMapper;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractService<Order, Integer, OrderRequest> implements OrderService {
    private final OrderMapper orderMapper;

    public OrderServiceImpl(JpaRepository<Order, Integer> repository, OrderMapper orderMapper) {
        super(repository);
        this.orderMapper = orderMapper;
    }

    @Override
    public void update(OrderRequest request) {

    }
}
