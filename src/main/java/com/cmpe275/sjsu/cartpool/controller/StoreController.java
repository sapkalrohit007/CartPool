package com.cmpe275.sjsu.cartpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.service.StoreService;

@RestController
@RequestMapping("/store")
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Store getStore(@PathVariable int storeId) {
		
		Store theStore = storeService.getStore(storeId);
		
		return theStore;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Store createStore(@RequestBody Store theStore) {
		
		Store resultStore = storeService.createStore(theStore);
		
		return resultStore;
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Store updateStore(@RequestBody Store theStore) {
		
		Store resultStore = storeService.updateStore(theStore);
		
		return resultStore;
	}
	
	
	@DeleteMapping("/{storeId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Store deleteStore(@PathVariable int storeId) {
		
		return storeService.deleteStore(storeId);
		
	}
	
	@GetMapping
	public List<Store> getAllStores(){
		
		List<Store> result =  storeService.getAllStores();
		
		return result;
	}
	
	
	@GetMapping("/search")
	public List<Store> searchStore(
			@RequestParam(value="name",required=true) String name
			){
		
		List<Store> result =  storeService.searchStore(name);
		
		return result;
		
	}
	
//	@PutMapping("/{storeId}/product")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public Store updateProducts(
//			@PathVariable("storeId") int storeId,
//			@RequestBody Store newStore) {
//		
//		Store existingStore = storeService.getStore(storeId);
//		
//		//remove the prev products
//		List<Product> exisingStoreProducts = existingStore.getProduct();
//		for(Product tempProduct : exisingStoreProducts) {
//			tempProduct.removeStore(existingStore, true);	
//		}
//		
//		//add the new products
//		List<Product> newStoreProducts = newStore.getProduct();
//		for(Product tempProduct : newStoreProducts) {
//			tempProduct.addStore(newStore, true);	
//		}
//		
//		Store result = storeService.updateStore(newStore);
//		
//		return result;
//	}
	
}
