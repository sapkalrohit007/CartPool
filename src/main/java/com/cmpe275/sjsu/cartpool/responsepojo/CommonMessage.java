package com.cmpe275.sjsu.cartpool.responsepojo;

import org.springframework.http.HttpStatus;

public class CommonMessage {
	
	private String message;
	private int status;
	
	public CommonMessage() {
		// TODO Auto-generated constructor stub
	}

	public CommonMessage(String message) {
		this.message = message;
		this.status=HttpStatus.OK.value();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
