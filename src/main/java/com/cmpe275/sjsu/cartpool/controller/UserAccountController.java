package com.cmpe275.sjsu.cartpool.controller;


import com.cmpe275.sjsu.cartpool.responsepojo.BooleanResponse;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
       System.out.println(confirmationToken);
       return userService.confirmUserAccount(confirmationToken);
    }

    @GetMapping("/isleader")
    @PreAuthorize("hasRole('POOLER')")
    public BooleanResponse isPoolLeader(@CurrentUser UserPrincipal currentUser)
    {
        return new BooleanResponse(userService.checkIfPoolLeader(currentUser));
    }
    
}