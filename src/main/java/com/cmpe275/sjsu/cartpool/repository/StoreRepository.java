package com.cmpe275.sjsu.cartpool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmpe275.sjsu.cartpool.model.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

	Optional<Store> findByName(String name);
	
	
	@Query(value = "SELECT * FROM STORE s WHERE s.name LIKE CONCAT('%',:name,'%')",nativeQuery = true)
	List<Store> searchStore(@Param("name") String name);
	
}
