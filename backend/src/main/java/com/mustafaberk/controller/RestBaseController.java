package com.mustafaberk.controller;

public class RestBaseController {

	public <T> RootEntity<T> ok(T payload){
		return RootEntity.ok(payload);
	}
	
	public RootEntity<?> error(String message){
		return RootEntity.error(message);
	}
	
}
