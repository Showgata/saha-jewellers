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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name="transaction_tbl")
public class Transaction {
	
	@JsonCreator
	public Transaction(@JsonProperty("id") Long id,@JsonProperty("version") Long version,@JsonProperty("reminderDate") Date reminderDate,@JsonProperty("storage") @Range(min = 1, max = 10) Integer storage,@JsonProperty("note") String note,
			@JsonProperty("transactionDate") Date transactionDate,@JsonProperty("mortgage") Mortgage mortgage,@JsonProperty("transactionSerial") String transactionSerial,@JsonProperty("voucher") Voucher voucher) {
		super();
		this.id = id;
		this.version = version;
		this.reminderDate = reminderDate;
		this.storage = storage;
		this.note = note;
		this.transactionDate = transactionDate;
		this.mortgage = mortgage;
		this.transactionSerial = transactionSerial;
		this.voucher = voucher;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Long id;
	
	@Version
	@Column(name="version")
	private Long version;
	
	@Column(name="reminder_date")
	private Date reminderDate;
	
	@Range(min=1,max=10)
	@Column(name="storage")
	private Integer storage;
	
	@Column(name="note")
	private String note;
	
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="mortgage_id")
	@JsonDeserialize
	private Mortgage mortgage;
	
	@Column(name="transactionSerial")
	private String transactionSerial;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
	private Voucher voucher;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Mortgage getMortgage() {
		return mortgage;
	}

	public void setMortgage(Mortgage mortgage) {
		this.mortgage = mortgage;
	}

	public String getTransactionSerial() {
		return transactionSerial;
	}

	public void setTransactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
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

	public void setTrasnsactionSerial(String trasnsactionSerial) {
		this.transactionSerial = trasnsactionSerial;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
