package com.fpoly.asm.TestUserService;

import com.fpoly.asm.controller.AccountController;
import com.fpoly.asm.controller.request.AccountRequest;
import com.fpoly.asm.controller.response.AccountResponse;
import com.fpoly.asm.controller.response.ApiResponse;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.mapper.AccountMapper;
import com.fpoly.asm.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class) // Chạy test trên AccountController
@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper; // Dùng để chuyển object -> JSON

    @Test
    void testGetAccountById() throws Exception {
        // Giả lập dữ liệu
        Account account = new Account(1, "testUser", "password");
        AccountResponse accountResponse = new AccountResponse(1, "testUser");

        // Khi gọi accountService.getById(1) thì trả về account
        when(accountService.getById(1)).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(accountResponse);

        // Gửi request GET /account/1
        mockMvc.perform(MockMvcRequestBuilders.get("/account/1"))
                .andExpect(status().isOk()) // Kiểm tra HTTP Status 200
                .andExpect(jsonPath("$.data.username").value("testUser")); // Kiểm tra giá trị username
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountRequest request = new AccountRequest("newUser", "newPassword");
        Account account = new Account(2, "newUser", "newPassword");
        AccountResponse response = new AccountResponse(2, "newUser");

        when(accountMapper.toAccount(any(AccountRequest.class))).thenReturn(account);
        when(accountService.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("newUser"));
    }

    @Test
    void testDeleteAccount() throws Exception {
        Mockito.doNothing().when(accountService).delete(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/1"))
                .andExpect(status().isOk());
    }
}
