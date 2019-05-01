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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="expense_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Expense {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="expense_id")
	private Long id;
	

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;
	
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@UpdateTimestamp
	private Date updateTimestamp;
	
	
	@Column(name="expense_head")
	private String expenseHead;
	
	@Column(name="amount")
	private Long amount;
	
	@Column(name="note")
	private String note;
	
	@Column(name="expense_info_id")
	private Long expenseInfoId;
	
	
	
	public Long getExpenseInfoId() {
		return expenseInfoId;
	}

	public void setExpenseInfoId(Long expenseInfoId) {
		this.expenseInfoId = expenseInfoId;
	}

	public String getExpenseHead() {
		return expenseHead;
	}

	public void setExpenseHead(String expenseHead) {
		this.expenseHead = expenseHead;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	

	
	
	
}
