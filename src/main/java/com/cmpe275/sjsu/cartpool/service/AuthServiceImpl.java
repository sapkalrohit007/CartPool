package com.cmpe275.sjsu.cartpool.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.responsepojo.JWTResponse;
import com.cmpe275.sjsu.cartpool.security.TokenProvider;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

@Service
public class AuthServiceImpl implements AuthService{
	

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public JWTResponse login(String email, String password) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = tokenProvider.createToken(authentication);
		
		 UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		 
		JWTResponse response = new JWTResponse(token,userPrincipal.getRole());
		
		return response;
	}

}
