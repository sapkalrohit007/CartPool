package com.cmpe275.sjsu.cartpool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.model.OrderDetails;
import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.OrderDetailsRepository;
import com.cmpe275.sjsu.cartpool.repository.OrderRepository;
import com.cmpe275.sjsu.cartpool.repository.ProductRepository;
import com.cmpe275.sjsu.cartpool.repository.StoreRepository;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrder;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Override
	public Orders placeOrder(int storeId, List<ProductOrder> products, UserPrincipal currentUser) {
		Optional<Store> store = storeRepository.findById(storeId);
		
		Optional<User> owner = userRepository.findByEmail(currentUser.getEmail());
		
		if(!store.isPresent()) {
			throw new BadRequestException("Store with given Id is not present");
		}
		
		Orders order = new Orders(OrderStatus.PENDING);
		order.setOwner(owner.get());
		owner.get().addOrder(order);
		
		
		order.setStore(store.get());
		store.get().addOrder(order);
		
		order = orderRepository.save(order);
		
		for(ProductOrder productOrder : products ) {
			int productId = productOrder.getProductId();
			Optional<Product> product = productRepository.findById(productId);
			if(product.isPresent()) {
				
				Optional<Orders> newOrder = orderRepository.findById(order.getId());
				
				OrderDetails orderDetail = new OrderDetails();
				orderDetail.setQuantity(productOrder.getQuantity());
				
				orderDetail.setOrder(newOrder.get());
				newOrder.get().addOrderDetail(orderDetail);
				
				orderDetail.setProduct(product.get());
				product.get().addOrderDetail(orderDetail);
				
				orderDetailsRepository.save(orderDetail);
				
			}
			
		}		
		return orderRepository.findById(order.getId()).get();
	}

	@Override
	public Orders updateStatus(Integer orderId,OrderStatus status)
	{
		if(status != null) {
			Optional<Orders> isOrder = orderRepository.findById(orderId);
			if (isOrder.isPresent()) {
				isOrder.get().setStatus(status);
				Orders order = orderRepository.save(isOrder.get());
				return order;
			}
			throw new BadRequestException("Invalid Order Id");
		}
		throw new BadRequestException("Orderstatus is null");
	}

	@Override
	public List<Orders> getAllOrders() {
		List<Orders> orders = orderRepository.findAll();
		return orders;
	}
}
