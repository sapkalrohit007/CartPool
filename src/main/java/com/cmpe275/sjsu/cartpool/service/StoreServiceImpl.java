package com.cmpe275.sjsu.cartpool.service;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.cmpe275.sjsu.cartpool.error.AlreadyExistsException;
import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public Store createStore(Store theStore){
		
		Optional<Store> existingStore = storeRepository.findByName(theStore.getName());
		
		if(existingStore.isPresent()) {
			throw new AlreadyExistsException("Store with same name already in use");
		}
		
		theStore.setId(0);
		
		try {
			Store result = storeRepository.save(theStore);
			return result;
		}catch(ConstraintViolationException e) {
			throw new ConstraintViolationException("Invalid request - bad input parameters", null);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Invalid request - bad input parameters");
		}catch(Exception e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
		
	}

	@Override
	public Store updateStore(Store theStore) {
		
		try {			
			Optional<Store> existingStore = storeRepository.findById(theStore.getId());
			
			if(!existingStore.isPresent()) {
				throw new NotFoundException("Store not found");
			}
			
			Store result = storeRepository.save(theStore);
			return result;
			
		}catch(TransactionSystemException e) {
			throw new TransactionSystemException("Invalid request - missing parameters");
		}
		catch(Exception e) {
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
				storeRepository.delete(theStore);
				return theStore;
			}catch(Exception e) {
				throw new RuntimeException(e.fillInStackTrace());
			}
			
		}
		
	}

	
	
}
