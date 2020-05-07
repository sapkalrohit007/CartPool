package com.cmpe275.sjsu.cartpool.model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int sku;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@Column(name = "img_url")
	private String imageUrl;
	
	@Column
	private String brand;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Unit unit;
	
	@Column(nullable = false)
	private double price;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "product_store", 
			joinColumns = @JoinColumn(name="product_id"),
			inverseJoinColumns = @JoinColumn( name="store_id")
			)
	@JsonIgnoreProperties({"product"})
	List<Store>stores;
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//	@JoinTable(
//			name = "order_product", 
//			joinColumns = @JoinColumn(name="product_id"),
//			inverseJoinColumns = @JoinColumn( name="order_id")
//			)
//	@JsonIgnoreProperties({"products"})
//	List<Orders>orders;

	//@JsonIgnoreProperties({"product"})
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	List<OrderDetails>orderDetail;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product( String name, String description, String imageUrl, String brand, Unit unit, double price) {
//		this.sku = sku;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.brand = brand;
		this.unit = unit;
		this.price = price;
	}

	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}
	
	

//	public List<Orders> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(List<Orders> orders) {
//		this.orders = orders;
//	}
	

	@Override
	public String toString() {
		return "Product [sku=" + sku + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl
				+ ", brand=" + brand + ", unit=" + unit + ", price=" + price + "]";
	}
	
	
	public List<OrderDetails> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetails> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void addStore(Store theStore, boolean addInOtherRelationship) {
		
		if(this.stores == null) {
			this.stores = new ArrayList<Store>();
		}
		
		boolean flag = false;
		int theStoreId = theStore.getId();
		
		for(Store tempStore:this.stores) {
			if(tempStore.getId() == theStoreId) {
				flag = true;
				break;
			}
		}
		
		if(!flag) {
			this.stores.add(theStore);			
		}
		
		if(addInOtherRelationship) {
			theStore.addProduct(this, false);
		}
	}
	
	public void removeStore(Store theStore, boolean removeFromOtherRelationship) {
		
		if(this.stores == null) {
			this.stores = new ArrayList<Store>();
		}
		
		Store existingStore = null;
		int theStoreId = theStore.getId();
		
		for(Store tempStore:this.stores) {
			if(tempStore.getId() == theStoreId) {
				existingStore = tempStore;
				break;
			}
		}
		
		if(existingStore!=null) {
			this.stores.remove(existingStore);
		}
		
		if(removeFromOtherRelationship) {
			existingStore.addProduct(this, false);
		}
		
	} 
	public void addOrderDetail(OrderDetails orderDetail) {
		if(this.orderDetail == null) {
			this.orderDetail = new ArrayList<OrderDetails>();
		}
		this.orderDetail.add(orderDetail);
	}
	
	
}
