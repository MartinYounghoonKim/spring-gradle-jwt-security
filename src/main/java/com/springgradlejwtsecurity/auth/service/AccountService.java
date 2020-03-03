package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.dto.AuthDto;
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
    public Account signUp (AuthDto authDto) {
        Account account = Account.builder()
                .userId(authDto.getUserId())
                .password(passwordEncoder.encode(authDto.getPassword()))
                .build();
        return accountRepository.save(account);
    }
}
