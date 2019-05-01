package com.sahaJwellers.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.ExpenseInfo;

public interface ExpenseService {

	Expense saveExpense(Expense expense);
	ExpenseInfo saveExpenseInfo(ExpenseInfo expenseInfo);

	void deleteExpense(Long id);
	void deleteExpenseInfo(Long id);

	List<ExpenseInfo> fetchAllExpenseInfo();
	List<Expense> fetchAllExpense();
	List<Object[]> getAllExpenseDetails();
	List<Object[]> getOneExpenseDetail(Long id);
	//=========================================================================
	Expense getExpenseById(Long voucherId);
	Expense updateExpense(Expense e);
	List<Object[]> listExpensesBySpecificDate(Date date);
	List<Object[]> listExpensesByDateRange(Date dateto,Date datefrom);
	ExpenseInfo getExpenseInfoById(Long voucherId);
	List<Expense> listExpenseForToday();
	List<Object[]> reportWeeklyExpense(Date date);
	List<Object[]> reportMonthlyExpense(Integer month,Integer year);
	List<Object[]> reportYearlyExpense(Integer year);
	List<Object[]> reportDecadeExpense(Integer lyear,Integer uyear);
	//List<LoanTransaction> fetchAllLoanTransactions();
	//List<LoanTransaction> fetchAllLoanTransactionsByFlag(Integer flag);
	//List<LoanTransaction> listLoanTransactionByFlagAndVoucherId(Integer flag,Long voucherId);
	List<Expense> getdatespecific(java.sql.Date date1, java.sql.Date date2);

}