package com.sahaJwellers.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="transaction_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class Transaction {
	
	@JsonCreator
	public Transaction(@JsonProperty("id") Long id,@JsonProperty("reminderDate") Date reminderDate,@JsonProperty("storage") @Range(min = 1, max = 10) Integer storage,@JsonProperty("note") String note,
			@JsonProperty("transactionDate") Date transactionDate,@JsonProperty("transactionSerial") String transactionSerial) {
		super();
		this.id = id;
		this.reminderDate = reminderDate;
		this.storage = storage;
		this.note = note;
		this.transactionDate = transactionDate;
		this.transactionSerial = transactionSerial;
		
	}

	public Transaction() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Long id;
/*	
	@Version
	@Column(name="version")
	private Long version;*/
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="reminder_date")
	private Date reminderDate;
	
	@Range(min=1,max=10)
	@Column(name="storage")
	private Integer storage;
	
	@Column(name="note")
	private String note;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="transaction_date")
	@UpdateTimestamp
	private Date transactionDate;
	
	@Column(name="expenses")
	private String expense;
	
	@Column(name="transactionSerial")
	private String transactionSerial;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public Long getVersion() {
		return version;
	}*/

	/*public void setVersion(Long version) {
		this.version = version;
	}*/

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

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
