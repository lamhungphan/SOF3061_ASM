package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.repository.CategoryRepository;
import com.fpoly.asm.repository.ProductRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "Product-Service")
public class ProductServiceImpl extends AbstractService<Product, Integer, ProductRequest> implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void update(ProductRequest request) {
    }
}
