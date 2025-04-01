package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.request.SignInRequest;
import com.fpoly.asm.controller.response.AccountResponse;
import com.fpoly.asm.controller.response.TokenResponse;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Category;

import java.util.Optional;
import java.util.List;

public interface AccountService extends BaseService<Account, Integer, AccountRequest> {
    public Account findByUsername(String username);
}
