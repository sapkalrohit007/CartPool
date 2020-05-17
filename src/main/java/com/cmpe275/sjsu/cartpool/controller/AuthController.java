package com.cmpe275.sjsu.cartpool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.requestpojo.LoginRequest;
import com.cmpe275.sjsu.cartpool.responsepojo.JWTResponse;
import com.cmpe275.sjsu.cartpool.service.AuthService;

@RestController
@RequestMapping("/auth") 
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private AuthService AuthService;
	
	@PostMapping("/login")
	public JWTResponse login(@RequestBody LoginRequest loginRequest) {
		
		return AuthService.login(loginRequest.getEmail(), loginRequest.getPassword());
		
	}
}
