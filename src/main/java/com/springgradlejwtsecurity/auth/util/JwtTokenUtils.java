package com.springgradlejwtsecurity.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgradlejwtsecurity.auth.entity.Account;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtTokenUtils {
    private final UserDetailsService userDetailsService;
    private final String ENCRYPT_STRING =  "tutorialJwtSecurity!@#$%%^&&*()";
    private final String DATA_KEY = "user";
    private final long HALF_HOUR = 1000L * 60 * 30;

    public String createToken (JwtTokenClaim jwtTokenClaim) {
        Claims claims = Jwts.claims().setSubject(jwtTokenClaim.getUserId());
        claims.put("role", jwtTokenClaim.getPermission());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(new Date(System.currentTimeMillis() + HALF_HOUR))
                .setClaims(claims)
                .claim(DATA_KEY, jwtTokenClaim)
                .signWith(SignatureAlgorithm.HS256, generateEncryptSecretKey())
                .compact();

    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getAccountIdByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getAccountIdByToken(String token) {
        return Jwts.parser().setSigningKey(generateEncryptSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean verifyToken(String token) {
        Optional.ofNullable(token)
                .orElseThrow(() -> new SignatureException("토큰 값은 필수입니다."));
        try {
            Jwts.parser().setSigningKey(generateEncryptSecretKey()).parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
    public Jws<Claims> parseToken (String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        Jws<Claims> test = Jwts.parser().setSigningKey(generateEncryptSecretKey()).parseClaimsJws(token);
        Account account = objectMapper.convertValue(test.getBody().get("user"), Account.class);
        System.out.println(account);

        return test;
    }

    private byte[] generateEncryptSecretKey(){
        byte[] key = null;
        try {
            key = ENCRYPT_STRING.getBytes("UTF-8");
        } catch (Exception e) {
        }

        return key;
    }
}
