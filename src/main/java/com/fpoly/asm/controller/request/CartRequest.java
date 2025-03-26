package com.fpoly.asm.controller.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Integer userId;
    private Integer productId;
    private int quantity;
}
