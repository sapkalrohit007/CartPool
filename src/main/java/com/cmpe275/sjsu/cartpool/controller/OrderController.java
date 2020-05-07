package com.cmpe275.sjsu.cartpool.controller;

import com.cmpe275.sjsu.cartpool.requestpojo.OrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrderRequest;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.cmpe275.sjsu.cartpool.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order") 
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
	@GetMapping
	public List<Orders> getAll(){
		return orderService.getAllOrders();
	}
}
