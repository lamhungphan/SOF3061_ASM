package com.fpoly.asm.service;

import com.fpoly.asm.controller.request.SignInRequest;
import com.fpoly.asm.controller.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse getAccessToken(SignInRequest request);

    TokenResponse getRefreshToken(String request);
}
