package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.CartRequest;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.exception.ResourceNotFoundException;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.repository.CartRepository;
import com.fpoly.asm.repository.ProductRepository;
import com.fpoly.asm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Cart> getCartItems(Account user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void addToCart(Account user, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cartItem = cartRepository.findByUserAndProduct(user, product)
                .orElse(new Cart(user, product, 0));

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Integer userId, Integer productId) {
        Account user = accountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cartItem = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartRepository.delete(cartItem);
    }

    @Override
    public Cart getByUserId(Integer userId) {
        Account user = accountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return cartRepository.findByUser(user)
                .stream()
                .findFirst()
                .orElse(new Cart(user, null, 0));
    }

    @Override
    public Cart addToCart(CartRequest request) {
        Account user = accountRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cartItem = cartRepository.findByUserAndProduct(user, product)
                .orElse(new Cart(user, product, 0));

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        return cartRepository.save(cartItem);
    }

    @Override
    public void clearCart(Integer userId) {
        Account user = accountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser(user);
        cartRepository.deleteAll(cartItems);
    }

}
