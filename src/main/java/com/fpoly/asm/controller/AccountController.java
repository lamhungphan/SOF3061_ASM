package com.fpoly.asm.controller;

import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.request.SignInRequest;
import com.fpoly.asm.controller.response.AccountResponse;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.controller.response.PageResponse;
import com.fpoly.asm.controller.response.TokenResponse;
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
    public ResponseEntity<ApiResponse<PageResponse<AccountResponse>>> getAllAccount(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("get all account");

        Page<Account> account = accountService.getAll(keyword, sort, page, size);
        Page<AccountResponse> response = account.map(accountMapper::toAccountResponse);
        return ResponseEntity.ok(ApiResponse.success(new PageResponse<>(response), "Account list retrieved successfully"));
    }

    @Operation(summary = "Get Account Detail", description = "API retrieve account detail by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Integer id) {
        log.info("get account");

        Account account = accountService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(accountMapper.toAccountResponse(account), "Account retrieved successfully"));
    }

    @Operation(summary = "Create Account", description = "API add new account to database")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {
        log.info("create account");

        Account account = accountMapper.toAccount(request);
        Account savedAccount = accountService.save(account);
        return ResponseEntity.ok(ApiResponse.success(accountMapper.toAccountResponse(savedAccount), "Account created successfully"));
    }

    @Operation(summary = "Update Account", description = "API update account to database")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateAccount(@PathVariable Integer id, @RequestBody AccountRequest request) {
        log.info("update account");

        Account account = accountService.getById(id);
        accountMapper.updateAccount(account, request);
        accountService.save(account);
        return ResponseEntity.ok(ApiResponse.success(null, "Account updated successfully"));
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid AccountRequest request) {
        log.info("change password");

        Account account = accountMapper.toAccount(request);
        accountService.save(account);
        return ResponseEntity.ok(ApiResponse.success(null, "Account change password successfully"));
    }

    @Operation(summary = "Delete Account", description = "API delete account to database")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Integer id) {
        log.info("delete account");

        accountService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Account deleted successfully"));
    }
}
