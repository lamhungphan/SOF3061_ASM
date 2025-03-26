package com.fpoly.asm.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Integer id;
    private AccountResponse user;
    private ProductResponse product;
    private int quantity;
}
