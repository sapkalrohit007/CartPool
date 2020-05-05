package com.cmpe275.sjsu.cartpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmpe275.sjsu.cartpool.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>  {	
}
