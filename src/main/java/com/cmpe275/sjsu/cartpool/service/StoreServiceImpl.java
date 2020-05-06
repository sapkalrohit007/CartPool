package com.cmpe275.sjsu.cartpool.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.cmpe275.sjsu.cartpool.error.AlreadyExistsException;
import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public Store createStore(Store theStore){
		
		try {
		
			Optional<Store> existingStore = storeRepository.findByName(theStore.getName());
			
			
			
			if(existingStore.isPresent()) {
				throw new AlreadyExistsException("Store with same name already in use");
			}
			
			theStore.setId(0);
		
			Store result = storeRepository.save(theStore);
			return result;
		}catch(ConstraintViolationException e) {
			throw new ConstraintViolationException("Invalid request - bad input parameters", null);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Invalid request - missing input parameters");
		}catch(TransactionSystemException e) {
			throw new TransactionSystemException("Invalid request - bad parameters");
		}catch(NullPointerException e) {
			throw new BadRequestException("Invalid request - bad input parameters");
		}catch(Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
	}

	@Override
	public Store updateStore(Store theStore) {
		
//		System.out.println(theStore.getId());
//		System.out.println(theStore.getName());
//		System.out.println(theStore.getAddress());
		
		try {			
			Optional<Store> existingStoreById = storeRepository.findById(theStore.getId());
			
			if(!existingStoreById.isPresent()) {
				throw new NotFoundException("Store not found");
			}
						
			Optional<Store> exisingStoreByName = storeRepository.findByName(theStore.getName());

			
			if(exisingStoreByName.isPresent() && exisingStoreByName.get().getId() != theStore.getId()) {
				throw new BadRequestException("Store with same name already present");
			}
			
			existingStoreById.get().setAddress(theStore.getAddress());
			
			existingStoreById.get().setName(theStore.getName());
			
			Store result = storeRepository.save(existingStoreById.get());
			return result;
			
		}catch(ConstraintViolationException e) {
			throw new BadRequestException("Invalid request - bad input parameters", null);
		}catch(DataIntegrityViolationException e) {
			throw new BadRequestException("Invalid request - missing input parameters");
		}catch(TransactionSystemException e) {
			throw new BadRequestException("Invalid request - bad parameters");
		}catch(NullPointerException e) {
			throw new BadRequestException("Invalid request - bad input parameters");
		}catch(Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}

	}

	@Override
	public Store getStore(int storeId) {
		
		Optional<Store> result = storeRepository.findById(storeId);
		
		if(result.isPresent()) {	
			return result.get();
		}else {
			throw new NotFoundException("Store not found");
		}
		
	}

	
	@Override
	public Store deleteStore(int storeId) {
		
		Optional<Store> existingStore = storeRepository.findById(storeId);
		
		if(!existingStore.isPresent()) {
			throw new NotFoundException("Store not found");
		}else {
			try {
				Store theStore = existingStore.get();
				
				List<Product> products = theStore.getProduct();
				
				for(Product theProduct : products) {
					theProduct.removeStore(theStore);
				}
				
				storeRepository.delete(theStore);
				return theStore;
			}catch(Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}
			
		}
		
	}

	@Override
	public List<Store> getAllStores() {
		
		List<Store> result = storeRepository.findAll();
		
		return result;
	}

	@Override
	public List<Store> searchStore(String name) {
		
		List<Store> result = storeRepository.searchStore(name.toLowerCase());
		
		return result;
		
	}

	
	
	
}
