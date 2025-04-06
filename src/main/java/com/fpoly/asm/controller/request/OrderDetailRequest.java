package com.fpoly.asm.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {

    private Integer orderId;

    @NotNull
    private Integer productId;

    @Positive
    private Integer quantity;

    @Positive
    private Double price;
}
