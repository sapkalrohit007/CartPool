package com.cmpe275.sjsu.cartpool.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( name = "created_date")
    private Date createdDate;
	
	
	@Column( nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	
	@ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	@JoinColumn(name = "store_id")
	@JsonIgnoreProperties({"orders"})
	private Store store;
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//	@JoinTable(
//			name = "order_product", 
//			joinColumns = @JoinColumn(name="order_id"),
//			inverseJoinColumns = @JoinColumn( name="product_id")
//			)	
//	@JsonIgnoreProperties({"orders"})
//	List<Product>products;
	
	
	@OneToMany(mappedBy = "order")
	@JsonIgnoreProperties({"order"})
	List<OrderDetails>orderDetail;


	@ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
    @JoinColumn(name = "picked_by")
	@JsonIgnoreProperties({"pickupOrders","orders","pool"})
	private User picker;
	
	@ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	@JoinColumn(name = "owner")
	@JsonIgnoreProperties({"orders"})
	private User owner; 
	
	public Orders() {
		// TODO Auto-generated constructor stub
	}

	public Orders(OrderStatus status) {
		this.createdDate = new Date();
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	
//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}

	
	public User getPicker() {
		return picker;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<OrderDetails> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetails> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setPicker(User picker) {
		this.picker = picker;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", createdDate=" + createdDate + ", status=" + status + "]";
	}
	
	public void addOrderDetail(OrderDetails orderDetail) {
		if(this.orderDetail == null) {
			this.orderDetail = new ArrayList<OrderDetails>();
		}
		this.orderDetail.add(orderDetail);
	}
}
