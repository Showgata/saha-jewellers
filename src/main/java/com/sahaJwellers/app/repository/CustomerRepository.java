package com.sahaJwellers.app.repository;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	/* Boilerplate code already present like insert,update,delete */
	
	@Query("select max(c1.customerId) from Customer c1")
	public Long getLastCustomerId();
	
	@Modifying
	@Transactional
	@Query("delete from Customer c1 where c1.customerId = :id")
	public void deleteByCustomerId(@Param("id")Long id);
	
	@Query("select c from Customer c where c.updateTimestamp >= :date ORDER BY c.updateTimestamp DESC")
	public List<Customer> listCustomerForToday(@Param("date") Date date);
	
	
	@Query("select c1 from Customer c1 where c1.customerId = :id")
	public Optional<Customer> findByCustomerId(@Param("id")Long id);
	
	//dummy
	@Query("select c1 from Customer c1 where c1.customerId = :id")
	public Customer findByCId(@Param("id")Long id);
}

