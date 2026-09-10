package com.mustafaberk.handler;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception<E> {

	private String hostName;
	
	private String path;
	
	private LocalDateTime createTime;
	
	private E message;
	
}
