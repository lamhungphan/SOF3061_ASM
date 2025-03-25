package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(Account user);
    Cart findByUserAndProduct(Account user, Product product);
}
