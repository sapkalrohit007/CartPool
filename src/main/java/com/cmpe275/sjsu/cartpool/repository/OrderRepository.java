package com.cmpe275.sjsu.cartpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe275.sjsu.cartpool.model.OrderStatus;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.User;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

	@Query("SELECT o from Orders o where o.status=?1 and o.picker.id=?2 ORDER BY o.createdDate")
	List<Orders> findByStatusAndPicker(OrderStatus status,Long picker);
	
	@Query("SELECT o from Orders o where o.status=?1 and o.owner.id=?2 ORDER BY o.createdDate")
	List<Orders> findByStatusAndOwner(OrderStatus status,Long owner);
	
	@Query("SELECT o from Orders o where o.status=?1 and o.owner.id=?2 and o.picker.id=?3 ORDER BY o.createdDate")
	List<Orders> findByStatusAndOwnerAndPicker(OrderStatus status,Long owner,Long picker);
}
