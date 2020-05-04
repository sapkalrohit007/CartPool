package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.repository.CrudRepository;

import com.cmpe275.sjsu.cartpool.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
