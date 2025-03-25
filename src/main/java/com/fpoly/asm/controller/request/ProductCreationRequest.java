package com.fpoly.asm.controller.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreationRequest {

    private String name;

    private String description;

    private Integer quantity;

    private Float price;

    private Integer categoryId;
}
