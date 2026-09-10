package com.mustafaberk.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotEmpty(message = "KULLANICI ADI BOS BIRAKILAMAZ !")
	private String username;
	
	@NotEmpty(message = "SIFRE BOS BIRAKILAMAZ !")
	private String password;
	
	@NotEmpty(message = "ISIM BOS BIRAKILAMAZ !")
	private String name;
	
	@NotEmpty(message = "SOYISIM BOS BIRAKILAMAZ !")
	private String surname;
	
}
