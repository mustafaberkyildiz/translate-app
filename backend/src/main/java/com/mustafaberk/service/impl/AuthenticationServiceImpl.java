package com.mustafaberk.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.mustafaberk.dto.AuthRequest;
import com.mustafaberk.dto.AuthResponse;
import com.mustafaberk.dto.DtoRefreshToken;
import com.mustafaberk.dto.DtoUser;
import com.mustafaberk.dto.RegisterRequest;
import com.mustafaberk.enums.MessageType;
import com.mustafaberk.enums.Role;
import com.mustafaberk.exception.BaseException;
import com.mustafaberk.exception.ErrorMessage;
import com.mustafaberk.jwt.JwtService;
import com.mustafaberk.model.RefreshToken;
import com.mustafaberk.model.User;
import com.mustafaberk.repository.RefreshTokenRepository;
import com.mustafaberk.repository.UserRepository;
import com.mustafaberk.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private JwtService jwtService;
	
	
	private User generateUser(RegisterRequest request) {
		
		User user = new User();
		
		BeanUtils.copyProperties(request, user);
		
		user.setPassword( passwordEncoder.encode(request.getPassword()) );
		user.setRole(Role.USER);
		
		return user;
	}
	
	private DtoUser generateDtoUser(User user) {
		
		DtoUser dtoUser = new DtoUser();
		
		BeanUtils.copyProperties(user, dtoUser);
		
		return dtoUser;
	}
	
	@Override
	public DtoUser registerUser(RegisterRequest request) {

		boolean isExist = userRepository.existsByUsername(request.getUsername());
		
		if(isExist) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXIST, null));
		}
		
		User user = generateUser(request);
		User savedUser = userRepository.save(user);
		
		return generateDtoUser(savedUser);
	}

	
	@Override
	public AuthResponse authenticate(AuthRequest request) {

		try {
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			
			authenticationProvider.authenticate(authenticationToken);
			
			User user = userRepository.findByUsername(request.getUsername())
								      .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, request.getUsername())) );
			
			RefreshToken refreshToken = generateRefreshToken(user);
			RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
			
			String accessToken = jwtService.generateAccessToken(user);
			
			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken(), user.getId());
			
		} catch (AuthenticationException ex) {
			throw new BaseException(new ErrorMessage( MessageType.USERNAME_OR_PASSWORD_INVALID , null ));
		} catch (Exception ex) {
			throw new BaseException(new ErrorMessage( MessageType.GENERAL_EXCEPTION , ex.getMessage() ));
		}

	}
	
	private RefreshToken generateRefreshToken(User user) {
		
		RefreshToken refreshToken = new RefreshToken();
		
		refreshToken.setCreateTime(LocalDateTime.now());
		refreshToken.setExpiredDate(LocalDateTime.now().plusHours(5));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setUser(user);
		
		return refreshToken;
	}

	private DtoRefreshToken generateDtoRefreshToken(RefreshToken refreshToken) {
		
		DtoRefreshToken dtoRefreshToken = new DtoRefreshToken();
		DtoUser dtoUser = new DtoUser();
		
		BeanUtils.copyProperties(refreshToken, dtoRefreshToken);
		BeanUtils.copyProperties(refreshToken.getUser(), dtoUser);
		
		dtoRefreshToken.setUser(dtoUser);
		
		return dtoRefreshToken;
	}

	@Override
	public AuthResponse refreshToken(String refreshToken) {
		
		RefreshToken basedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
							  							  .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND_DB, refreshToken)) );
		
		if (!isTokenValid(basedRefreshToken.getExpiredDate())) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, null));
		}
		
		RefreshToken newRefreshToken = generateRefreshToken(basedRefreshToken.getUser());
		newRefreshToken.setExpiredDate(basedRefreshToken.getExpiredDate()); // expireDate korunmasi icin mevcut RefreshToken'in expireDate'ini yeni RefreshToken'in expireDate'ine aktardik
		
		RefreshToken savedRefreshToken = refreshTokenRepository.save(newRefreshToken);
		String accessToken = jwtService.generateAccessToken(basedRefreshToken.getUser());
		
		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken(), savedRefreshToken.getUser().getId());
	}
	
	private boolean isTokenValid(LocalDateTime expireDateTime) { // eger expire olmadiysa true doner , expire olduysa false
		return expireDateTime.isAfter(LocalDateTime.now());
	}
	
}
