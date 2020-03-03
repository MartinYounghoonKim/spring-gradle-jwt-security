package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.entity.Account;
import com.springgradlejwtsecurity.auth.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountDetailService implements UserDetailsService {
	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = Optional.ofNullable(accountRepository.findAccountByUserId(username)).orElseThrow(() -> new RuntimeException("Not found user"));

		return new SecurityAccount(account);
	}
}
