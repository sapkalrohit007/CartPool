package com.cmpe275.sjsu.cartpool.service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(int productId)
    {
        Optional<Product> isProduct = productRepository.findById(productId);
        if(isProduct.isPresent())
            return isProduct.get();
        else
            throw new NotFoundException("Product Not found");
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> isProduct = productRepository.findById(product.getSku());
        if(isProduct.isPresent())
            return productRepository.save(product);
        else
            throw new NotFoundException("Product Not Found");
    }

    @Override
    public Product deleteProduct(int productId) {
        Optional<Product> isProduct = productRepository.findById(productId);
        if (isProduct.isPresent())
        {
            Product product = isProduct.get();
            List<Orders> orders = product.getOrders();
            if(orders.isEmpty())
            {
                productRepository.deleteById(productId);
                return product;
            }
            else
            {
                throw new BadRequestException("Cannot delete product. Orders with the product still pending.");
            }
        }
        else
            throw new NotFoundException("Product Not Found");
    }

    @Override
    public List<Product> getProductsInStore(int storeId) {
        return productRepository.readProductsByStores(storeId);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.readProductsByName(name);
    }
}
