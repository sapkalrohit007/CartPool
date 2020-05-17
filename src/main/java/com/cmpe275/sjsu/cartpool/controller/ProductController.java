package com.cmpe275.sjsu.cartpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductRequest;
import com.cmpe275.sjsu.cartpool.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController
{
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public List<Product> getProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/sku={sku}")
    public Product getProductBySku(@PathVariable int sku)
    {
        return productService.getProductById(sku);
    }

    @GetMapping("/storeid={storeId}")
    public List<Product> getProductByStoreId(@PathVariable int storeId)
    {
        return productService.getProductsInStore(storeId);
    }

    @GetMapping("/name={productName}")
    public List<Product> getProductByName(@PathVariable String productName)
    {
        return productService.getProductByName(productName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product addProduct(@RequestBody ProductRequest productRequest)
    {
        return productService.addProduct(productRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Product updateProduct(@RequestBody ProductRequest productRequest)
    {
        return productService.updateProduct(productRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sku}")
    public Product deleteProduct(@PathVariable int sku)
    {
        return productService.deleteProduct(sku);
    }
}
