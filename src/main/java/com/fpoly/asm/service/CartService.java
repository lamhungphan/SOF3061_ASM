package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.CartRequest;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> getCartItems(Account user);

    public Cart updateCart(CartRequest request);

    List<Cart> getByUserId(Integer userId);

    Cart addToCart(CartRequest request);

    void clearCart(Integer userId);

    void removeFromCart(Integer userId, Integer productId);
}
