package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

import java.util.List;

public interface PoolService {
	public Pool createPool(UserPrincipal currentUser, Pool pool);
	public Pool deletePool(UserPrincipal currentUser);
	public CommonMessage joinPool(UserPrincipal currentUser,String poolName, String referee );
	public String confirmRequestAdmin(String token);
	public String confirmRequestRefree(String confirmationToken);
	public String rejectRequestRefree(String confirmationToken);
	public CommonMessage leaveGroup(UserPrincipal currentUser);
	public List<Pool> getPool(String poolName, String neighborhoodName, String zipcode);
	public List<Pool> getAllPool();
}
