package com.springgradlejwtsecurity.auth.configuration;

import com.springgradlejwtsecurity.auth.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @description OncePerRequestFilter
 * OncePerRequestFilter 은 GenericFilterBean 상속한 클래스로서, filter를 중첩 호출한 경우
 * 매번 filter 의 내용이 수행되는 것을 방지하기 위해 사용함.
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = Optional.ofNullable(WebUtils.getCookie(request, "accessToken"))
                .orElse(null);

        if (cookie != null && jwtTokenUtils.verifyToken(cookie.getValue())) {
            Authentication authentication = jwtTokenUtils.getAuthentication(cookie.getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
