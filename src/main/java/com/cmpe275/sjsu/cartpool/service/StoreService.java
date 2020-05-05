package com.cmpe275.sjsu.cartpool.service;

import java.util.List;

import com.cmpe275.sjsu.cartpool.model.Store;

public interface StoreService {

	public Store createStore(Store theStore);
	
	public Store updateStore(Store theStore);
	
	public Store getStore(int storeId);
	
	public Store deleteStore(int storeId);
	
	public List<Store> getAllStores();
	
	public List<Store> searchStore(String name);
	
}
