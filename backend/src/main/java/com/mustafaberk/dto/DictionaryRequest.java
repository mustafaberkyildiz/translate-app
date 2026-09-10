package com.mustafaberk.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryRequest {

	@NotEmpty
	private String word;
	
	@NotEmpty
	private String mean;
	
	@NotEmpty
	private String sentence;
		
}
