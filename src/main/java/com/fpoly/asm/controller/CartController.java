package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.CartRequest;
import com.fpoly.asm.controller.response.CartResponse;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.mapper.CartMapper;
import com.fpoly.asm.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart Controller")
@Slf4j(topic = "CART-CONTROLLER")
@Validated
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @Operation(summary = "Get Cart", description = "Retrieve cart for a user")
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Integer userId) {
        log.info("get cart for user {}", userId);

        Cart cart = cartService.getByUserId(userId);
        return ResponseEntity.ok(cartMapper.toCartResponse(cart));
    }

    @Operation(summary = "Add Item to Cart", description = "Add a product to the user's cart")
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest request) {
        log.info("add product {} to cart of user {}", request.getProductId(), request.getUserId());

        Cart cart = cartService.addToCart(request);
        return ResponseEntity.ok(cartMapper.toCartResponse(cart));
    }

    @Operation(summary = "Remove Item from Cart", description = "Remove a product from the user's cart")
    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        log.info("remove product {} from cart of user {}", productId, userId);

        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Clear Cart", description = "Remove all products from the user's cart")
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Integer userId) {
        log.info("clear cart for user {}", userId);

        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
