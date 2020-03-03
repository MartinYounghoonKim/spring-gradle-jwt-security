package com.springgradlejwtsecurity.auth.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenClaim {
    private String userId;
    private String permission;
}
