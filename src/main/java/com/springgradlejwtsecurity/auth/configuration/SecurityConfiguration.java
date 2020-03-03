package com.springgradlejwtsecurity.auth.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity  // 이 클래스로부터 생성된 객체가 시큐리티 설정 파일임을 의미. 동시에 시큐리티를 사용하는데 필요한 수많은 객체를 생성.
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final JwtTokenProvider jwtTokenProvider;
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl(); // disabled caching

		http.httpBasic().disable() // REST API 이므로 기본 설정은 하지 않음. 기본 설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
			.csrf().disable() // CSRF 보안 처리 disabled
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 토큰 인증이므로, 세션 생성 X
			.and().authorizeRequests()
				.antMatchers("/hello").permitAll()
				.antMatchers("/sign-in", "/sign-up").permitAll()
				.antMatchers("/hello-admin").hasRole("admin")
				.antMatchers("/hello-member").hasRole("member")
				.anyRequest().authenticated() // .hasRole("ADMIN")
//			.and().addFilter(new JwtAuthenticationFilter0(jwtTokenProvider));
//				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.and().addFilterBefore(new JwtAuthenticationFilter0(jwtTokenProvider), JwtAuthenticationFilter.class);

//			.and().addFilter();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**", "/v1/api-docs", "/swagger-resources/**", "swagger-ui.html", "/webjars/**", "/swagger/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		// auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		//        auth.inMemoryAuthentication()
		//                .withUser("admin")
		//                .password("password")
		//                .roles("ADMIN");
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
