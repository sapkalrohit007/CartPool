package com.cmpe275.sjsu.cartpool.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe275.sjsu.cartpool.model.Pool;

public interface PoolRepository extends JpaRepository<Pool, String>{
	
	@Query("SELECT p FROM Pool p WHERE p.owner.id= ?1")
    List<Pool> finByOwner(long owner);
	
	@Query("SELECT p FROM Pool p WHERE p.name = ?1")
	Pool finByName(String name);

	Pool readPoolByName(String name);
	List<Pool> readPoolsByNeighbourhoodContains(String neighborhood);
	List<Pool> readPoolsByZipcode(String zip);
}
