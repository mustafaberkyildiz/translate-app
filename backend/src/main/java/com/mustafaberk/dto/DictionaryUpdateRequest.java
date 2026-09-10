package com.mustafaberk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryUpdateRequest {

	private String word;
	
	private String mean;
	
	private String sentence;
	
}
