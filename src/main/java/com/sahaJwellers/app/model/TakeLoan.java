package com.sahaJwellers.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="take_loan_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class TakeLoan {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="loan_id")
	private Long loanId;

	@Column(name="account_name")
	private String accountName;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="bank_list")
	private String bankList;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="remainder_date")
	private  Date remainderDate;
		
	
	@Column(name="interest_rate")
	private Double interestRate;
	
	@Column(name="loan_Amt")
	private Double loanAmount;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private  Date createdDate;
	
	@Column(name="customerId")
	private long customerId;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval=false)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	
	public TakeLoan() {super();}
	

	@JsonCreator
	public TakeLoan(@JsonProperty("loanId") Long loanId,@JsonProperty("accountName") String accountName,@JsonProperty("currency") String currency,
			@JsonProperty("bankList") String bankList,@JsonProperty("remainderDate") Date remainderDate,
			@JsonProperty("interestRate") Double interestRate,@JsonProperty("loanAmount") Double loanAmount,
			@JsonProperty("customerId") long customerId,@JsonProperty("createdDate") Date createdDate,@JsonProperty("customer")Customer customer) {
		super();
		this.loanId = loanId;
		this.accountName = accountName;
		this.currency = currency;
		this.bankList = bankList;
		this.remainderDate = remainderDate;
		this.interestRate = interestRate;
		this.loanAmount = loanAmount;
		this.customerId = customerId;
		this.createdDate=createdDate;
		this.customer=customer;
	}

	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Override
	public String toString() {
		return "TakeLoan [loanId=" + loanId + ", accountName=" + accountName + ", currency=" + currency + ", bankList="
				+ bankList + ", remainderDate=" + remainderDate + ", interestRate=" + interestRate + ", loanAmount="
				+ loanAmount + ", customerId=" + customerId + "]";
	}

	public Long getLoanId() {
		return loanId;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getCurrency() {
		return currency;
	}

	public String getBankList() {
		return bankList;
	}

	public Date getRemainderDate() {
		return remainderDate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setBankList(String bankList) {
		this.bankList = bankList;
	}

	public void setRemainderDate(Date remainderDate) {
		this.remainderDate = remainderDate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	

	/*
	@JsonCreator
	public TakeLoan(@JsonProperty("mortgageId") Long mortgageId,@JsonProperty("bori") Double bori,@JsonProperty("ana") Double ana,
			@JsonProperty("ratti") Double ratti,@JsonProperty("point") Double point,@JsonProperty("gram") Double gram,
			@JsonProperty("interestRate") Double interestRate,@JsonProperty("loanAmount") Double loanAmount) {
		super();
		
		
		this.mortgageId = mortgageId;
		this.bori = bori;
		this.ana = ana;
		this.ratti = ratti;
		this.point = point;
		this.gram = gram;
		this.interestRate = interestRate;
		this.loanAmount = loanAmount;
	}*/

	

	
}
