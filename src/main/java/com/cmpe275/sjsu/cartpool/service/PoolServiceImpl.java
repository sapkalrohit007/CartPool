package com.cmpe275.sjsu.cartpool.service;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.jni.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.model.User;
import com.cmpe275.sjsu.cartpool.repository.PoolRepository;
import com.cmpe275.sjsu.cartpool.repository.UserRepository;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

@Service
public class PoolServiceImpl implements PoolService{
	@Autowired
	private PoolRepository poolRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
		
		Pool responsePool = poolRepository.save(pool);
		
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
	public Pool joinPool(UserPrincipal currentUser,String poolName, String referee ) {
		Pool pool = poolRepository.finByName(poolName);
		List<User> members = pool.getMembers();
		if(members.size()>3) {
			
		}
		return new Pool();
	}
	
}
