package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.response.AccountResponse;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.mapper.AccountMapper;
import com.fpoly.asm.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Account Controller")
@Slf4j(topic = "ACCOUNT-CONTROLLER")
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Operation(summary = "Get Account List", description = "API retrieve account from database")
    @GetMapping
    public ResponseEntity<Page<AccountResponse>> getAllAccount(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("get all account");

        Page<Account> account = accountService.getAll(keyword, sort, page, size);
        Page<AccountResponse> response = account.map(accountMapper::toAccountResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Account Detail", description = "API retrieve account detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Integer id) {
        log.info("get account");

        Account account = accountService.getById(id);
        return ResponseEntity.ok(accountMapper.toAccountResponse(account));
    }

    @Operation(summary = "Create Account", description = "API add new account to database")
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        log.info("create account");

        Account account = accountMapper.toAccount(request);
        Account savedaccount = accountService.save(account);
        return ResponseEntity.ok(accountMapper.toAccountResponse(savedaccount));
    }

    @Operation(summary = "Update Account", description = "API update account to database")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable Integer id, @RequestBody AccountRequest request) {
        log.info("update account");

        Account account = accountService.getById(id);
        accountMapper.updateAccount(account, request);
        accountService.save(account);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete Account", description = "API delete account to database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        log.info("delete account");

        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
