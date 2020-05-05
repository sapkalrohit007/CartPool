package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.responsepojo.JWTResponse;

public interface AuthService {

	public JWTResponse login(String email, String password);
	
}
