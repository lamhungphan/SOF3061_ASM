package com.fpoly.asm.service;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Category;

import java.util.Optional;
import java.util.List;

public interface AccountService extends BaseService<Account, Integer>{
    public Account findByUsername(String username);
}
