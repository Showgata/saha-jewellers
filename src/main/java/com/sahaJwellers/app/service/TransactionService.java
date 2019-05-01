package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.Transaction;

public interface TransactionService {

	List<Transaction> fetchAll();

	Transaction saveTransaction(Transaction transaction);

	void removeTransaction(Long id);

	Optional<Transaction> findTransactionById(Long id);

	List<Transaction> findAllTransaction(Date date);
	
	//=============================MODIFICATION
	void removeSpecificTransactionsRange(Date dateFrom,Date dateTo);
	
	void removeSpecificTransaction(Date date);
	
	void removeByTransactionNo(String serialNo);
	
	void removeByCustomerId(Long id);
	
	Transaction getTransactionBySerial(String serial);
	
	public List<Transaction> getTransactionByRemainderDate(Date reminderDate);
	

}