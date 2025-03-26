package com.fpoly.asm.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Integer id;
    private OrderResponse order;
    private ProductResponse product;
    private Integer quantity;
    private Double price;
}
