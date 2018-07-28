package com.sahaJwellers.app.service;

import java.util.List;

import com.sahaJwellers.app.model.LoanTransaction;

public interface LoanTransactionService {

	LoanTransaction saveLoan(LoanTransaction loan);

	void deleteLoanTransaction(Long id);

	List<LoanTransaction> fetchLoanTransactions(Long voucherId);

}