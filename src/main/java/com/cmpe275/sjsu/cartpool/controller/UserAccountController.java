package com.cmpe275.sjsu.cartpool.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.requestpojo.RegisterUserRequest;
import com.cmpe275.sjsu.cartpool.service.UserService;

@RestController
@RequestMapping("/user")
public class UserAccountController {


	@Autowired
	private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterUserRequest registerUserRequest)
    {
        return userService.registerUser(registerUserRequest);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
       return userService.confirmUserAccount(confirmationToken);
    }
    
}