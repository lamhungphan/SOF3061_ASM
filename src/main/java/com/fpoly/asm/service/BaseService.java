package com.fpoly.asm.service;

import org.springframework.data.domain.Page;

public interface BaseService<T, ID> {
    Page<T> getAll(String keyword, String sort, int page, int size);

    T getById(ID id);

    T save(T request);

    void update(T request);

    void delete(ID id);
}
