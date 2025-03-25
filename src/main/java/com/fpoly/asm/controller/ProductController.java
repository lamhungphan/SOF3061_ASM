package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.ProductCreationRequest;
import com.fpoly.asm.controller.request.ProductUpdateRequest;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.service.ProductService;
import com.fpoly.asm.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Get product list", description = "API retrieve product from database")
    @GetMapping("/list")
    public ApiResponse getProductList(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size) {
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
    @PostMapping("/add")
    public ApiResponse createProduct(@RequestBody ProductCreationRequest request) {

        log.info("Create product: {}", request);
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .data(productService.save(request))
                .build();
    }

    @Operation(summary = "Update product", description = "API delete product to database")
    @PutMapping("/update")
    public ApiResponse updateProduct(@RequestBody @Valid ProductUpdateRequest request) {
        log.info("Update product: {}", request);

        productService.update(request);

        return ApiResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Product updated successfully")
                .data("")
                .build();
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

