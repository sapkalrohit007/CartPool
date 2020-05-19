package com.cmpe275.sjsu.cartpool.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(name = "nick_name")
    private String nickName;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false, name = "email_verified")
    
    private Boolean emailVerified;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private AuthProvider provider;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @Column(name = "provider_id")
    @JsonIgnore
    private String providerId;
    
//    @Valid
    @Embedded
    private Address address;
    
    @OneToMany( mappedBy = "owner")
    @JsonIgnoreProperties({"owner"})
    private List<Orders>orders;

    @OneToMany( mappedBy = "picker")
    @JsonIgnoreProperties({"picker"})
    private List<Orders>pickupOrders;

    @ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	@JoinColumn(name = "pool_id")
    @JsonIgnoreProperties({"members"})
    private Pool pool;
    
    @Column(name = "credit")
    private int credit;

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	public void addOrder(Orders order) {
		if(this.orders==null) {
			this.orders=new ArrayList<Orders>();
		}
		this.orders.add(order);
	}
	
	public void addPickUpOrder(Orders order) {
		if(this.pickupOrders == null) {
			this.pickupOrders = new ArrayList<Orders>();
		}
		this.pickupOrders.add(order);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", nickName=" + nickName + ", email=" + email + ", emailVerified=" + emailVerified
				+ ", password=" + password + ", provider=" + provider + ", role=" + role + ", providerId=" + providerId
				+ ", address=" + address + ", credit=" + credit + "]";
	}
	
    
}
