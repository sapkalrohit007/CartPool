package com.cmpe275.sjsu.cartpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cmpe275.sjsu.cartpool.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>  {

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.name LIKE CONCAT('%',:name,'%')",nativeQuery = true)
    List<Product> searchProduct(@Param("name") String name);

    List<Product> readProductsByStores(int storeId);
    List<Product> readProductsByName(String name);

}
