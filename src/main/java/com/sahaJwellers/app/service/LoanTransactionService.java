package com.sahaJwellers.app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.LoanUpdateDetails;

public interface LoanTransactionService {

	LoanTransaction saveLoan(LoanTransaction loan);
//todays
	List<LoanTransaction> getTodaysLoan();

	void deleteLoanTransaction(Long id);

	List<LoanTransaction> fetchLoanTransactions(Long voucherId);
	
	//=========================================================================
	List<LoanTransaction> getOneLoanTransactionByVoucher(Long voucherId);
	LoanTransaction updateLoan(LoanTransaction loan);
	List<LoanTransaction> fetchAllLoanTransactions();
	List<LoanTransaction> fetchAllLoanTransactionsByFlag(Integer flag);
	List<Object[]> listLoanTransactionByFlagAndVoucherId(Integer flag,Long voucherId);
	BigDecimal totalSumOfVoucherId(Long voucherId);
	BigDecimal totalSumInterestOfVoucherId(Long voucherId);
	void updateAmounts(Long voucherId,Long id,BigDecimal amnt,BigDecimal intAmnt);
	public List<LoanTransaction> getRecentTransactionOfAllVouchers(Integer flag,String type);
	public List<LoanTransaction> getRecentTransactionOfSpecificVoucher(int flag,Long voucherId);
	
	//delete And Update
	void deleteAndUpdateAmount(Long id,BigDecimal paidAmt,BigDecimal paidInterestAmt,Long voucherId);
	
	//Reports
	List<Object[]> getDailyTransactionUpdate(String type,Date date);
	List<Object[]> reportWeeklyTransactionUpdate(String type,Date date);	
	List<Object[]> reportMonthlyTransactionUpdate(String type,Integer month,Integer year);		
	List<Object[]> reportYearlyTransactionUpdate(String type,Integer year);	
	List<Object[]> reportDecadeTransactionUpdate(String type,Integer lyear,Integer uyear);

	List<LoanTransaction> getDailyLoan(int flag,String type,Date date);
	List<Object[]> reportWeeklyLoan(int flag,String type,Date date);
	List<Object[]> reportMonthlyLoan(int flag,String type,Integer month,Integer year);
	List<Object[]> reportYearlyLoan(int flag,String type,Integer year);
	List<Object[]> reportDecadeLoan(int flag,String type,Integer lyear,Integer uyear);
	//by rajat
	List<LoanTransaction> getdatespecific(java.sql.Date date1, java.sql.Date date2);
}