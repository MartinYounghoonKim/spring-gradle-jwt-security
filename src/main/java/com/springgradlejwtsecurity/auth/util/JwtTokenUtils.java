package com.springgradlejwtsecurity.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtils {
    static private final String ENCRYPT_STRING =  "tutorialJwtSecurity!@#$%%^&&*()";
    static private final String DATA_KEY = "user";
    static private long HALF_HOUR = 1000L * 60 * 30;

    static public String createToken (JwtTokenClaim jwtTokenClaim) {
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

    static private byte[] generateEncryptSecretKey(){
        byte[] key = null;
        try {
            key = ENCRYPT_STRING.getBytes("UTF-8");
        } catch (Exception e) {
        }

        return key;
    }
}
