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


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="expense_info_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExpenseInfo {
	
	
	@Override
	public String toString() {
		return "ExpenseInfo [id=" + id + ", createdTimestamp=" + createdTimestamp + ", updateTimestamp="
				+ updateTimestamp + ", expenseName=" + expenseName + ", expenseDescription=" + expenseDescription
				+ ", mobileNo=" + mobileNo + ", address=" + address + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="expense_info_id")
	private Long id;
	
	
	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@UpdateTimestamp
	private Date updateTimestamp;
	
	
	@Column(name="expense_name")
	private String expenseName;
	
	@Column(name="expense_desc")
	private String expenseDescription;
	
	
	
	@Column(name="mobile")
	private Long mobileNo;
	
	@Column(name="address")
	private String address;
	
	
	public ExpenseInfo(String expenseName, String expenseDescription, Long mobileNo, String address) {
		super();
		this.expenseName = expenseName;
		this.expenseDescription = expenseDescription;
		this.mobileNo = mobileNo;
		this.address = address;
	}

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public ExpenseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	
	
}
