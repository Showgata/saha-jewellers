package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.Transaction;

public interface TransactionService {

	List<Transaction> fetchAll();

	Transaction saveTransaction(Transaction transaction);

	void removeTransaction(Long id);

	Optional<Transaction> findTransactionById(Long id);

}