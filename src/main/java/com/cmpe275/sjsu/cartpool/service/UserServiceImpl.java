package com.cmpe275.sjsu.cartpool.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
