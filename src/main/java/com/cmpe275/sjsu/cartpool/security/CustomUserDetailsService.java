package com.cmpe275.sjsu.cartpool.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		 Optional<User> user = userRepository.findByEmail(email);
		 if(user.isPresent()) {
			 if(!user.get().getEmailVerified()) {
				 throw new BadRequestException("Please verify your email ID First!! Check your Email..");
			 }
			 return UserPrincipal.create(user.get());
		 }else {
			 throw new UsernameNotFoundException("User with given Email not found");
		 }
	}

}
