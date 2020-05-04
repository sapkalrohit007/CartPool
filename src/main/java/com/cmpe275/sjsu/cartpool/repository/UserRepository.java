package com.cmpe275.sjsu.cartpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe275.sjsu.cartpool.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.nickName = ?1")
    User finByNickName(String nickName);
    
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User findByName(String name);
    
    Boolean existsByEmail(String email);

}
