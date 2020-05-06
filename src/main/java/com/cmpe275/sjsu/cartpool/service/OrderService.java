package com.cmpe275.sjsu.cartpool.service;

import java.util.List;

import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrder;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

public interface OrderService {
	public Orders placeOrder(int storeId,List<ProductOrder>products,UserPrincipal userPrinciple);
}
