package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.CategoryRequest;
import com.fpoly.asm.controller.response.CategoryResponse;
import com.fpoly.asm.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "products", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}

