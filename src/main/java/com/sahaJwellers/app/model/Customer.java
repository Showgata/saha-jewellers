package com.sahaJwellers.app.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="customer_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class Customer {
	
	/*
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	@Column(name="customerId")
	private Long customerId;*/
	
	@Id	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customerId")
	private Long customerId;
	
	
	
	@Column(name="customerName")
	private String customerName;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="address")
	private String address;
	
	@Column(name="ref")
	private String references;
	
	@Column(name="note")
	private String note;
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	private Date updateTimestamp;
	
	@Version
	@Column(name="version")
	private Long version;

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", mobile=" + mobile
				+ ", Address=" + address + ", references=" + references +" note= "+note+"]";
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
/*
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/

	

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
