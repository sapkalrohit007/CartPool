package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product getProductById(int productId);
    public Product addProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(int productId);
    public List<Product> getProductsInStore(int storeId);
    public List<Product> getProductByName(String name);
}
