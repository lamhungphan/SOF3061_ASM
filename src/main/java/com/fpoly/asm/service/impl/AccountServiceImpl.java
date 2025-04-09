package com.fpoly.asm.service.impl;

import com.fpoly.asm.common.SecurityUtil;
import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.request.PasswordChangeRequest;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Role;
import com.fpoly.asm.exception.ResourceNotFoundException;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.AccountService;
import com.github.dockerjava.api.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends AbstractService<Account, Integer, AccountRequest> implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Account findByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with email: " + email);
        }
        return account;
    }

    @Override
    public void updatePassword(PasswordChangeRequest request) {
        String username = SecurityUtil.getCurrentUsername();

        // Tìm account theo username
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // So sánh mật khẩu cũ (đã mã hoá)
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new BadRequestException("Password does not match");
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
    }

    @Override
    public void update(AccountRequest request) {

    }

    @Override
    public Account save(Account account) {
        account.setRole(Role.CUSTOMER);
        if (account.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        return super.save(account);
    }
}
