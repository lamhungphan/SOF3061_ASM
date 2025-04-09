package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.request.PasswordChangeRequest;
import com.fpoly.asm.entity.Account;

public interface AccountService extends BaseService<Account, Integer, AccountRequest> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    void updatePassword(PasswordChangeRequest request);
}
