package com.fpoly.asm.service.impl;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.repository.OrderRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends AbstractService<Order, Integer> implements OrderService {

    public OrderServiceImpl(JpaRepository<Order, Integer> repository) {
        super(repository);
    }

    @Override
    public void update(Order request) {

    }
}
