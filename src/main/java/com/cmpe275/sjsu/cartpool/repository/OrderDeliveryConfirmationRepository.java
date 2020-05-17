package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.repository.CrudRepository;
import com.cmpe275.sjsu.cartpool.model.OrderDeliveryConfirmationToken;

public interface OrderDeliveryConfirmationRepository extends CrudRepository<OrderDeliveryConfirmationToken, Integer> {
	OrderDeliveryConfirmationToken findByConfirmationToken(String confirmationToken);
}
