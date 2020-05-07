package com.cmpe275.sjsu.cartpool.service;

import java.util.Optional;

import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.requestpojo.RegisterUserRequest;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

public interface UserService {
	public Optional<User> getUserByEmail(String email);
	public User registerUser(RegisterUserRequest registerUserRequest);
	public String confirmUserAccount(String confirmationToken);
	public boolean checkIfPoolLeader(UserPrincipal user);
}
