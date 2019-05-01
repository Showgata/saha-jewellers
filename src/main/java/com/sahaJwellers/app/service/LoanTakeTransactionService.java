package com.sahaJwellers.app.service;

import java.util.List;

import com.sahaJwellers.app.model.LoanTakeTransaction;


public interface LoanTakeTransactionService {

	LoanTakeTransaction saveLoan(LoanTakeTransaction loan);

	void deleteLoanTransaction(Long id);

	List<LoanTakeTransaction> fetchLoanTransactions(Long voucherId);
	
	LoanTakeTransaction fetchTakeLoanByTransactionID(Long id);

}