package com.mustafaberk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary extends BaseEntity{

	private String word;
	
	private String mean;
	
	@Column(columnDefinition = "TEXT") // CUMLE UZUN OLABILIR ONDAN DOLAYI TEXT YAPTIK
	private String sentence;
	
	@ManyToOne
	private User user;
	
}
