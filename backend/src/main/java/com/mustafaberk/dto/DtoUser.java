package com.mustafaberk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser extends DtoBaseEntity{
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String surname;
	
}
