package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.TakeLoan;

public interface LoanTakeService {
	
	List<TakeLoan> fetchAllTakeLoanDetails();
	
	TakeLoan saveLoan(TakeLoan loan);

	void deleteLoanTakeDetails(Long id);

	List<TakeLoan> fetchLoanTakeDetailsByCustomerId(Long customerId);
	
	Optional<TakeLoan>  fetchTakeLoanDetail(Long id);

}
