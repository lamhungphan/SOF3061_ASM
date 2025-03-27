package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.OrderDetailRequest;
import com.fpoly.asm.controller.response.OrderDetailResponse;
import com.fpoly.asm.entity.OrderDetail;
import com.fpoly.asm.mapper.OrderDetailMapper;
import com.fpoly.asm.service.OrderDetailService;
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
@RequestMapping("/order-detail")
@RequiredArgsConstructor
@Tag(name = "Order Detail Controller")
@Slf4j(topic = "ORDER-DETAIL-CONTROLLER")
@Validated
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final OrderDetailMapper orderDetailMapper;

    @Operation(summary = "Get Order Detail List", description = "API retrieve order details from database")
    @GetMapping
    public ResponseEntity<Page<OrderDetailResponse>> getAllOrderDetails(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("get All Order Details");

        Page<OrderDetail> orderDetails = orderDetailService.getAll(keyword, sort, page, size);
        Page<OrderDetailResponse> response = orderDetails.map(orderDetailMapper::toOrderDetailResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Order Detail", description = "API retrieve order detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Integer id) {
        log.info("get order detail");

        OrderDetail orderDetail = orderDetailService.getById(id);
        return ResponseEntity.ok(orderDetailMapper.toOrderDetailResponse(orderDetail));
    }

    @Operation(summary = "Create Order Detail", description = "API add new order detail to database")
    @PostMapping
    public ResponseEntity<OrderDetailResponse> createOrderDetail(@Valid @RequestBody OrderDetailRequest request) {
        log.info("create order detail");

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);
        OrderDetail savedOrderDetail = orderDetailService.save(orderDetail);
        return ResponseEntity.ok(orderDetailMapper.toOrderDetailResponse(savedOrderDetail));
    }

    @Operation(summary = "Update Order Detail", description = "API update order detail in database")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetailRequest request) {
        log.info("update order detail");

        orderDetailService.update(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete Order Detail", description = "API delete order detail from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        log.info("delete order detail");

        orderDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
