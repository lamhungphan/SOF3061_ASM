package com.fpoly.asm.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String platform; // web, mobile, tablet
    private String deviceToken; // for push notify
    private String versionApp;
}
