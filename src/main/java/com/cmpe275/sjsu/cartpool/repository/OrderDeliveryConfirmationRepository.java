package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.cmpe275.sjsu.cartpool.model.OrderDeliveryConfirmationToken;

public interface OrderDeliveryConfirmationRepository extends JpaRepository<OrderDeliveryConfirmationToken, Long>{
	OrderDeliveryConfirmationToken findByConfirmationToken(String confirmationToken);
}
