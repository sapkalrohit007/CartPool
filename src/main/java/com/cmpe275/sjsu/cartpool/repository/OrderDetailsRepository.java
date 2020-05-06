package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.sjsu.cartpool.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>{

}
