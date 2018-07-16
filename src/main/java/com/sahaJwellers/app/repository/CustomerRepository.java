package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query("select c from Customer c where c.updateTimestamp >= :date ORDER BY c.updateTimestamp DESC")
	public List<Customer> listCustomerForToday(@Param("date") Date date);
}
