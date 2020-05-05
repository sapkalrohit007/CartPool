package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.sjsu.cartpool.model.ReferenceConfirmation;

public interface ReferenceConfirmationRepository  extends JpaRepository<ReferenceConfirmation, Integer>{
	
	ReferenceConfirmation findByConfirmationToken(String confirmationToken);
}
