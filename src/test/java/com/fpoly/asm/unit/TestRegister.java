package com.fpoly.asm.unit;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Role;
import com.fpoly.asm.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestRegister {

    @Autowired
    private AccountService accountService;

    @Test
    void testRegisterSuccess() {
        Account request = new Account();
        request.setUsername("newuser");
        request.setPassword("123");
        request.setEmail("newuser@example.com");
        request.setFullName("New User");
        request.setPhone("0987654301");
        request.setAddress("HN");
        request.setRole(Role.CUSTOMER);

        assertDoesNotThrow(() -> {
            accountService.save(request);
        });
    }

    @Test
    void testRegisterDuplicateUsername() {
        Account request = new Account();
        request.setUsername("staff");
        request.setPassword("any");
        request.setEmail("duplicate@example.com");
        request.setFullName("Dup User");
        request.setPhone("0000000000");
        request.setAddress("HCM");
        request.setRole(Role.CUSTOMER);

        assertThrows(RuntimeException.class, () -> {
            accountService.save(request);
        });
    }
}
