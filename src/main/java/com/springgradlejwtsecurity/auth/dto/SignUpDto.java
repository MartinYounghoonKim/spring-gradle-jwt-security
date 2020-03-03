package com.springgradlejwtsecurity.auth.dto;

import com.springgradlejwtsecurity.auth.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class SignUpDto {
    private final PasswordEncoder passwordEncoder;

    @NotNull(message = "아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, message = "비밀번호는 최소 8자리 이상 입력해주세요.")
    private String password;

    public Account toEntity () {
        return Account.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
