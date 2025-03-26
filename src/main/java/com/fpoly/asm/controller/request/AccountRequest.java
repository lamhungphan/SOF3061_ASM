package com.fpoly.asm.controller.request;

import com.fpoly.asm.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Role role;
}
