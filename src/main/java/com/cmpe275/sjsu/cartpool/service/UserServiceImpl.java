package com.cmpe275.sjsu.cartpool.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class UserServiceImpl implements UserService{
	

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    
    private BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User registerUser(RegisterUserRequest registerUserRequest) {
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

	@Override
	public String confirmUserAccount(String confirmationToken) {
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
