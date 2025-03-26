package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.OrderDetailRequest;
import com.fpoly.asm.controller.response.OrderDetailResponse;
import com.fpoly.asm.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    OrderDetail toOrderDetail(OrderDetailRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
