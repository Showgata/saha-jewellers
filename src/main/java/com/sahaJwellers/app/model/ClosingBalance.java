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
@Table(name="closingbalance_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class ClosingBalance {
	

	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="cbalance")
	private Long cbalance;
	
	@Column(name="date")
	private String date;
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	private Date updateTimestamp;
	

	@Override
	public String toString() {
		return "CBalance [Id=" + id + ", cbalance=" + cbalance + ", date=" + date +"]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCbalance() {
		return cbalance;
	}


	public void setCbalance(Long cbalance) {
		this.cbalance = cbalance;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	}
