package com.cmpe275.sjsu.cartpool.requestpojo;

public class ProductOrder {
	private int productId;
	private int quantity;
	
	public ProductOrder() {
		// TODO Auto-generated constructor stub
	}
	public ProductOrder(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
