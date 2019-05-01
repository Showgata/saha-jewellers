package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	@Query("select t from Transaction t where t.reminderDate > :date")
	public List<Transaction> findAllTransactionByDate(@Param("date") Date date);
	
	
	//=================delete specific transaction===================
	@Transactional
	@Modifying
	@Query("delete from Transaction t  where t.transactionDate >= :datefrom AND t.transactionDate <= :dateto")
	public void deleteSpecificTransactionsRange(@Param("datefrom")Date datefrom,@Param("dateto")Date dateto);
	
	
	@Transactional
	@Modifying
	@Query("delete from Transaction t where t.transactionDate = :date")	
	public void deleteSpecificTransaction(@Param("date") Date date);
	
	
	@Transactional
	@Modifying
	@Query("delete from Transaction t where t.transactionSerial = :serial")	
	public void deleteByTransactionNo(@Param("serial") String serial);
	
	
	@Transactional
	@Modifying
	@Query("delete from Transaction t where t.customerId = :id")	
	public void deleteByCustomerID(@Param("id") Long id);
	
	//==============================================================
	
	
	@Query("select t from Transaction t where t.transactionSerial = :ts")
	public Transaction getTransactionBySerial(@Param("ts") String ts);

	
	@Query("select t from Transaction t where t.reminderDate = :reminderDate")
	public List<Transaction> getTransactionByRemainderDate(@Param("reminderDate") Date reminderDate);
	
	
	
	
	
	
	
	
	
	
}
