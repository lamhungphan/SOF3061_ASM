package com.fpoly.asm.service.impl;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.exception.ResourceNotFoundException;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl extends AbstractService<Account, Integer> implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with username: " + username));
    }

    @Override
    public void update(Account request) {

    }
}

