package com.cmpe275.sjsu.cartpool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.config.EmailConfig;
import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.model.ReferenceConfirmation;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.PoolRepository;
import com.cmpe275.sjsu.cartpool.repository.ReferenceConfirmationRepository;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

@Service
public class PoolServiceImpl implements PoolService{
	@Autowired
	private PoolRepository poolRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReferenceConfirmationRepository referenceConfirmationRepository;
	
	@Autowired
    private EmailSenderService emailSenderService;
	
	@Autowired
	private EmailConfig emailConfig;
	
	@Override
	public Pool createPool(UserPrincipal currentUser, Pool pool) {
		
		Optional<User> user = userRepository.findByEmail(currentUser.getEmail());
		
		Pool usersPool = user.get().getPool();
		if(usersPool!=null) {
			throw new BadRequestException("You are already part of some pool you can not create new pool...");
		}
		usersPool = poolRepository.finByName(pool.getName()); 
		if(usersPool!=null) {
			throw new BadRequestException("Pool name already present....");
		}
		List<Pool> usersPoolList  = poolRepository.finByOwner(currentUser.getId());		
		if(usersPoolList.size() >0) {
			throw new BadRequestException("Sorry you can not create poll as you are already owner of some pool...");
		}
		
		pool.setOwner(user.get());
		pool.addMember(user.get());
		user.get().setPool(pool);
		Pool responsePool = poolRepository.save(pool);
		System.out.println(responsePool);
		return responsePool;
	}

	@Override
	public Pool deletePool(UserPrincipal currentUser) {
		List<Pool> usersPoolList  = poolRepository.finByOwner(currentUser.getId());		
		if(usersPoolList.size() ==0) {
			throw new BadRequestException("You are not pool leader of any of the pool....");
		}
		Pool pool = usersPoolList.get(0);
		if(pool.getMembers().size()!=0) {
			throw new BadRequestException("You can not delete the pool as there are members");
		}
		poolRepository.deleteById(pool.getUuid());
		return pool;
	}
	
	@Override
	public CommonMessage joinPool(UserPrincipal currentUser,String poolName, String referee ) {
		
		Optional<User> user = userRepository.findByEmail(currentUser.getEmail());
		if(user.get().getPool()!=null) {
			throw new BadRequestException("You are already part of some pool ..you can not join two pools at the same time");
		}
		
		Pool pool = poolRepository.finByName(poolName);
		if(pool==null) {
			throw new BadRequestException("Pool Name is incoreect......");
		}
		List<User> members = pool.getMembers();
		if(members.size()>3) {
			throw new BadRequestException("Pool is full you can not join the pool......");
		}
		
		ReferenceConfirmation token = new ReferenceConfirmation(user.get());
		pool.addReferenceConfirmation(token);
		token.setPool(pool);
		referenceConfirmationRepository.save(token);
		if(referee==null) {
			 SimpleMailMessage mailMessage = new SimpleMailMessage();
	         mailMessage.setTo(pool.getOwner().getEmail());
	         mailMessage.setSubject("Approve Pool Join Request for "+user.get().getName()+" in your pool!!  CartPool Website!");
	         mailMessage.setText("To confirm the request, please click here : "
	        		 +emailConfig.getUrl()+"/pool/confirm-request-owner?token="+token.getConfirmationToken()+
	         "\n\nTo reject the request, please click here : "
	         +emailConfig.getUrl()+"/pool/reject-request?token="+token.getConfirmationToken());

	         emailSenderService.sendEmail(mailMessage);

		}else {
			User refree = userRepository.findByName(referee);
			if(refree!=null && refree.getPool()!=null && refree.getPool().getName().equals(poolName)) {
				
				SimpleMailMessage mailMessage = new SimpleMailMessage();
		         mailMessage.setTo(refree.getEmail());
		         mailMessage.setSubject("Approve reference request for "+user.get().getName()+" to join pool!!! CartPool Website!");
		         mailMessage.setText("To confirm the request, please click here : "
		        		 +emailConfig.getUrl()+"/pool/confirm-request?token="+token.getConfirmationToken()+"\n\n\n"+
		         	"To reject the request, please click here : "
		         	+emailConfig.getUrl()+"/pool/reject-request?token="+token.getConfirmationToken());

		         emailSenderService.sendEmail(mailMessage);

			}else {
				throw new BadRequestException("refree is not part of the pool that you want to join...");
			}
		}
		
		return new CommonMessage("We have Successfully sent approval email...You can check out once the request is aprroved....");
	}
	
	
	@Override
	public String confirmRequestAdmin(String confirmationToken) {
		ReferenceConfirmation token = referenceConfirmationRepository.findByConfirmationToken(confirmationToken);  
        if(token != null)
        {
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            if(user.isPresent()) {
            	User currentUser = user.get();
            	Pool pool = token.getPool();
            	if(pool!=null) {
            		token.setIsConfirmed(true);
            		currentUser.setPool(pool);
            		pool.addMember(currentUser);
            		referenceConfirmationRepository.save(token);
            		userRepository.save(currentUser);
            		
            	    SimpleMailMessage mailMessage = new SimpleMailMessage();
   		            mailMessage.setTo(currentUser.getEmail());
   		            mailMessage.setSubject("Congratulations your request has been approved for your pool..");
   		            mailMessage.setText("Congratulations....Pool Owner has approved your request.....Now you can place orders.....");
   		            emailSenderService.sendEmail(mailMessage);
 
   		            referenceConfirmationRepository.delete(token);
                	return "You have successfully added user to your pool";
            	}else {
            		return "Failed to add user";
            	}
            	
            }else {
            	return "Failed to add user";
            }
        }
        return "invalid URL";
	}
	
	
	@Override
	public String confirmRequestRefree(String confirmationToken) {
		ReferenceConfirmation token = referenceConfirmationRepository.findByConfirmationToken(confirmationToken);  
		 if(token != null){
			 Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
	         if(user.isPresent()) {
	            token.setIsConfirmed(true);
	            referenceConfirmationRepository.save(token);
	            
	            SimpleMailMessage mailMessage = new SimpleMailMessage();
		        mailMessage.setTo(token.getPool().getOwner().getEmail());
		        mailMessage.setSubject("Approve Pool Join Request for "+token.getUser().getName()+" in your pool!!  CartPool Website!");
		        mailMessage.setText("To confirm the request, please click here : "
		        		+emailConfig.getUrl()+"/pool/confirm-request-owner?token="+token.getConfirmationToken()+
		        "\n\nTo reject the request, please click here : "
		        +emailConfig.getUrl()+"/pool/reject-request?token="+token.getConfirmationToken());

		         emailSenderService.sendEmail(mailMessage);
	            
	            return "You have approved the request";
	         }else {
	            return "Failed to add user";
	          }
	            
	      }
		 return "invalid URL";
	}

	@Override
	public String rejectRequestRefree(String confirmationToken) {
		ReferenceConfirmation token = referenceConfirmationRepository.findByConfirmationToken(confirmationToken); 
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(token.getUser().getEmail());
        mailMessage.setSubject("Update on your request to join pool!!!! CartPool Website!");
        mailMessage.setText("We are extremely sorry your request has been rejected to join the pool...Please try to join another pool..");
        emailSenderService.sendEmail(mailMessage);	
		referenceConfirmationRepository.delete(token);
		return null;
	}

	@Override
	public CommonMessage leaveGroup(UserPrincipal userPrincipal) {
		Optional<User> user = userRepository.findByEmail(userPrincipal.getEmail());
		if(user.isPresent()) {
			User currentUser = user.get();
			Pool pool = currentUser.getPool();
			if(pool != null) {
				pool.removeMember(currentUser);
				currentUser.setPool(null);
				userRepository.save(currentUser);
				return new CommonMessage("You have successfully left the group");
			}else{
				throw new BadRequestException("You are not part of any group!!!!");
			}
		}
		throw new BadRequestException("failed to leave group!!!!");
	}

	@Override
	public List<Pool> getPool(String poolName,String neighborhoodName, String zip)
	{
		if(poolName != null) {
			List<Pool> pools = new ArrayList<>();
			pools.add(poolRepository.readPoolByName(poolName));
			return pools;
		}
		if(neighborhoodName != null)
		{
			return poolRepository.readPoolsByNeighbourhoodContains(neighborhoodName);
		}
		if(zip != null)
		{
			return poolRepository.readPoolsByZipcode(zip);
		}
		throw new BadRequestException("Missing or Invalid parameters");
	}

	@Override
	public List<Pool> getAllPool() {
		return poolRepository.findAll();
	}
	
	@Override
	public Pool getMyPool(UserPrincipal userPrincipal) {
		Optional<User> user = userRepository.findByEmail(userPrincipal.getEmail());
		if(user.get().getPool()!=null) {
			return user.get().getPool();
		}
		throw new BadRequestException("You are not part of any pool yet!!! Please join some pool");
	}		 
}
