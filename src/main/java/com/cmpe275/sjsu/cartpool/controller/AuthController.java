package com.cmpe275.sjsu.cartpool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.cmpe275.sjsu.cartpool.requestpojo.LoginRequest;
import com.cmpe275.sjsu.cartpool.responsepojo.JWTResponse;
import com.cmpe275.sjsu.cartpool.security.TokenProvider;

@RestController
@RequestMapping("/auth") 
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@PostMapping("/login")
	public JWTResponse login(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = tokenProvider.createToken(authentication);
		
		JWTResponse response = new JWTResponse(token);
		
		return response;
	}
}
