package com.mustafaberk.controller.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mustafaberk.controller.IRestAuthenticationController;
import com.mustafaberk.controller.RestBaseController;
import com.mustafaberk.controller.RootEntity;
import com.mustafaberk.dto.AuthRequest;
import com.mustafaberk.dto.AuthResponse;
import com.mustafaberk.dto.DtoUser;
import com.mustafaberk.dto.RegisterRequest;
import com.mustafaberk.enums.MessageType;
import com.mustafaberk.exception.BaseException;
import com.mustafaberk.exception.ErrorMessage;
import com.mustafaberk.model.RefreshToken;
import com.mustafaberk.repository.RefreshTokenRepository;
import com.mustafaberk.service.IAuthenticationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController{

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	
	@PostMapping("/register")
	@Override
	public RootEntity<DtoUser> registerUser(@RequestBody @Valid RegisterRequest request) {
		return ok(authenticationService.registerUser(request));
	}

	@PostMapping("/authenticate")
	@Override
	public RootEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest request,HttpServletResponse response) {

		AuthResponse authResponse = authenticationService.authenticate(request);
		
		long seconds = 5 * 60 * 60 ;// 5 saat tutucaz cookie'de cunku refrehToken expire olma suresi de 5 saat
		
		ResponseCookie cookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
											  .httpOnly(true)
											  .secure(false)
											  .maxAge(seconds)
											  .path("/")
											  .sameSite("Lax")
											  .build();
		
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		
		authResponse.setRefreshToken(null);
		
		return ok(authResponse);
	}

	@PostMapping("/refreshToken")
	@Override
	public RootEntity<AuthResponse> refreshToken(@CookieValue(name = "refreshToken",required = false) String refreshToken, HttpServletResponse response) {

		if (refreshToken==null) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND_COOKIE, null));
		}
		
		AuthResponse authResponse = authenticationService.refreshToken(refreshToken);
		RefreshToken newRefreshToken = refreshTokenRepository.findByRefreshToken(authResponse.getRefreshToken())
															 .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND_DB, null)) );
		
		long seconds = Duration.between(LocalDateTime.now(), newRefreshToken.getExpiredDate()).getSeconds();
		if (seconds <= 0) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, null));
		}
		
		ResponseCookie cookie = ResponseCookie.fromClientResponse("refreshToken", authResponse.getRefreshToken())
											  .httpOnly(true)
											  .secure(false)
											  .maxAge(seconds)
											  .path("/")
											  .sameSite("Lax")
											  .build();
		
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		
		authResponse.setRefreshToken(null);
		
		return ok(authResponse);
	}

	@PostMapping("/logoutFromCookie")
	@Override
	public RootEntity<String> logoutFromCookie(HttpServletResponse response) {
		
		ResponseCookie deleteCookie = ResponseCookie.from("refreshToken","")
													.httpOnly(true)
													.secure(false)
													.maxAge(0)
													.sameSite("Lax")
													.path("/")
													.build();
		
		response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
		
		return ok("Cikis Yapildi !");
	}

	
	
}
