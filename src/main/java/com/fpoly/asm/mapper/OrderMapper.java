package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.OrderRequest;
import com.fpoly.asm.controller.response.OrderResponse;
import com.fpoly.asm.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "userId", target = "user.id")
    Order toOrder(OrderRequest request);

    OrderResponse toOrderResponse(Order order);
}
