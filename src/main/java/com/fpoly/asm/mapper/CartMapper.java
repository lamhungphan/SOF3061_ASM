package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.CartRequest;
import com.fpoly.asm.controller.response.CartResponse;
import com.fpoly.asm.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    Cart toCart(CartRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    CartResponse toCartResponse(Cart cart);
}
