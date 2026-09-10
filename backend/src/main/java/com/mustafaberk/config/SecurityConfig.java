package com.mustafaberk.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mustafaberk.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // (@PreAuthorize) çalışması için
public class SecurityConfig {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Autowired
	private AuthEntryPoint authEntryPoint;
	
	
	public static final String AUTHENTICATE = "/authenticate";
	public static final String REGISTER = "/register";
	public static final String REFRESH_TOKEN = "/refreshToken";
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		
		http
		.cors(Customizer.withDefaults())
		.csrf( csrf -> csrf.disable() )
		.authorizeHttpRequests( request -> 
			request.requestMatchers( AUTHENTICATE , REGISTER , REFRESH_TOKEN ).permitAll()
			.anyRequest().authenticated()
		)
		.exceptionHandling( exception -> exception 
			.authenticationEntryPoint(authEntryPoint)
			.accessDeniedHandler( (request,response,AccessDeniedException) -> {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,"YETKINIZ YOK");
			})
		)
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
		config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:81"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
}
