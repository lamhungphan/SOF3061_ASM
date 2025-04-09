package com.fpoly.asm.repository;

import com.fpoly.asm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
   Account findByUsername(String username);
   Account findByEmail(String email);
}
