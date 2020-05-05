package com.cmpe275.sjsu.cartpool.repository;

import com.cmpe275.sjsu.cartpool.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>  {
}
