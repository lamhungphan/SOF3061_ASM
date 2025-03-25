package com.fpoly.asm.controller.response;

import lombok.*;

import java.sql.Date;

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

    private Date publishDate;

    private Date lastUpdateTime;

    private Integer categoryId;
}
