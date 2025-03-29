package com.fpoly.asm.service;

import com.fpoly.asm.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record UserServiceDetail(AccountRepository accountRepository) {

    public UserDetailsService getAccountDetailsService() {
        return accountRepository::findByUsername;
    }
}
