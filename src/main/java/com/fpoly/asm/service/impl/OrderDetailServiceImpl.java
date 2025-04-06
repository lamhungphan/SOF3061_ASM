package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.OrderDetailRequest;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.entity.OrderDetail;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.repository.OrderDetailRepository;
import com.fpoly.asm.repository.OrderRepository;
import com.fpoly.asm.repository.ProductRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.OrderDetailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderDetailServiceImpl extends AbstractService<OrderDetail, Integer, OrderDetailRequest> implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        super(orderDetailRepository);
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void update(OrderDetailRequest request) {
    }

    @Transactional
    @Override
    public OrderDetail createOrderDetail(OrderDetailRequest request) {
        log.info("Creating OrderDetail with orderId: {}", request.getOrderId()); // Kiểm tra orderId

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.getOrderId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order); // Gán Order trực tiếp
        orderDetail.setProduct(product);
        orderDetail.setQuantity(request.getQuantity());
        orderDetail.setPrice(request.getPrice());

        return orderDetailRepository.save(orderDetail);
    }

}
