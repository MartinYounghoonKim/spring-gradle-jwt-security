package com.springgradlejwtsecurity.auth.configuration;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Getter
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.setUsernameParameter("userId");
		this.setFilterProcessesUrl("/api/v1/auth/verify");
		this.setPostOnly(true);
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported : " + request.getMethod());
		}
		String userId = Optional.ofNullable(obtainUsername(request)).orElse("").trim();
		String password = Optional.ofNullable(obtainPassword(request)).orElse("").trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws
		IOException, ServletException {
		System.out.println(" ========== ");
		System.out.println(" 실패했드아ㅏㅏㅏㅏ ");
		System.out.println(" ========== ");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "teastasdf");
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		System.out.println(" ========== ");
		System.out.println(" 성공했드아ㅏㅏㅏㅏ ");
		System.out.println(" ========== ");
	}
}
