package com.sahaJwellers.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="account_tbl")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accountId;
	
	@Column(name="account_name")
	private String accountName;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="loan_amount")
	private Double amount;
	
	@Column(name="interest_rate")
	private Double interestRate;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="account_creation_date")
	@CreationTimestamp
	private Date createdDate;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="account_last_updation_date")
	@UpdateTimestamp
	private Date updateDate;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	
	
}
