package com.mustafaberk.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoRefreshToken extends DtoBaseEntity{

	private LocalDateTime expiredDate;
	
	private String refreshToken;
	
	private DtoUser user;
	
}
