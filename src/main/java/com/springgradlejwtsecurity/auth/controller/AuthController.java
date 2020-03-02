package com.springgradlejwtsecurity.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	@GetMapping("/sign-in")
	public String signIn () {
		return "Sign in";
	}
	@GetMapping("/verify")
	public String Verify () {
		return "Verify";
	}
}
