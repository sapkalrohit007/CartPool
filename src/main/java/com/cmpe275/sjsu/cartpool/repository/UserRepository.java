package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.sjsu.cartpool.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    Boolean existsByEmail(String email);

}
