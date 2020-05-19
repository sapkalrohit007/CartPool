package com.cmpe275.sjsu.cartpool.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductRequest;
import com.cmpe275.sjsu.cartpool.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

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

    @Deprecated
    @ApiOperation("API to add product without the support for image upload. This API is no longer used. Use /product/add")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add",consumes = {"multipart/form-data"})
    public Product addProductWithImage(@RequestPart(name = "product", required = true) String productJson,
                                       @RequestPart(name = "file", required = true)MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest product = mapper.readValue(productJson,ProductRequest.class);
        return productService.addProductWithImage(product,file);
    }
}
