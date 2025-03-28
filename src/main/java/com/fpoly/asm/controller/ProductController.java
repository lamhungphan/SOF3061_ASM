package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.controller.response.PageResponse;
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
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Get product list");

        Page<Product> products = productService.getAll(keyword, sort, page, size);
        Page<ProductResponse> respPage = products.map(productMapper::toProductResponse);
        return ResponseEntity.ok(ApiResponse.success(new PageResponse<>(respPage), "Product list retrieved successfully"));
    }

    @Operation(summary = "Get product detail", description = "API retrieve product detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductDetail(@PathVariable Integer id) {
        log.info("Fetching product detail with ID: {}", id);

        Product product = productService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(productMapper.toProductResponse(product), "Product retrieved successfully"));
    }

    @Operation(summary = "Create product", description = "API add new product to database")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Creating new product: {}", request);

        Product product = productMapper.toProduct(request);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(ApiResponse.success(productMapper.toProductResponse(savedProduct), "Product created successfully"));
    }

    @Operation(summary = "Update product", description = "API update product in database")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        log.info("Updating product with ID: {}", id);

        Product product = productService.getById(id);
        productMapper.updateProduct(product, request);
        productService.save(product);
        return ResponseEntity.ok(ApiResponse.success(null, "Product updated successfully"));
    }

    @Operation(summary = "Delete product", description = "API delete product from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        log.info("Deleting product with ID: {}", id);

        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }
}
