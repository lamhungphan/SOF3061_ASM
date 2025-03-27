package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.OrderRequest;
import com.fpoly.asm.controller.request.ProductCreationRequest;
import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.controller.request.ProductUpdateRequest;
import com.fpoly.asm.controller.response.ProductPageResponse;
import com.fpoly.asm.controller.response.ProductResp;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<Product, Integer, ProductRequest> {
//    ProductPageResponse getAll(String keyword, String sort, int page, int size);
//
//    ProductResponse getById(Integer id);
//
//    List<Product> getByCategory(Integer categoryId);
//
//    Product save(ProductCreationRequest request);
//
//    void update(ProductUpdateRequest request);
//
//    void delete(Integer id);
}
