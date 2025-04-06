package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Order;
import com.fpoly.asm.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrder_Id(Integer orderId);
    List<OrderDetail> findByProduct_Id(Integer productId);
}
