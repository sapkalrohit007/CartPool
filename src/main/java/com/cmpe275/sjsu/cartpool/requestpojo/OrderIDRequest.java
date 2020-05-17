package com.cmpe275.sjsu.cartpool.requestpojo;

import java.util.List;

public class OrderIDRequest {

	private List<Integer> orderIds;
	
	public OrderIDRequest() {
		
	}

	public OrderIDRequest(List<Integer> orderIds) {
		this.orderIds = orderIds;
	}

	public List<Integer> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<Integer> orderIds) {
		this.orderIds = orderIds;
	}

	
}
