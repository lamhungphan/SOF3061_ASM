package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.ProductRequest;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductRequest request);

    @Mapping(target = "categoryId", expression = "java(product.getCategory() != null ? product.getCategory().getId() : null)")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}


