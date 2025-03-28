package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.SignInRequest;
import com.fpoly.asm.controller.response.TokenResponse;
import com.fpoly.asm.entity.Account;
import com.fpoly.asm.repository.AccountRepository;
import com.fpoly.asm.service.AuthenticationService;
import com.fpoly.asm.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.fpoly.asm.common.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl  {

    
}
