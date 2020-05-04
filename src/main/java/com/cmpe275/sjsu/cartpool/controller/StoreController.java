package com.cmpe275.sjsu.cartpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.service.StoreService;

@RestController
@RequestMapping("/store")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StoreController {
	
	/*Exceptions thrown by this class
	 * NotFoundException - not present store id
	 * ConstraintViolationException - for valid address fields and empty fields
	 * DataIntegrityViolationException - for absent name
	 * TransactionSystemException - PUT missing parameters
	 * */
	
	@Autowired
	private StoreService storeService;
	
	@GetMapping("/{storeId}")
	public Store getStore(@PathVariable int storeId) {
		
		Store theStore = storeService.getStore(storeId);
		
		return theStore;
	}
	
	@PostMapping
	public Store createStore(@RequestBody Store theStore) {
		
		Store resultStore = storeService.createStore(theStore);
		
		return resultStore;
	}
	
	@PutMapping
	public Store updateStore(@RequestBody Store theStore) {
		
		Store resultStore = storeService.updateStore(theStore);
		
		return resultStore;
	}
	
	
	@DeleteMapping("/{storeId}")
	public Store deleteStore(@PathVariable int storeId) {
		
		return storeService.deleteStore(storeId);
		
	}
}
