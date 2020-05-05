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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Product> getProducts()
    {
        return productService.getAllProducts();
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
    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable int productId)
    {
        return productService.deleteProduct(productId);
    }
}
