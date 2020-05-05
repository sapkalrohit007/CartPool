package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;

public interface PoolService {
	public Pool createPool(UserPrincipal currentUser, Pool pool);
	public Pool deletePool(UserPrincipal currentUser);
	public Pool joinPool(UserPrincipal currentUser,String poolName, String referee );
}
