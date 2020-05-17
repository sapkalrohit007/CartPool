package com.cmpe275.sjsu.cartpool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cmpe275.sjsu.cartpool.model.*;
import com.cmpe275.sjsu.cartpool.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
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

	@Autowired
	private PoolRepository poolRepository;
	
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
	public List<Orders> findMyPoolOrders(UserPrincipal currentUser) {
		Optional<User> owner = userRepository.findByEmail(currentUser.getEmail());
		List<Orders> orders = new ArrayList<>();
		Pool pool = owner.get().getPool();
		if (pool != null) {
			List<User> members = pool.getMembers();
			members.add(pool.getOwner());
			int i;
			for(i=0;i<members.size();i++)
				if(members.get(i).getId() == owner.get().getId())
					break;
			members.remove(i);
			for (User member : members) {
				orders.addAll(member.getOrders());
			}
			return orders;
		}
		throw new BadRequestException("No pool associated to the user");
	}

	@Override
	public List<Orders> getOrders(Integer orderId, String poolName) {
		if(orderId != null)
		{
			Optional<Orders> isOrder = orderRepository.findById(orderId);
			if(isOrder.isPresent())
			{
				List<Orders> orders = new ArrayList<>();
				orders.add(isOrder.get());
				return orders;
			}
			throw new BadRequestException("Invalid Order Id");
		}
		else if(poolName != null){
			Pool pool = poolRepository.finByName(poolName);
			if (pool != null){
				List<Orders> orders = new ArrayList<>();
				List<User> members = pool.getMembers();
				for(User member:members){
					orders.addAll(member.getOrders());
				}
				return orders;
			}
			throw new BadRequestException("Invalid Pool Name");
		}
		throw new BadRequestException("Not parameter specified.");
	}

	@Override
	public List<Orders> getAllOrders() {
		List<Orders> orders = orderRepository.findAll();
		return orders;
	}
}
