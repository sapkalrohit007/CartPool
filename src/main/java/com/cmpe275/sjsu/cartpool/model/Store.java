package com.cmpe275.sjsu.cartpool.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(nullable = false, unique = true)
	@Size(min=1)
	private String name;
	
	@NotNull
	@Valid
	@Embedded
	private Address address;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "product_store", 
			joinColumns = @JoinColumn( name="store_id"),
			inverseJoinColumns = @JoinColumn(name="product_id")
			)
	@JsonIgnoreProperties({"stores"})
	List<Product>product;
	public Store() {
		// TODO Auto-generated constructor stub
	}
	
	@OneToMany(mappedBy = "store")
	@JsonIgnoreProperties({"store"})
	List<Orders>orders;
	
	public Store(int id, String name, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.trim();
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	public void addProduct(Product theProduct, boolean addInOtherRelationship) {
		
		if(this.product == null) {
			this.product = new ArrayList<Product>();
		}
		
		boolean flag = false;
		int theProductSKU = theProduct.getSku();
		
		for(Product tempProduct:this.product) {
			if(tempProduct.getSku() == theProductSKU) {
				flag = true;
				break;
			}
		}
		
		if(!flag) {
			this.product.add(theProduct);			
		}
		
		if(addInOtherRelationship) {
			theProduct.addStore(this, false);
		}
		
	}
	
	
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public void removeProduct(Product theProduct, boolean removeFromOtherRelationship) {
		
		if(this.product == null) {
			this.product = new ArrayList<Product>();
		}
		
		Product existingProduct = null;
		int theProductSKU = theProduct.getSku();
		
		for(Product tempProduct:this.product) {
			if(tempProduct.getSku() == theProductSKU) {
				existingProduct = tempProduct;
				break;
			}
		}
		
		if(existingProduct!=null) {
			this.product.remove(existingProduct);
		}
		
		if(removeFromOtherRelationship) {
			existingProduct.removeStore(this, false);
		}
		
	}
	
	public void addOrder(Orders order) {
		if(this.orders == null) {
			this.orders = new ArrayList<Orders>();
		}
		this.orders.add(order);
	}
	
}
