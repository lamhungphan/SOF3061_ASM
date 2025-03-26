package com.fpoly.asm.controller.response;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private int id;

    private String name;

    private String description;

    private String image;

    private Float price;

    private String size;

    private int quantity;

    private LocalDate publishDate;

    private LocalDateTime lastUpdateTime;

    private Integer categoryId;
}
