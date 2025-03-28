package com.fpoly.asm.controller.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> extends PageResponseAbstract {
    private List<T> content;

    public PageResponse(Page<T> page) {
        super(page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
        this.content = page.getContent();
    }
}
