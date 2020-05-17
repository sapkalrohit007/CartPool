package com.cmpe275.sjsu.cartpool.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.ConfirmationToken;
import com.cmpe275.sjsu.cartpool.model.OrderDeliveryConfirmationToken;
import com.cmpe275.sjsu.cartpool.model.OrderDetails;
import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.OrderDeliveryConfirmationRepository;
import com.cmpe275.sjsu.cartpool.repository.OrderDetailsRepository;
import com.cmpe275.sjsu.cartpool.repository.OrderRepository;
import com.cmpe275.sjsu.cartpool.repository.PoolRepository;
import com.cmpe275.sjsu.cartpool.repository.ProductRepository;
import com.cmpe275.sjsu.cartpool.repository.StoreRepository;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;
import com.cmpe275.sjsu.cartpool.requestpojo.OrderIDRequest;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductOrder;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
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
	
	@Autowired
    private EmailSenderService emailSenderService;
	
	@Autowired
	private OrderDeliveryConfirmationRepository orderDeliveryConfirmationRepository;
	
	
	@Override
	public Orders placeOrder(int storeId, List<ProductOrder> products, UserPrincipal currentUser) {
		Optional<Store> store = storeRepository.findById(storeId);
		
		Optional<User> owner = userRepository.findByEmail(currentUser.getEmail());
		
		if(owner.get().getPool()==null) {
			throw new BadRequestException("Please join some pool to place the order...Without pool you are not allowed to place order...Thanks");
		}
		
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
		if (pool != null)
		{
			//Get pool members
			List<User> members = pool.getMembers();
			members.add(pool.getOwner());
			int i;
			//Remove the member who called the api
			for(i=0;i<members.size();i++)
				if(members.get(i).getId() == owner.get().getId())
					break;
			members.remove(i);
			//For each member get its list of PENDING orders
			for (User member : members) {
				List<Orders> userOrders = member.getOrders();
				for(Orders order : userOrders)
					if(order.getStatus() == OrderStatus.PENDING)
						orders.add(order);
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

	@Override
	public CommonMessage markUserToPickTheOrders(OrderIDRequest orderIDRequest, UserPrincipal currentUser) {
		
		Optional<User> owner = 
				userRepository.findByEmail(currentUser.getEmail());
		
		User theUser = null;
		if(owner.isPresent()) {
			theUser = owner.get();
		}else {
			throw new NotFoundException("User not found!");
		}
		
		List<Integer> orderIds = orderIDRequest.getOrderIds();
		
		Iterator iterator = orderIds.iterator();
		
		while(iterator.hasNext()) {
			
			Integer orderID = (Integer) iterator.next();
			
			Optional<Orders> theOrder = orderRepository.findById(orderID);
				
			if(theOrder.isPresent()) {
				
				theOrder.get().setStatus(OrderStatus.PICKED);
				theOrder.get().setPicker(theUser);
			}
			
			theUser.addPickUpOrder(theOrder.get());
			
		}
		
		try {
			userRepository.save(theUser);
		}catch(Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
		CommonMessage result = new CommonMessage(
				"Thank you for deciding to pick the fellow pooler orders!");
		
		return result;
		
	}

	@Override
	public List<Orders> getOrdersToBePickedByUser(UserPrincipal currentUser) {
		
		List<Orders> result = orderRepository.findByStatusAndPicker(OrderStatus.PICKED, currentUser.getId());
		
		return result;
	}

	@Override
	public List<Orders> getUserPendingOrder(UserPrincipal currentUser) {
		
		List<Orders> result = orderRepository.findByStatusAndOwner(OrderStatus.PENDING, currentUser.getId());
		
		return result;
		
	}

	@Override
	public List<Orders> getUserPickedUpOrders(UserPrincipal currentUser) {
		
		List<Orders> result = orderRepository.findByStatusAndOwner(OrderStatus.PICKED, currentUser.getId());
		
		return result;
	}

	@Override
	public List<Orders> getUserDeliveryOrders(UserPrincipal currentUser) {
		
		List<Orders> result = orderRepository.findByStatusAndOwner(OrderStatus.INDELIVERY, currentUser.getId());
		
		return result;
		
	}

	@Override
	public List<Orders> getOrdersToBeDeliverByUser(UserPrincipal currentUser) {
		List<Orders> result = orderRepository.findByStatusAndPicker(OrderStatus.INDELIVERY, currentUser.getId());
		
		return result;
	}

	public CommonMessage deliver(int orderId) {
		Optional<Orders> orderToBeDelivered = orderRepository.findById(orderId);
		if(orderToBeDelivered.get()==null) {
			throw new BadRequestException("Incorrect Order Id");
		}
		Orders order = orderToBeDelivered.get();
		order.setStatus(OrderStatus.DELIVERED);
		
		int pickerCredit = order.getPicker().getCredit();
		pickerCredit = pickerCredit + 1;
		order.getPicker().setCredit(pickerCredit);
		
		int ownersCredit = order.getOwner().getCredit();
		ownersCredit = ownersCredit -1;
		order.getOwner().setCredit(ownersCredit);
		
		orderRepository.save(order);
		
		OrderDeliveryConfirmationToken token = new OrderDeliveryConfirmationToken(order);
		
		orderDeliveryConfirmationRepository.save(token);
	
		
		//this will trigger an email to the owner of the order.
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(order.getOwner().getEmail());
        mailMessage.setSubject("Your order delivery status");
        mailMessage.setText("Your order has been delivered by "+order.getPicker().getName()+".\n"
        		+ "If you have received the order please click here : "
        +"http://localhost:8080/order/confirm-order-received?token="+token.getConfirmationToken()+
        "\n\nIf you have not received the order please click here "
        +"http://localhost:8080/order/reject-order-received?token="+token.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        
		return new CommonMessage("Thank you for delivering order....");
	}
	
	@Override
	public String confirmOrderReceived(String confirmationToken) {
		OrderDeliveryConfirmationToken token = orderDeliveryConfirmationRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {	
        	Optional<Orders> order = orderRepository.findById(token.getOrder().getId());
            if(order.isPresent()) {
            	
            	List<OrderDetails> orderDetails = order.get().getOrderDetail();
            	
            	for (OrderDetails orderDetail: orderDetails) {
            		orderDetailsRepository.delete(orderDetail);
            	}
            }
            orderDeliveryConfirmationRepository.delete(token);
            return "Thank you for confirming!!!!";
        }
        return "invalid URL";
	}

	@Override
	public String rejectOrderReceived(String confirmationToken) {
		OrderDeliveryConfirmationToken token = orderDeliveryConfirmationRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {	
        	Optional<Orders> newOrder = orderRepository.findById(token.getOrder().getId());
            if(newOrder.isPresent()) {
            	
            	Orders order = newOrder.get();
            	order.setPicker(null);
            	order.setStatus(OrderStatus.PENDING);
            	
            	
            	int pickerCredit = order.getPicker().getCredit();
        		pickerCredit = pickerCredit - 1;
        		order.getPicker().setCredit(pickerCredit);
        		
        		int ownersCredit = order.getOwner().getCredit();
        		ownersCredit = ownersCredit + 1;
        		order.getOwner().setCredit(ownersCredit);
        		
        		orderRepository.save(order);
        		
            }
            orderDeliveryConfirmationRepository.delete(token);
            return "Thank you for confirming....\nYour order will be delivered by some other pooler...\n\nWe will investigate why your order is not delivered....\n\nWe are extremely sorry for inconvinience..";
        }
        return "invalid URL";
	}
	
}
