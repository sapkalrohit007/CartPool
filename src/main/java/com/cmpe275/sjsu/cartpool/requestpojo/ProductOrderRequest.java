package com.cmpe275.sjsu.cartpool.requestpojo;

import java.util.List;

public class ProductOrderRequest {
	private int storId;
	private List<ProductOrder>products;
	
	public ProductOrderRequest() {
		// TODO Auto-generated constructor stub
	}

	public ProductOrderRequest(int storId, List<ProductOrder> products) {
		this.storId = storId;
		this.products = products;
	}

	public int getStorId() {
		return storId;
	}

	public void setStorId(int storId) {
		this.storId = storId;
	}

	public List<ProductOrder> getProducts() {
		return products;
	}

	public void setProducts(List<ProductOrder> products) {
		this.products = products;
	}
	
}
