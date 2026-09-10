package com.mustafaberk.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mustafaberk.enums.MessageType;
import com.mustafaberk.exception.BaseException;
import com.mustafaberk.exception.ErrorMessage;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		
		if (header==null || header.trim().isEmpty()) { // header'in Authorization kismi null ise token gonderilmemistir 
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.substring(7);
		
		try {
			
			String username = jwtService.getUsernameByToken(token);
			
			if ( username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if ( userDetails!=null && jwtService.isTokenValid(token) ) {
					
					UsernamePasswordAuthenticationToken authenticationToken = 
							new UsernamePasswordAuthenticationToken(username, userDetails, userDetails.getAuthorities());
					
					authenticationToken.setDetails(userDetails);
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
				}
				
			}
			
		} catch (UsernameNotFoundException ex) {
			
			// userDetailsService.loadUserByUsername(username); BURDA FIRLATILAN UsernameNotFoundException HATASINI BU CATCH BLOGUNDA YAKALIYORUM
			// loadUserByUsername METODU ICINDE FIRLATILAN .orElseThrow( () -> new UsernameNotFoundException("KULLANICI BULUNAMADI : "+username) );
			// BURADA UsernameNotFoundException nesnesi oluşturuluyor ve constructor'a verdiğin metin exception'ın message alanına kaydediliyor.
			System.out.println(ex.getMessage()); // çıktı: KULLANICI BULUNAMADI : mustafa
		
		} catch (ExpiredJwtException ex) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED , null));
		} catch (Exception ex) {
			System.out.println( "Filter Katmanında Yakalandi : "+ ex.getMessage() );
		}
		
		filterChain.doFilter(request, response); // controller'a yonlendiriyoruz
				
	}

}
