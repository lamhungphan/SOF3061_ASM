package com.fpoly.asm.service.impl;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.repository.CartRepository;
import com.fpoly.asm.repository.ProductRepository;
import com.fpoly.asm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Cart> getCartItems(Account user) {
        return cartRepository.findByUser(user);
    }

    public void addToCart(Account user, Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Cart cartItem = cartRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new Cart(null, user, product, 1);
        }
        cartRepository.save(cartItem);
    }

    public void removeFromCart(Account user, Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Cart cartItem = cartRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }
}
