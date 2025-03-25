package com.fpoly.asm.controller.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponse extends PageResponseAbstract implements Serializable {
    public List<ProductResponse> products;
}
