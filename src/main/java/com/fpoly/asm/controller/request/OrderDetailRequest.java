package com.fpoly.asm.controller.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double price;
}
