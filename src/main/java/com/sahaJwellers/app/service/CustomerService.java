package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.Customer;

public interface CustomerService {

	void deleteCustomerById(Long id);

	Optional<Customer> findCustomerById(Long id);

	Customer saveOrupdateCustomer(Customer customer);

	List<Customer> findAllCustomer();

	List<Customer> fetchAllCustromerByDate(Date date);

}
