package com.springgradlejwtsecurity.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgradlejwtsecurity.auth.entity.Account;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final String ENCRYPT_STRING =  "humansApiToekn!@#$%^&*()";

    private long A_HOUR = 1000L * 60 * 60;
    private static final String DATA_KEY = "user";
    private final UserDetailsService userDetailsService;

    public String createToken (Account account) {
        Claims claims = Jwts.claims().setSubject(account.getUserId());
        claims.put("role", account.getPermission());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(new Date(System.currentTimeMillis() + A_HOUR))
                .setClaims(claims)
                .claim(DATA_KEY, account)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }
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
