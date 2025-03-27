package com.fpoly.asm.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
