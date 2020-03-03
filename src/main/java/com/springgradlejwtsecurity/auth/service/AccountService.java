package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.dto.SignUpDto;
import com.springgradlejwtsecurity.auth.entity.Account;
import com.springgradlejwtsecurity.auth.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    @Transactional
    public Account signUp (SignUpDto signUpDto) {
        Account account = Account.builder()
                .userId(signUpDto.getUserId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        return accountRepository.save(account);
    }
}
