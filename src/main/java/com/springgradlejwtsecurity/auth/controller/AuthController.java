package com.springgradlejwtsecurity.auth.controller;

import com.springgradlejwtsecurity.auth.dto.SignUpDto;
import com.springgradlejwtsecurity.auth.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
	private AccountService accountService;
	@PostMapping("/sign-up")
	public boolean singUp (@RequestBody SignUpDto signUpDto) {
		accountService.signUp(signUpDto);
		return true;
	}
	@GetMapping("/sign-in")
	public String signIn () {
		return "Sign in";
	}
	@GetMapping("/verify")
	public String Verify () {
		return "Verify";
	}
}
