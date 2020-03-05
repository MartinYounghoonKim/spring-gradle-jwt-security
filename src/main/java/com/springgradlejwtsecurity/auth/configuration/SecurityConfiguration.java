package com.springgradlejwtsecurity.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity  // 이 클래스로부터 생성된 객체가 시큐리티 설정 파일임을 의미. 동시에 시큐리티를 사용하는데 필요한 수많은 객체를 생성.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl(); // disabled caching

		http.httpBasic().disable() // REST API 이므로 기본 설정은 하지 않음. 기본 설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
			.csrf().disable() // RESTful 을 이용가히 위해 CSRF 보안 처리 disabled
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 토큰 인증이므로, 세션 생성 X
			.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
				.antMatchers("/api/auth/sign-in", "/api/auth/sign-up", "/hello").permitAll() // 누구나 허용 가능
				.antMatchers("/hello-member").hasRole("USER")
				.antMatchers("/hello-admin").hasRole("ADMIN")
				.anyRequest().authenticated() // .hasRole("ADMIN")
//			.and().addFilter(new JwtAuthenticationFilter0(jwtTokenProvider));
//				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.and().addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // jwt token 필터를 id/password 인증 필터 전에 넣어라.
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**", "/v1/api-docs", "/swagger-resources/**", "swagger-ui.html", "/webjars/**", "/swagger/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder () {
		/**
		 * @description
		 * bcrypt 해시 알고리즘을 이용하여 입력받은 데이터를 암호화한다
		 */
		return new BCryptPasswordEncoder();
	}
	//    @Bean
	//    public DaoAuthenticationProvider authenticationProvider () {
	//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	//        authenticationProvider.setUserDetailsService();
	//    }

	@Bean
	public CorsConfigurationSource corsConfigurationSource () {
		/**
		 * @description
		 * CORS 에 대한 configuration
		 *
		 */
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/*/**", corsConfiguration);

		return urlBasedCorsConfigurationSource;
	}
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler () {
		AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
		System.out.println("실패 !!!");
		return authenticationFailureHandler;
	}
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler () {
		AuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
		System.out.println("성고 !!!!");
		return authenticationSuccessHandler;
	}
}
