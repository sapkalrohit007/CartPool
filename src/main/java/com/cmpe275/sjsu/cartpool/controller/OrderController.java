package com.cmpe275.sjsu.cartpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@GetMapping("/pool")
	public List<Orders> getPoolOrders(@CurrentUser UserPrincipal currentUser)
	{
		return orderService.findMyPoolOrders(currentUser);
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
	@PostMapping("/user/will/pickup")
	public CommonMessage markUserToPickTheOrders(
			@RequestBody OrderIDRequest orderIDRequest,
			@CurrentUser UserPrincipal currentUser) {
		
		return orderService.markUserToPickTheOrders(orderIDRequest, currentUser);
		
	}
	
	
	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/user/placed")
	public List<Orders> getUserPendingOrder(@CurrentUser UserPrincipal currentUser){
		
		return orderService.getUserPendingOrder(currentUser);
	}
	
	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/user/of/pick-up")
	public List<Orders> getUserPickedUpOrders(@CurrentUser UserPrincipal currentUser){
		
		return orderService.getUserPickedUpOrders(currentUser);
	}
	
	
	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/user/to/pick-up")
	public List<Orders> getOrdersToBePickedByUser(@CurrentUser UserPrincipal currentUser){
		
		return orderService.getOrdersToBePickedByUser(currentUser);
		
	}
	
	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/user/of/deliver")
	public List<Orders> getUserDeliveryOrders(@CurrentUser UserPrincipal currentUser){
		
		return orderService.getUserDeliveryOrders(currentUser);
	}
	
	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/user/to/deliver")
	public List<Orders> getOrdersToBeDeliverByUser(@CurrentUser UserPrincipal currentUser){
		
		return orderService.getOrdersToBeDeliverByUser(currentUser);
	}

	@PreAuthorize("hasRole('POOLER')")
	@GetMapping("/deliver/{orderId}")
	public CommonMessage deliver(@PathVariable int orderId) {
		return orderService.deliver(orderId);
	}
	
	@RequestMapping(value="/confirm-order-received", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmOrderReceiver(@RequestParam("token")String confirmationToken) {
		return orderService.confirmOrderReceived(confirmationToken);
	}
	
	@RequestMapping(value="/reject-order-received", method= {RequestMethod.GET, RequestMethod.POST})
	public String rejectOrderReceiver(@RequestParam("token")String confirmationToken) {
		return orderService.rejectOrderReceived(confirmationToken);
	}
	
}
