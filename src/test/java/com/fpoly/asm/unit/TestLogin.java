package com.fpoly.asm.unit;

import com.fpoly.asm.controller.request.SignInRequest;
import com.fpoly.asm.controller.response.TokenResponse;
import com.fpoly.asm.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLogin {

	@Autowired
	private AuthenticationService authService;

	@Test
	void testLoginSuccess() {
		SignInRequest request = new SignInRequest();
		request.setUsername("staff");
		request.setPassword("123");

		TokenResponse response = authService.getAccessToken(request);

		assertNotNull(response);
		assertNotNull(response.getAccessToken());
		assertNotNull(response.getRefreshToken());
	}

	@Test
	void testLoginWrongPassword() {
		SignInRequest request = new SignInRequest();
		request.setUsername("staff");
		request.setPassword("wrong");

		assertThrows(RuntimeException.class, () -> {
			authService.getAccessToken(request);
		});
	}

	@Test
	void testLoginUserNotFound() {
		SignInRequest request = new SignInRequest();
		request.setUsername("unknown");
		request.setPassword("any");

		assertThrows(RuntimeException.class, () -> {
			authService.getAccessToken(request);
		});
	}
}
