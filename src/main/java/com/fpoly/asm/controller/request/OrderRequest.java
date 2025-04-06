package com.fpoly.asm.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    private Integer userId;

    private List<OrderDetailRequest> orderDetails;
}
