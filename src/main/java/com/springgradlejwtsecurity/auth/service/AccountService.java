package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.dto.AuthDto;
import com.springgradlejwtsecurity.auth.dto.SignUpDto;
import com.springgradlejwtsecurity.auth.entity.Account;
import com.springgradlejwtsecurity.auth.exception.CustomException;
import com.springgradlejwtsecurity.auth.repository.AccountRepository;
import com.springgradlejwtsecurity.auth.util.JwtTokenClaim;
import com.springgradlejwtsecurity.auth.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public void signUp (SignUpDto signUpDto) {
        boolean isExistedAccountId = Optional.ofNullable(accountRepository.findAccountByUserId(signUpDto.getUserId()))
                .isPresent();

        if (isExistedAccountId) {
            throw new CustomException("이미 존재하는 아이디입니다.");
        }
        Account account = Account.builder()
                .userId(signUpDto.getUserId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        accountRepository.save(account);
    }

    @Transactional
    public String signIn(AuthDto authDto, HttpServletResponse response) {
        Account account = Optional.ofNullable(accountRepository.findAccountByUserId(authDto.getUserId()))
                .orElseThrow(() -> new CustomException("해당하는 계정 아이디가 없습니다."));
        if (!passwordEncoder.matches(authDto.getPassword(), account.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }

        JwtTokenClaim jwtTokenClaim = JwtTokenClaim.builder()
                .userId(account.getUserId())
                .permission(account.getPermission())
                .build();
        String accessToken = jwtTokenUtils.createToken(jwtTokenClaim);
        setAuthenticationCookie(accessToken, response);

        return accessToken;
    }

    private void setAuthenticationCookie (String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setValue(token);

        response.addCookie(cookie);
    }
}
