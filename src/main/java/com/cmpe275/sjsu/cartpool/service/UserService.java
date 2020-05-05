package com.cmpe275.sjsu.cartpool.service;

import java.util.Optional;

import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.requestpojo.RegisterUserRequest;

public interface UserService {
	public Optional<User> getUserByEmail(String email);
	public User registerUser(RegisterUserRequest registerUserRequest);
	public String confirmUserAccount(String confirmationToken);
}
