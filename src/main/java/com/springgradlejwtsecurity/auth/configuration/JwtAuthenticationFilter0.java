package com.springgradlejwtsecurity.auth.configuration;

import com.springgradlejwtsecurity.auth.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtAuthenticationFilter0 extends GenericFilterBean {
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie cookie = Optional.ofNullable(WebUtils.getCookie((HttpServletRequest) request, "accessToken"))
                .orElse(null);

        if (cookie != null && jwtTokenUtils.verifyToken(cookie.getValue())) {
            Authentication authentication = jwtTokenUtils.getAuthentication(cookie.getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
