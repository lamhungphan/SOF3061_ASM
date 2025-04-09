package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService extends BaseService<Product, Integer, ProductRequest> {
    Page<Product> getProductByCategoryId(Integer categoryId, String sort, int page, int size);
    Page<Product> getProductByKeyword(String keyword, String sort, int page, int size);
}
