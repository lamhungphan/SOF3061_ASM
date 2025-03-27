package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(Account user);
    Optional<Cart> findByUserAndProduct(Account user, Product product);
    void deleteByUser(Account user);
}
