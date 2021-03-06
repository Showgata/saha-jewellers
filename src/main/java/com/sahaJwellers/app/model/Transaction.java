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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="transaction_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class Transaction {
	
	@JsonCreator
	public Transaction(@JsonProperty("id") Long id,@JsonProperty("reminderDate") Date reminderDate,@JsonProperty("storage") @Range(min = 1, max = 10) Integer storage, @JsonProperty("note") String note,
			@JsonProperty("transactionDate") Date transactionDate, @JsonProperty("transactionSerial") String transactionSerial,@JsonProperty("customerId")Long customerId) {
		super();
		this.id = id;
		this.reminderDate = reminderDate;
		this.storage = storage;
		this.note = note;
		this.transactionDate = transactionDate;
		this.transactionSerial = transactionSerial;
		this.customerId=customerId;
		
	}

	public Transaction() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Long id;
	
	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="address")
	private String address;

/*	
	@Version
	@Column(name="version")
	private Long version;*/
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="reminder_date")
	@Temporal(TemporalType.DATE)
	private Date reminderDate;
	
	@Range(min=1,max=10)
	@Column(name="storage")
	private Integer storage;
	
	@Column(name="note")
	private String note;
	
	//======================================================================
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="creation_timestamp")
	@UpdateTimestamp
	private Date creationTimestamp;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="transaction_date")
	private Date transactionDate;
	
	
	//======================================================================
	
	
	@Column(name="expenses")
	private String expense;
	
	@Column(name="transactionSerial")
	private String transactionSerial;

	@Column(name="amount")
	private Double amount;
	
	@OneToOne(fetch=FetchType.EAGER,cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
	@JoinColumn(name="account")
	private Account account;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//@Column(name="cancel")
	//private int cancel=0;

	/*public Long getVersion() {
		return version;
	}*/

	/*public void setVersion(Long version) {
		this.version = version;
	}*/

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTransactionSerial() {
		return transactionSerial;
	}

	public void setTransactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public String getTrasnsactionSerial() {
		return transactionSerial;
	}

	public void setTrasnsactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerName) {
		this.customerId = customerName;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}

