package com.sahaJwellers.app.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.Voucher;

public interface CashService {

//	void deleteByCustomerId(Long id);
//
//	Optional<Customer> findCustomerById(Long id);
//
//	Customer saveOrupdateCustomer(Customer customer);
 
	 List<Customer> findAllCustomer();
	 List<Voucher> fetchAllVouchers();
	 List<LoanTransaction> fetchAllLoanTransactions();
	 
     List<Expense> fetchAllExpense();
     
    // List<Expense> listExpensesBySpecificDatee();
     
	 //
//	List<Customer> fetchAllCustromerByDate(Date date);

}

