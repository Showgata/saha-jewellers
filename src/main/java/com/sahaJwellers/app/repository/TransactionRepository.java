package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	@Query("select t from Transaction t where t.reminderDate > :date")
	public List<Transaction> findAllTransactionByDate(@Param("date") Date date);
	
}
