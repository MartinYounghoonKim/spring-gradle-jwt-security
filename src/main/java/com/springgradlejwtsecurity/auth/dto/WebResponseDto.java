package com.springgradlejwtsecurity.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebResponseDto<T> {
    private WebResponseCode code;
    private boolean isSuccess;
    private T content;
    private String message;

    public static <T> WebResponseDto<T> success (T data) {
        return new WebResponseDto<>(WebResponseCode.SUCCESS, true, data, "");
    }
    public static WebResponseDto failure (String errorMessage) {
        return new WebResponseDto(WebResponseCode.FAILURE, false, null, errorMessage);
    }

    enum WebResponseCode {
        SUCCESS("성공하였습니다."),
        FAILURE("실패하였습니다.");

        private String message;

        WebResponseCode(String message) {
            this.message = message;
        }
    }
}
