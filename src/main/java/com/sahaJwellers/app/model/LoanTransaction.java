package com.sahaJwellers.app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="loanTransaction_tbl")
public class LoanTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
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
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="payment_Date")
	private  Date date;

	@JsonIgnore
	@CreationTimestamp
	private Date createdTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	private Date updateTimestamp;

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
	
	
}
