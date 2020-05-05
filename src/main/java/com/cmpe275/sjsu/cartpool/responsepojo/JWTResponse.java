package com.cmpe275.sjsu.cartpool.responsepojo;

public class JWTResponse {
	String webToken;
	String role;
	
	public JWTResponse() {
		// TODO Auto-generated constructor stub
	}
	

	public JWTResponse(String webToken, String role) {
		this.webToken = webToken;
		this.role = role;
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}
	
}
