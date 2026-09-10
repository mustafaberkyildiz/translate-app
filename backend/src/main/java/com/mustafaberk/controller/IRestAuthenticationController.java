package com.mustafaberk.controller;

import com.mustafaberk.dto.AuthRequest;
import com.mustafaberk.dto.AuthResponse;
import com.mustafaberk.dto.DtoUser;
import com.mustafaberk.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface IRestAuthenticationController {

	public RootEntity<DtoUser> registerUser(RegisterRequest request);
	
	public RootEntity<AuthResponse> authenticate(AuthRequest request,HttpServletResponse response);
	
	public RootEntity<AuthResponse> refreshToken(String refreshToken,HttpServletResponse response);
	
	public RootEntity<String> logoutFromCookie(HttpServletResponse response);
} 
