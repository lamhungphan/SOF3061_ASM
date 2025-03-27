package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.mapper.ProductMapper;
import com.fpoly.asm.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
@Tag(name = "Product Controller")
@Slf4j(topic = "PRODUCT-CONTROLLER")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Get product list", description = "API retrieve product from database")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.getAll(keyword, sort, page, size);
        Page<ProductResponse> respPage = products.map(productMapper::toProductResponse);
        return ResponseEntity.ok(respPage);
    }

    @Operation(summary = "Get product detail", description = "API retrieve product detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable Integer id) {
        log.info("Get user detail by ID: {}", id);
        Product product = productService.getById(id);
        return ResponseEntity.ok(productMapper.toProductResponse(product));
    }

    @Operation(summary = "Create product", description = "API add new product to database")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Create product: {}", request);
        Product product = productMapper.toProduct(request);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(productMapper.toProductResponse(savedProduct));
    }

    @Operation(summary = "Update product", description = "API update product to database")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        log.info("Update product: {}", request);
        Product product = productService.getById(id);
        productMapper.updateProduct(product, request);
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete product", description = "API delete product to database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        log.info("Delete product: {}", id);
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
