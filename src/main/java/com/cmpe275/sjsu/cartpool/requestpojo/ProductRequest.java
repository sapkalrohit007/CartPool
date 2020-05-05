package com.cmpe275.sjsu.cartpool.requestpojo;

import java.util.List;

public class ProductRequest {
	
	private int sku;

	private String name;
	
	private String description;
	
	private String imageUrl;
	
	private String brand;
	
	private String unit;
	
	private double price;
	
	private List<Integer> stores;
	
	public ProductRequest() {
		
	}

	public ProductRequest(String name, String description, String imageUrl, String brand, String unit, double price) {
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.brand = brand;
		this.unit = unit;
		this.price = price;
	}

	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Integer> getStores() {
		return stores;
	}

	public void setStores(List<Integer> stores) {
		this.stores = stores;
	}
	
	
}
