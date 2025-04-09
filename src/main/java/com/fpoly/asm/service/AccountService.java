package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.request.PasswordChangeRequest;
import com.fpoly.asm.entity.Account;

public interface AccountService extends BaseService<Account, Integer, AccountRequest> {
    public Account findByUsername(String username);
    public Account findByEmail(String email);
    public void updatePassword(PasswordChangeRequest request);
}
