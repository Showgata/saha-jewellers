package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl  implements CustomerService{
	
	@Autowired
	public CustomerRepository customerRepository;
	
	

	/*public CustomerServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}*/
	
	@Override
	public Customer saveOrupdateCustomer(Customer customer){
		return customerRepository.save(customer);
	}
	
	@Override
	public Optional<Customer> findCustomerById(Long id){
		return customerRepository.findById(id);
	}
	
	@Override
	public void deleteCustomerById(Long id){
		 customerRepository.deleteById(id);
	}
	
	@Override
	public List<Customer> findAllCustomer(){
		return customerRepository.findAll();
	}
	
	@Override
	public List<Customer> fetchAllCustromerByDate(Date date){
		return customerRepository.listCustomerForToday(date);
	}
	
}
