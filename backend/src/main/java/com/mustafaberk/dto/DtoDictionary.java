package com.mustafaberk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoDictionary extends DtoBaseEntity{

	private String word;
	
	private String mean;
	
	private String sentence;
	
	private DtoUser user;
}
