package com.cmpe275.sjsu.cartpool.responsepojo;

public class JWTResponse {
	String webToken;
	
	public JWTResponse(String token) {
		this.webToken = token;
	}

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}
	
}
