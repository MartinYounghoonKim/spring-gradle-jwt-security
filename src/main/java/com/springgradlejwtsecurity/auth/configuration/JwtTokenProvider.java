package com.springgradlejwtsecurity.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgradlejwtsecurity.auth.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final String ENCRYPT_STRING =  "humansApiToekn!@#$%^&*()";

    private final UserDetailsService userDetailsService;

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getAccountIdByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getAccountIdByToken(String token) {
        return Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody().getSubject();
    }

    private byte[] generateKey(){
        byte[] key = null;
        try {
            key = ENCRYPT_STRING.getBytes("UTF-8");
        } catch (Exception e) {
        }

        return key;
    }
    public boolean verifyToken(String token) {
        Optional.ofNullable(token)
                .orElseThrow(() -> new SignatureException("토큰 값은 필수입니다."));
        try {
            Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
    public Jws<Claims> parseToken (String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        Jws<Claims> test = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
        Account account = objectMapper.convertValue(test.getBody().get("user"), Account.class);
        System.out.println(account);

        return test;
    }
}
