package com.fpoly.asm.mapper;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.response.AccountResponse;
import com.fpoly.asm.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountRequest request);
    AccountResponse toAccountResponse(Account account);
}
