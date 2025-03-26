package com.fpoly.asm.controller.response;

import com.fpoly.asm.entity.Order;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private AccountResponse user;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private Order.Status status;
}
