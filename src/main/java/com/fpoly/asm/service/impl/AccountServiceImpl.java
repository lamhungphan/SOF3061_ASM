package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.exception.ResourceNotFoundException;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends AbstractService<Account, Integer, AccountRequest> implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with username: " + username);
        }
        return account;
    }

    @Override
    public void update(AccountRequest request) {

    }
}
