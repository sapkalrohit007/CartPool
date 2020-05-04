package com.cmpe275.sjsu.cartpool.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Pool {
	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2", strategy = "uuid2")
	@Column(name = "uuid",columnDefinition = "BINARY(16)")
	private String uuid;
	
	@Column(nullable = false, unique = true)
	private String name; 
	
	@Column(nullable = false)
	private String neighbourhood;

	@Column
	private String description;
	
	@Column(updatable = false, nullable = false)
	@Length(max = 5, min = 5)
	private String zipcode;
	
	@OneToMany( mappedBy = "pool")
	@JsonIgnoreProperties({"pool"})
	private List<User> users;
	
	@OneToMany( mappedBy = "pool")
	@JsonIgnoreProperties({"pool"})
	private List<ReferenceConfirmation> referenceConfirmation;
	
	public Pool() {
		// TODO Auto-generated constructor stub
	}

	public Pool(String uuid, String name, String neighbourhood, String description,
			@Length(max = 5, min = 5) String zipcode) {
		this.uuid = uuid;
		this.name = name;
		this.neighbourhood = neighbourhood;
		this.description = description;
		this.zipcode = zipcode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "Pool [uuid=" + uuid + ", name=" + name + ", neighbourhood=" + neighbourhood + ", description="
				+ description + ", zipcode=" + zipcode + "]";
	}
}
