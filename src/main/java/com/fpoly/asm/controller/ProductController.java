package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.ProductCreationRequest;
import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.controller.request.ProductUpdateRequest;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.controller.response.ProductResp;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.mapper.ProductMapper;
import com.fpoly.asm.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;


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
    @GetMapping("/list")
    public ApiResponse getProductList(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        log.info("Get product list");
        return ApiResponse.builder().status(HttpStatus.OK.value())
                .message("product list")
                .data(productService.getAll(keyword, sort, page, size))
                .build();
    }

    @Operation(summary = "Get product detail", description = "API retrieve product detail by ID from database")
    @GetMapping("/{productId}")
    public ApiResponse getProductDetail(@PathVariable Integer productId) {
        log.info("Get user detail by ID: {}", productId);
        return ApiResponse.builder().status(HttpStatus.OK.value())
                .message("product detail")
                .data(productService.getById(productId))
                .build();
    }

    @Operation(summary = "Create product", description = "API add new product to database")
    @PostMapping()
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        log.info("Create product: {}", request);
        Product product = productMapper.toProduct(request);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(productMapper.toProductResponse(savedProduct));
    }

    @Operation(summary = "Update product", description = "API delete product to database")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        log.info("Update product: {}", request);
        Product product = productService.getById(id);
        productMapper.updateProduct(product, request);
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete product", description = "API delete product to database")
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteProduct(@PathVariable Integer id) {
        log.info("Delete product: {}", id);
        productService.delete(id);
        return ApiResponse.builder()
                .status(HttpStatus.RESET_CONTENT.value())
                .message("Product deleted successfully")
                .data("")
                .build();
    }
}
