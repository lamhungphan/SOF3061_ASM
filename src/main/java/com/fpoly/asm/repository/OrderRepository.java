package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Order;
import com.fpoly.asm.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(Integer userId);
    List<Order> findByUser_IdOrderByOrderDateDesc(Integer userId);
    List<Order> findByUser_IdAndStatus(Integer userId, OrderStatus status);
}
