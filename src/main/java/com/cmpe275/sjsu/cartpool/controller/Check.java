package com.cmpe275.sjsu.cartpool.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins = "*")
public class Check {

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/hello")
	public String getMessage(@CurrentUser UserPrincipal userPrincipal){
		return "Hello "+userPrincipal.getEmail();
	}
	
	@PostMapping("/checkdata")
	@PreAuthorize("hasRole('ADMIN')")
	public String checkData(@RequestBody JsonNode obj) {
		System.out.println(obj.get("mob").getClass().getName());
		
		return "success";
	}
}
