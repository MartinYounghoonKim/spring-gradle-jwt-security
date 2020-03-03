package com.springgradlejwtsecurity.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String Hello () {
		return "Hello";
	}

	@GetMapping("/hello-admin")
	public String HelloForAdmin () {
		return "Hello Admin";
	}

	@GetMapping("/hello-member")
	public String HelloForMember () {
		return "Hello Member";
	}
}
