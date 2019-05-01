package com.sahaJwellers.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.CashRepository;
import com.sahaJwellers.app.repository.CustomerRepository;
import com.sahaJwellers.app.repository.ExpenseRepository;
import com.sahaJwellers.app.repository.LoanTransactionRepository;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

@Service
@Transactional
public class CashServiceImpl  implements CashService{
	
	@Autowired
	public CashRepository cashRepository;
	
	@Autowired
	public VoucherRepository voucherRepository;
	
	@Autowired
	public LoanTransactionRepository loanTransactionRepository;


	@Autowired
	public ExpenseRepository expenseRepository;

	/*public CustomerServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}*/
	
//	@Override
//	public Customer saveOrupdateCustomer(Customer customer){
//	
//		if(customer.getCustomerId()==null) {
//			
//			Long id = customerRepository.getLastCustomerId();
//
//			if(id == null || id == 0) {id =1l;}
//			else {id++;}
//			
//			customer.setCustomerId(id);
//			
//		}
//		
//		return customerRepository.save(customer);
//	}
//	
//	@Override
//	public Optional<Customer> findCustomerById(Long id){
//		return customerRepository.findByCustomerId(id);
//	}
//	
//	@Override
//	public void deleteByCustomerId(Long id){
//		 customerRepository.deleteByCustomerId(id);
//	}
	@Override
	public List<Customer> findAllCustomer(){
		return cashRepository.findAll();
	}
	@Override
	public List<Voucher> fetchAllVouchers(){
		return voucherRepository.findAll();
	}
	
	@Override
	public List<LoanTransaction> fetchAllLoanTransactions(){
		return loanTransactionRepository.findAll();
	}
	
	@Override
	public List<Expense> fetchAllExpense(){
		return expenseRepository.findAll();
	}
//	@Override
//	public List<Expense> listExpensesBySpecificDatee(){
//		return expenseRepository.listExpensesBySpecificDatee();
//	}
//	
//	@Override
//	public List<Customer> fetchAllCustromerByDate(Date date){
//		return customerRepository.listCustomerForToday(date);
//	}
	
}

