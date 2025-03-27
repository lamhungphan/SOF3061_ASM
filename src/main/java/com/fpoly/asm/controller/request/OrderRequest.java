package com.fpoly.asm.controller.request;

import com.fpoly.asm.entity.Order;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer userId;
    private Double totalPrice;
    private Order.Status status;
}
