//package com.fpoly.asm.TestUserService;
//
//import com.fpoly.asm.controller.AuthenticationController;
//import com.fpoly.asm.controller.request.SignInRequest;
//import com.fpoly.asm.controller.response.TokenResponse;
//import com.fpoly.asm.service.AuthenticationService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthenticationController.class) // Chạy test trên AuthenticationController
//@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito
//class AuthenticationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private AuthenticationService authenticationService;
//
//    @InjectMocks
//    private AuthenticationController authenticationController;
//
//    @Autowired
//    private ObjectMapper objectMapper; // Dùng để chuyển object -> JSON
//
//    @Test
//    void testLoginSuccess() throws Exception {
//        SignInRequest request = new SignInRequest("admin", "123");
//        TokenResponse tokenResponse = new TokenResponse("access_token", "refresh_token");
//
//        when(authenticationService.getAccessToken(any(SignInRequest.class))).thenReturn(tokenResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/access-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk()) // Kiểm tra HTTP 200
//                .andExpect(jsonPath("$.accessToken").value("access_token"))
//                .andExpect(jsonPath("$.refreshToken").value("refresh_token"));
//    }
//
//    @Test
//    void testLoginWithInvalidUsername() throws Exception {
//        SignInRequest request = new SignInRequest("not-admin", "123");
//
//        when(authenticationService.getAccessToken(any(SignInRequest.class)))
//                .thenThrow(new RuntimeException("Invalid username or password"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/access-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void testLoginWithInvalidPassword() throws Exception {
//        SignInRequest request = new SignInRequest("admin", "123456");
//
//        when(authenticationService.getAccessToken(any(SignInRequest.class)))
//                .thenThrow(new RuntimeException("Invalid username or password"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/access-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void testLoginWithEmptyUsername() throws Exception {
//        SignInRequest request = new SignInRequest("", "123");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/access-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void testRefreshTokenSuccess() throws Exception {
//        String refreshToken = "valid_refresh_token";
//        TokenResponse tokenResponse = new TokenResponse("new_access_token", "new_refresh_token");
//
//        when(authenticationService.getRefreshToken(refreshToken)).thenReturn(tokenResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(refreshToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value("new_access_token"));
//    }
//
//    @Test
//    void testRefreshTokenInvalid() throws Exception {
//        String refreshToken = "invalid_refresh_token";
//
//        when(authenticationService.getRefreshToken(refreshToken))
//                .thenThrow(new RuntimeException("Invalid refresh token"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(refreshToken))
//                .andExpect(status().isUnauthorized());
//    }
//}
//
