package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.CategoryRequest;
import com.fpoly.asm.controller.response.CategoryResponse;
import com.fpoly.asm.entity.Category;
import com.fpoly.asm.mapper.CategoryMapper;
import com.fpoly.asm.service.CategoryService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category Controller")
@Slf4j(topic = "CATEGORY-CONTROLLER")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Get category list", description = "API retrieve category from database")
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("getAllCategories");

        Page<Category> categories = categoryService.getAll(keyword, sort, page, size);
        Page<CategoryResponse> response = categories.map(categoryMapper::toCategoryResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get category detail", description = "API retrieve category detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Integer id) {
        log.info("getCategory");

        Category category = categoryService.getById(id);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(category));
    }

    @Operation(summary = "Create category", description = "API add new category to database")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        log.info("createCategory");

        Category category = categoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(summary = "Update category", description = "API update category to database")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest request) {
        log.info("updateCategory");

        Category category = categoryService.getById(id);
        categoryMapper.updateCategory(category, request);
        categoryService.save(category);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Delete category", description = "API delete category to database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        log.info("deleteCategory");

        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
