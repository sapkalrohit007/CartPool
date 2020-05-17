package com.cmpe275.sjsu.cartpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.User;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

	@Query("SELECT o from Orders o where o.status=?1 and o.picker.id=?2")
	List<Orders> getOrdersToBePickedByUser(OrderStatus picked,Long user);
	
}
