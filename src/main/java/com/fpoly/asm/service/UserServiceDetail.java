package com.fpoly.asm.service;

import com.fpoly.asm.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record UserServiceDetail(AccountRepository userRepository) { // java 17

//    public UserDetailsService getUserDetailsService() {
//        return userRepository::findByUsername;
//    }
}
