package com.cmpe275.sjsu.cartpool.requestpojo;

public class RegisterUserRequest {
	private String email;
	private String name;
	private String password;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String nickName;
	
	public RegisterUserRequest() {
		// TODO Auto-generated constructor stub
	}

	



	public RegisterUserRequest(String email, String name, String password, String street, String city, String state,
			String zip, String nickName) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.nickName = nickName;
	}





	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
