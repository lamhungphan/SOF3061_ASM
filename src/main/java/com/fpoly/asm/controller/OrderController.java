package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.OrderRequest;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.controller.response.OrderResponse;
import com.fpoly.asm.controller.response.PageResponse;
import com.fpoly.asm.entity.Order;
import com.fpoly.asm.mapper.OrderMapper;
import com.fpoly.asm.service.OrderService;
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
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
@Slf4j(topic = "ORDER-CONTROLLER")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(summary = "Get Order List", description = "API retrieve order from database")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getAllOrder(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("get All Categories");

        Page<Order> order = orderService.getAll(keyword, sort, page, size);
        Page<OrderResponse> response = order.map(orderMapper::toOrderResponse);
        return ResponseEntity.ok(ApiResponse.success(new PageResponse<>(response), "Order list retrieved successfully"));
    }

    @Operation(summary = "Get Order Detail", description = "API retrieve order detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable Integer id) {
        log.info("get order");

        Order order = orderService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(orderMapper.toOrderResponse(order), "Order retrieved successfully"));
    }

    @Operation(summary = "Create Order", description = "API add new order to database")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request) {
        log.info("create order");

        Order order = orderMapper.toOrder(request);
        Order savedOrder = orderService.save(order);
        return ResponseEntity.ok(ApiResponse.success(orderMapper.toOrderResponse(savedOrder), "Order created successfully"));
    }

    @Operation(summary = "Update Order", description = "API update order in database")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateOrder(@PathVariable Integer id, @RequestBody OrderRequest request) {
        log.info("update order");

        Order order = orderService.getById(id);
        orderMapper.updateOrder(order, request);
        orderService.save(order);
        return ResponseEntity.ok(ApiResponse.success(null, "Order updated successfully"));
    }

    @Operation(summary = "Delete Order", description = "API delete order from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        log.info("delete order");

        orderService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Order deleted successfully"));
    }
}
