package com.cmpe275.sjsu.cartpool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.sjsu.cartpool.model.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

	Optional<Store> findByName(String name);
	
}
