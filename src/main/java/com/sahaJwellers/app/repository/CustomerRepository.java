package com.sahaJwellers.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
