package com.springgradlejwtsecurity.auth.dto;

import com.springgradlejwtsecurity.auth.entity.Account;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AuthDto {
    private Long id;

    @NotNull(message = "아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    private String permission;

    public Account toEntity () {
        return Account.builder()
                .userId(userId)
                .password(password)
                .permission(permission)
                .build();
    }
}
