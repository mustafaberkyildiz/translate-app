package com.mustafaberk.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBaseEntity {

	private Long id;
	
	private LocalDateTime createTime;
	
	private LocalDateTime updateTime;
	
}
