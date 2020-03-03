package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.configuration.JwtTokenProvider;
import com.springgradlejwtsecurity.auth.dto.AuthDto;
import com.springgradlejwtsecurity.auth.entity.Account;
import com.springgradlejwtsecurity.auth.exception.CustomException;
import com.springgradlejwtsecurity.auth.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {
    private final JwtTokenProvider jwtTokenProvider;
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

    public String signIn(AuthDto authDto) {
        Account account = Optional.ofNullable(accountRepository.findAccountByUserId(authDto.getUserId()))
                .orElseThrow(() -> new CustomException("해당하는 계정 아이디가 없습니다."));
        if (!passwordEncoder.matches(authDto.getPassword(), account.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(account);
    }
}
