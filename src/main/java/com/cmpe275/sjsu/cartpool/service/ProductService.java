package com.cmpe275.sjsu.cartpool.service;

import java.util.List;

import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductRequest;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product getProductById(int productId);
    public Product addProduct(ProductRequest productRequest);
    public Product updateProduct(ProductRequest productRequest);
    public Product deleteProduct(int productId);
    public List<Product> getProductsInStore(int storeId);
    public List<Product> getProductByName(String name);
}
