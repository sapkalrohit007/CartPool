package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.model.Store;

public interface StoreService {

	public Store createStore(Store theStore);
	
	public Store updateStore(Store theStore);
	
	public Store getStore(int storeId);
	
	public Store deleteStore(int storeId);
	
}
