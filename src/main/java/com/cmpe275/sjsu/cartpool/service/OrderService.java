package com.cmpe275.sjsu.cartpool.service;

import java.util.List;

import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.requestpojo.OrderIDRequest;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrder;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

public interface OrderService {
	public Orders placeOrder(int storeId,List<ProductOrder>products,UserPrincipal userPrinciple);
	public Orders updateStatus(Integer orderId, OrderStatus status);
	public List<Orders> getAllOrders();
	public List<Orders> getOrders(Integer orderId,String poolName);
	public List<Orders> findMyPoolOrders(UserPrincipal currentUser);
	public CommonMessage markUserToPickTheOrders(OrderIDRequest orderIDRequest,  UserPrincipal currentUser);
	public List<Orders> getUserPendingOrder(UserPrincipal currentUser);
	public List<Orders> getUserPickedUpOrders(UserPrincipal currentUser);
	public List<Orders> getOrdersToBePickedByUser(UserPrincipal currentUser);
	public List<Orders> getUserDeliveryOrders(UserPrincipal currentUser);
	public List<Orders> getOrdersToBeDeliverByUser(UserPrincipal currentUser);
	public CommonMessage deliver(int orderId);
	public String confirmOrderReceived(String token);
	public String rejectOrderReceived(String confirmationToken);
	public Orders setOrderInDelivery(Integer orderId);
	public void sendCheckoutMailToOwner(Orders order);
	public void sendCheckoutMailToPicker(Orders order);
}
