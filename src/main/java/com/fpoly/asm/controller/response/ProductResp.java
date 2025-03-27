package com.fpoly.asm.controller.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResp {
    private Integer id;
    private String name;
    private int quantity;
    private String size;
    private String description;
    private String image;
    private Float price;
    private LocalDate publishDate;
    private Integer categoryId;
}
