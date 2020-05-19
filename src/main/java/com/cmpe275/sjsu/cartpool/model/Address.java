package com.cmpe275.sjsu.cartpool.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
	
	@Column(name = "street")
	@NotNull
	@Size(min=1)
	private String street;
	
	
	@Column(name = "city")
	@NotNull
	@Size(min=1)
	private String city;
	
	@Column(name = "state")
	@NotNull
	@Size(min=1)
	private String state;
	
	@Column(name = "zip")
	@NotNull
	@Size(min=1)
	private String zip;
	
	public Address() {
		
	}
	
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(street!=null)
			this.street = street.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city!=null)
			this.city = city.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if(state!=null)
			this.state = state.trim();
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		if(zip!=null)
			this.zip = zip.trim();
	}

	@Override
	public String toString() {
		return street + ", " + city + ", " + state + ", " + zip;
	}
}
