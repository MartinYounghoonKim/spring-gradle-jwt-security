package com.springgradlejwtsecurity.auth.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
public class SignUpDto {
    @NotNull(message = "아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, message = "비밀번호는 최소 8자리 이상 입력해주세요.")
    private String password;

}
