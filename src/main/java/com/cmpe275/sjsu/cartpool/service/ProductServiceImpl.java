package com.cmpe275.sjsu.cartpool.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;
import com.cmpe275.sjsu.cartpool.error.NotFoundException;
import com.cmpe275.sjsu.cartpool.model.Orders;
import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;
import com.cmpe275.sjsu.cartpool.model.Unit;
import com.cmpe275.sjsu.cartpool.repository.ProductRepository;
import com.cmpe275.sjsu.cartpool.repository.StoreRepository;
import com.cmpe275.sjsu.cartpool.requestpojo.ProductRequest;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(ProductRequest productRequest) {
    	
    	String name = productRequest.getName().trim();
    	String unitType = productRequest.getUnit().trim();
    	
    	if(name == null || (name != null && name == "")) {
    		throw new BadRequestException("Invalid name parameter");
    	}
    	
    	if(unitType == null || (unitType != null && unitType == "")) {
    		throw new BadRequestException("Invalid unit parameter");
    	}
    	
    	String description = productRequest.getDescription();
    	String imageUrl = productRequest.getImageUrl();
    	String brand = productRequest.getBrand();
    	Double price = productRequest.getPrice();
       	
    	Unit unit = Unit.valueOf(unitType);
    	
    	List<Integer> storeIds = productRequest.getStores();
    	
    	Product theProduct = new Product(name,description,imageUrl,brand,unit,price);
    	
    	for(Integer storeId : storeIds) {
    		
    		Optional<Store> theStore = storeRepository.findById(storeId);
    		
    		if(theStore.isPresent()) {
    			theProduct.addStore(theStore.get(), true);
    		}
    	}
    	
    	try {
    		return productRepository.save(theProduct);
    	}catch(BadRequestException e) {
    		throw new BadRequestException("Invalid input parameters");
    	}
        
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
    public Product updateProduct(ProductRequest productRequest) {
        Optional<Product> isProduct = productRepository.findById(productRequest.getSku());
        if(isProduct.isPresent()) {
        	
        	String name = productRequest.getName().trim();
        	String unitType = productRequest.getUnit().trim();
        	
        	if(name == null || (name != null && name == "")) {
        		throw new BadRequestException("Invalid name parameter");
        	}
        	
        	if(unitType == null || (unitType != null && unitType == "")) {
        		throw new BadRequestException("Invalid unit parameter");
        	}
        	
        	String description = productRequest.getDescription();
        	String imageUrl = productRequest.getImageUrl();
        	String brand = productRequest.getBrand();
        	Double price = productRequest.getPrice();
           	
        	Unit unit = Unit.valueOf(unitType);
        	
        	Product existingProduct = isProduct.get();
        	
        	List<Store> stores = updatedStores(existingProduct,productRequest.getStores());
        	
        	Product theProduct = new Product(name,description,imageUrl,brand,unit,price);
        	theProduct.setSku(productRequest.getSku());
        	theProduct.setStores(stores);
        	
        	try {
        		return productRepository.save(theProduct);
        	}catch(BadRequestException e) {
        		throw new BadRequestException("Invalid input parameters");
        	}
        	
        }else
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
            	List<Store> stores = product.getStores();
            	
            	for(Store theStore : stores) {
            		theStore.removeProduct(product, false);
            	}
            	
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
    
    private List<Store> updatedStores(Product existingProduct, List<Integer> storeIdsRequest){
    	
    	List<Store> existingStores = existingProduct.getStores();
    	Set<Integer> storeIdsRequestSet = new HashSet<Integer>(storeIdsRequest);
    	Set<Integer> existingStoreIds = new HashSet<Integer>();
    	List<Store> removeStoresFromProduct = new ArrayList<Store>();
    	
    	Iterator<Store> iterator = existingStores.iterator();
    	
    	while(iterator.hasNext()) {
    		Store theStore = iterator.next();
    		
    		if(!storeIdsRequestSet.contains(theStore.getId())) {
    			removeStoresFromProduct.add(theStore);
    		}else {
    			existingStoreIds.add(theStore.getId());
    		}
    		
    	}
    	
    	for(Store theStore : removeStoresFromProduct) {
    		existingProduct.removeStore(theStore, true);
    	}
    	
    	
    	for(Integer storeId : storeIdsRequestSet) {
    		
    		if(!existingStoreIds.contains(storeId)) {
    			Optional<Store> theStore = storeRepository.findById(storeId);
        		
        		if(theStore.isPresent()) {
        			existingProduct.addStore(theStore.get(), true);
        		}
    		}
    		
    	}
    	
    	return existingProduct.getStores();
    }
}
