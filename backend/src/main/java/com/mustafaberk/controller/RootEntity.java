package com.mustafaberk.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value =  Include.NON_NULL)
public class RootEntity<T> {

	private Integer status;
		
	private String errorMessage;
	
	private T payload;
	
	// buradaki <T> parametre olarak  tipinde generic bir ifade alicam demis olduk
	public static <T> RootEntity<T> ok(T payload){
		
		return RootEntity.<T>builder()
				.status(200)
				.errorMessage(null)
				.payload(payload)
				.build();
	}
	
	// buradaki <T> parametre olarak  tipinde generic bir ifade alicam demis olduk
	public static <T> RootEntity<T> error(String message){
		
		return RootEntity.<T>builder()
				.status(500)
				.errorMessage(message)
				.payload(null)
				.build();
	}
	
}
