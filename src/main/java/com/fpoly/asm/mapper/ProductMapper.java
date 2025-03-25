package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.ProductCreationRequest;
import com.fpoly.asm.controller.request.ProductUpdateRequest;
import com.fpoly.asm.controller.response.ProductPageResponse;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductCreationRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);

    default ProductPageResponse toProductPageResponse(Page<Product> productPage, int page, int size) {
        List<ProductResponse> productList = productPage.stream()
                .map(this::toProductResponse)
                .toList();

        ProductPageResponse response = new ProductPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setProducts(productList);

        return response;
    }

}

