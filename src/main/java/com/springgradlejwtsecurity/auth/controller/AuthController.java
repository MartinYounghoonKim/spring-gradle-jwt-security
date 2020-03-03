package com.springgradlejwtsecurity.auth.controller;

import com.springgradlejwtsecurity.auth.dto.AuthDto;
import com.springgradlejwtsecurity.auth.dto.SignUpDto;
import com.springgradlejwtsecurity.auth.dto.WebResponseDto;
import com.springgradlejwtsecurity.auth.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	final private AccountService accountService;
	@PostMapping("/sign-up")
	public WebResponseDto singUp (@Valid @RequestBody SignUpDto signUpDto) {
		accountService.signUp(signUpDto);
		return WebResponseDto.success(null);
	}
	@PostMapping("/sign-in")
	public WebResponseDto signIn (@Valid @RequestBody AuthDto authDto, HttpServletRequest request, HttpServletResponse response) {
		return WebResponseDto.success(accountService.signIn(authDto, request, response));
	}
	@GetMapping("/verify")
	public String Verify () {
		return "Verify";
	}
}
