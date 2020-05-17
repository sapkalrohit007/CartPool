package com.cmpe275.sjsu.cartpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.requestpojo.OrderIDRequest;
import com.cmpe275.sjsu.cartpool.requestpojo.OrderStatusRequest;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrderRequest;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.cmpe275.sjsu.cartpool.service.OrderService;

@RestController
@RequestMapping("/order") 
@CrossOrigin(origins = "*")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeorder")
	public Orders placeOrder(@RequestBody ProductOrderRequest request, @CurrentUser UserPrincipal currentUser) {
		return orderService.placeOrder(request.getStorId(), request.getProducts(), currentUser); 
	}

	@PatchMapping("/status")
	public Orders updateOrderStatus(@RequestBody OrderStatusRequest request)
	{
		return orderService.updateStatus(request.getId(),request.getOrderStatus());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public List<Orders> getAll(){
		return orderService.getAllOrders();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<Orders> getOrders(@RequestParam(name="orderid",required = false) Integer orderId,
							   @RequestParam(name = "poolname",required = false) String poolName){
		return orderService.getOrders(orderId,poolName);
	}
	
	@PreAuthorize("hasRole('POOLER')")
	@PostMapping("/pick_by")
	public CommonMessage ordersPickedBy(
			@RequestBody OrderIDRequest orderIDRequest,
			@CurrentUser UserPrincipal currentUser) {
		
		return orderService.ordersPickedBy(orderIDRequest, currentUser);
		
	}
	
	
	
}
