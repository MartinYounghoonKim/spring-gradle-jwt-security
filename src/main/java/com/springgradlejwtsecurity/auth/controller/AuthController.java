package com.springgradlejwtsecurity.auth.controller;

import com.springgradlejwtsecurity.auth.dto.AuthDto;
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
	public boolean singUp (@RequestBody AuthDto authDto) {
		accountService.signUp(authDto);
		return true;
	}
	@PostMapping("/sign-in")
	public String signIn (@RequestBody AuthDto authDto) {
		return accountService.signIn(authDto);
	}
	@GetMapping("/verify")
	public String Verify () {
		return "Verify";
	}
}
