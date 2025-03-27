package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.OrderDetailRequest;
import com.fpoly.asm.entity.OrderDetail;
import com.fpoly.asm.repository.OrderDetailRepository;
import com.fpoly.asm.repository.OrderRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.OrderDetailService;

import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends AbstractService<OrderDetail, Integer, OrderDetailRequest> implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository) {
        super(orderDetailRepository);
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void update(OrderDetailRequest request) {
        OrderDetail orderDetail = orderDetailRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Detail not found"));

        orderDetail.setQuantity(request.getQuantity());
        orderDetail.setPrice(request.getPrice());

        orderDetailRepository.save(orderDetail);
    }
}
