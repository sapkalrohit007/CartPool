package com.cmpe275.sjsu.cartpool.controller;

import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
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
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Product updateProduct(@RequestBody Product product)
    {
        return productService.updateProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sku}")
    public Product deleteProduct(@PathVariable int sku)
    {
        return productService.deleteProduct(sku);
    }
}
