package com.cmpe275.sjsu.cartpool.service;

import java.util.List;

import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.requestpojo.OrderIDRequest;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrder;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

public interface OrderService {
	public Orders placeOrder(int storeId,List<ProductOrder>products,UserPrincipal userPrinciple);
	public Orders updateStatus(Integer orderId, OrderStatus status);
	public List<Orders> getAllOrders();
	public List<Orders> getOrders(Integer orderId,String poolName);
	public List<Orders> findMyPoolOrders(UserPrincipal currentUser);
	public CommonMessage ordersPickedBy(OrderIDRequest orderIDRequest,  UserPrincipal currentUser);
	public List<Orders> ordersToBePickedByUser(UserPrincipal currentUser);
}
