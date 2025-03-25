package com.fpoly.asm.controller.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
    private Integer productId;

    private String name;

    private String description;

    private Integer quantity;

    private Float price;

    private Integer categoryId;
}
