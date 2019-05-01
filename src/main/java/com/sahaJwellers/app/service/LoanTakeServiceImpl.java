package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.TakeLoan;
import com.sahaJwellers.app.repository.CustomerRepository;
import com.sahaJwellers.app.repository.LoanTakeRepository;


@Service
@Transactional
public class LoanTakeServiceImpl implements LoanTakeService {
	
	@Autowired
	private LoanTakeRepository loanTakeRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	@Override
	public List<TakeLoan> fetchAllTakeLoanDetails(){
		return loanTakeRepository.findAll(new Sort(Sort.Direction.DESC, "loanId"));
	}

	@Override
	public TakeLoan saveLoan(TakeLoan loan) {
		
		Customer customer = loan.getCustomer();
		if(customer != null && customer.getCustomerId() != null) {
			Customer cust = customerRepository.getOne(loan.getCustomerId());
			cust.setAddress(customer.getAddress());
			cust.setCustomerName(customer.getCustomerName());
			cust.setMobile(customer.getMobile());
			cust.setNote(customer.getNote());
			cust.setReferences(customer.getReferences());
			customerRepository.save(customer);
			customer = cust;
		}
		loan.setCustomer(customer);
		
		System.out.println(loan.toString());
		
		if(loan.getLoanId() != null) {
			
			if(loanTakeRepository.existsById(loan.getLoanId())) {
			
			TakeLoan tl = loanTakeRepository.getOne(loan.getLoanId());
			
			tl.setAccountName(loan.getAccountName());
			tl.setBankList(loan.getBankList());
			tl.setCurrency(loan.getCurrency());
			tl.setInterestRate(loan.getInterestRate());
			tl.setLoanAmount(loan.getLoanAmount());
			tl.setRemainderDate(loan.getRemainderDate());
			
			System.out.println("=========================================");
			System.out.println(tl.toString());
			
			
			return loanTakeRepository.save(tl);
			}
		}
		
		
		return loanTakeRepository.save(loan);
	}

	@Override
	public void deleteLoanTakeDetails(Long id) {
		
		loanTakeRepository.deleteById(id);
		
	}

	@Override
	public List<TakeLoan> fetchLoanTakeDetailsByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return loanTakeRepository.findLoanTakeDetailsByCustomerId(customerId);
	}

	@Override
	public Optional<TakeLoan> fetchTakeLoanDetail(Long id) {
		// TODO Auto-generated method stub
		return loanTakeRepository.findById(id);
	}
	
	
	

}
