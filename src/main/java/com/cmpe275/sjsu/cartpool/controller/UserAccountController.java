package com.cmpe275.sjsu.cartpool.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.error.AlreadyExistsException;
import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.model.Address;
import com.cmpe275.sjsu.cartpool.model.AuthProvider;
import com.cmpe275.sjsu.cartpool.model.ConfirmationToken;
import com.cmpe275.sjsu.cartpool.model.Role;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.ConfirmationTokenRepository;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;
import com.cmpe275.sjsu.cartpool.requestpojo.RegisterUserRequest;
import com.cmpe275.sjsu.cartpool.service.EmailSenderService;

@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    
    private BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
 

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception
    {
        Optional<User> existingUser = userRepository.findByEmail(registerUserRequest.getEmail());
        User existingUserWithGivenName = userRepository.findByName(registerUserRequest.getName());
        User existingUserWithGivenNickName = userRepository.finByNickName(registerUserRequest.getNickName());
        
        if(existingUserWithGivenName != null) {
        	throw new BadRequestException("Name is already used...");
        }
        if(existingUserWithGivenNickName != null) {
        	throw new BadRequestException("Nick Name is already used..");
        }
        if(existingUser.isPresent())
        {
            throw new AlreadyExistsException("This email ID is already used!!!!");
        }
        else
        {
        	User user = new User();
        	user.setEmail(registerUserRequest.getEmail());
        	user.setEmailVerified(false);
        	user.setName(registerUserRequest.getName());
        	user.setNickName(registerUserRequest.getNickName());
        	Address address = new Address();
        	address.setCity(registerUserRequest.getCity());
        	address.setState(registerUserRequest.getState());
        	address.setStreet(registerUserRequest.getStreet());
        	address.setZip(registerUserRequest.getZip());
        	user.setAddress(address);
        	String encryptedPassword = bcryptPasswordEncoder.encode(registerUserRequest.getPassword());
        	user.setPassword(encryptedPassword);
        	String email = registerUserRequest.getEmail();
        	String checkString = email.substring(email.lastIndexOf("@")+1);
        	if(checkString.equals("sjsu.edu")) {
        		user.setRole(Role.ADMIN);
        	}else {
        		user.setRole(Role.POOLER);
        	}
        	
        	user.setProvider(AuthProvider.local);
            userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Verify Email for CartPool Website!");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/user/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            return user;
        }
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(token);
        if(token != null)
        {
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            if(user.isPresent()) {
            	User verfiedUser = user.get();
            	verfiedUser.setEmailVerified(true);
            	userRepository.save(verfiedUser);
            }
            
            return "Account verfied Successfully";
        }
        return "invalid URL";
    }
}