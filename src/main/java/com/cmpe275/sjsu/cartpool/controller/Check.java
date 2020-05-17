package com.cmpe275.sjsu.cartpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.config.EmailConfig;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins = "*")
public class Check {

	@Autowired
	private EmailConfig emailConfig;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/hello")
	public String getMessage(@CurrentUser UserPrincipal userPrincipal){
		return "Hello "+userPrincipal.getEmail();
	}
	
//	@PostMapping("/checkdata")
//	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/checkdata")
	public String checkData() {
		String url = emailConfig.getUrl();
		return url;
	}
}
