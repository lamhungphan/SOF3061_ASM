package com.fpoly.asm.service;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> getCartItems(Account user);
    public void addToCart(Account user, Integer productId);
    public void removeFromCart(Account user, Integer productId);
}
