package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.CartRequest;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import jakarta.validation.Valid;

import java.util.List;

public interface CartService {
    public List<Cart> getCartItems(Account user);

    public void addToCart(Account user, Integer productId);


    Cart getByUserId(Integer userId);

    Cart addToCart(@Valid CartRequest request);

    void clearCart(Integer userId);

    void removeFromCart(Integer userId, Integer productId);
}
