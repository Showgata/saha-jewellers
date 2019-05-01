package com.sahaJwellers.app.model;

import java.math.BigDecimal;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="loanTransaction_tbl")
@SelectBeforeUpdate
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//done by rajat
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval=false)
//	@JoinColumn(name="customer_id")
//	private Customer customer;
//	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval=false)
//	@JoinColumn(name="mortgage_id")
//	private Mortgage mortgage;
	//end
	
	@Version
	private Long version;
	
	@Column(name="voucher_Id")
	private Long voucherId;
	
	@Column(name="total_transaction_amnt")
	private BigDecimal transaction_amount;
	
	@Column(name="total_due_amount")
	private BigDecimal dueAmount;
	
	@Column(name="paid_amount")
	private BigDecimal paidAmount;
	
	@Column(name="prev_loan_amount")
	private BigDecimal previousLoanAmount;
	
	@Column(name="total_interest_amnt")
	private BigDecimal interest_amount;
	
	@Column(name="total_interest_due_amount")
	private BigDecimal interestDueAmount;
	
	@Column(name="interest_paid_amount")
	private BigDecimal interestPaidAmount;
	
	@Column(name="interest_prev_loan_amount")
	private BigDecimal interestPreviousLoanAmount;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
	@Temporal(TemporalType.DATE)
	@Column(name="payment_Date")
	private  Date date;

	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	private Date updateTimestamp;
	
	@Column(name="flag")
	private Integer flag =0;
	
	@Column(name="type")
	private String type;
	
	@Column(name="balance")
	private BigDecimal balance;
	

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public BigDecimal getTransaction_amount() {
		return transaction_amount;
	}

	public void setTransaction_amount(BigDecimal transaction_amount) {
		this.transaction_amount = transaction_amount;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		this.dueAmount = dueAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getPreviousLoanAmount() {
		return previousLoanAmount;
	}

	public void setPreviousLoanAmount(BigDecimal previousLoanAmount) {
		this.previousLoanAmount = previousLoanAmount;
	}

	public BigDecimal getInterest_amount() {
		return interest_amount;
	}

	public void setInterest_amount(BigDecimal interest_amount) {
		this.interest_amount = interest_amount;
	}

	public BigDecimal getInterestDueAmount() {
		return interestDueAmount;
	}

	public void setInterestDueAmount(BigDecimal interestDueAmount) {
		this.interestDueAmount = interestDueAmount;
	}

	public BigDecimal getInterestPaidAmount() {
		return interestPaidAmount;
	}

	public void setInterestPaidAmount(BigDecimal interestPaidAmount) {
		this.interestPaidAmount = interestPaidAmount;
	}

	public BigDecimal getInterestPreviousLoanAmount() {
		return interestPreviousLoanAmount;
	}

	public void setInterestPreviousLoanAmount(BigDecimal interestPreviousLoanAmount) {
		this.interestPreviousLoanAmount = interestPreviousLoanAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "LoanTransaction [id=" + id + ", version=" + version + ", voucherId=" + voucherId
				+ ", transaction_amount=" + transaction_amount + ", dueAmount=" + dueAmount + ", paidAmount="
				+ paidAmount + ", previousLoanAmount=" + previousLoanAmount + ", interest_amount=" + interest_amount
				+ ", interestDueAmount=" + interestDueAmount + ", interestPaidAmount=" + interestPaidAmount
				+ ", interestPreviousLoanAmount=" + interestPreviousLoanAmount + ", date=" + date
				+ ", createdTimestamp=" + createdTimestamp + ", updateTimestamp=" + updateTimestamp + "]";
	}

	

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//
//	public Mortgage getMortgage() {
//		return mortgage;
//	}
//
//	public void setMortgage(Mortgage mortgage) {
//		this.mortgage = mortgage;
//	}
	
	
}
