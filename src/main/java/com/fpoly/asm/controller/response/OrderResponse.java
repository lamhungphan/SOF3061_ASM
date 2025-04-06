package com.fpoly.asm.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Integer id;
    private AccountResponse user;
    private Double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderDetailResponse> orderDetails;
}
