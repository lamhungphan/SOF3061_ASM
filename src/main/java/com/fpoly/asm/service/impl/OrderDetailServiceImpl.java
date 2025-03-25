package com.fpoly.asm.service.impl;

import com.fpoly.asm.entity.OrderDetail;
import com.fpoly.asm.repository.OrderDetailRepository;
import com.fpoly.asm.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public List<OrderDetail> getByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}

