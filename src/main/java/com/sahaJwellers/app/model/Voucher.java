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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="voucher_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
public class Voucher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="voucher_id")
	private Long id;
	
	@Column(name="version")
	private Long version;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name="date")
	private Date date;
	
	@Column(name="serial")
	private String serial;
	
	@Override
	public String toString() {
		return "Voucher [id=" + id + ", version=" + version + ", date=" + date + ", serial=" + serial
				+ ", createdTimestamp=" + createdTimestamp + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@UpdateTimestamp
	private Date updateTimestamp;
	
	/*@OneToMany(mappedBy="voucher",orphanRemoval=true,cascade=CascadeType.PERSIST)
	private List<Transaction> transactionList;*/
	
	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},orphanRemoval=false)
	@JoinColumn(name="customer_id")
	private Customer customer;

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval=false)
	@JoinColumn(name="transaction_id")
	private Transaction transaction;

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval=false)
	@JoinColumn(name="mortgage_id")
	private Mortgage mortgage;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Mortgage getMortgage() {
		return mortgage;
	}

	public void setMortgage(Mortgage mortgage) {
		this.mortgage = mortgage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
}
