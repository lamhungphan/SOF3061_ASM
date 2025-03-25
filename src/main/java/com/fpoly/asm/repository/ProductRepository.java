package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);

    @Query(value = "FROM Product u WHERE lower(u.name) LIKE :keyword OR lower(u.description) LIKE :keyword ")
    Page<Product> searchByKeyword(String keyword, Pageable pageable);
}
