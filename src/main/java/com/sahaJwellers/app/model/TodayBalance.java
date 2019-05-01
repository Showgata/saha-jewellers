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
@Table(name="todaybalance_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class TodayBalance {
	
	/*
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	@Column(name="customerId")
	private Long customerId;*/
	
	@Id	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cashIn")
	private Long cashin;
	
	@Column(name="id")
	private Long id;
	
	@Column(name="cashOut")
	private Long cashout;
	
	@Column(name="balance")
	private Long balance;
	
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	private Date updateTimestamp;
	

	@Override
	public String toString() {
		return "Balance [cashIn=" + cashin + ", cashOut=" + cashout + ", balance=" + balance +"]";
	}


	public Long getCashin() {
		return cashin;
	}


	public void setCashin(Long cashin) {
		this.cashin = cashin;
	}


	public Long getCashout() {
		return cashout;
	}


	public void setCashout(Long cashout) {
		this.cashout = cashout;
	}


	public Long getBalance() {
		return balance;
	}


	public void setBalance(Long balance) {
		this.balance = balance;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


}
