package com.mustafaberk.service;

import com.mustafaberk.dto.AuthRequest;
import com.mustafaberk.dto.AuthResponse;
import com.mustafaberk.dto.DtoUser;
import com.mustafaberk.dto.RegisterRequest;

public interface IAuthenticationService {

	public DtoUser registerUser(RegisterRequest request);
	
	public AuthResponse authenticate(AuthRequest request);
	
	public AuthResponse refreshToken(String refreshToken);
}
