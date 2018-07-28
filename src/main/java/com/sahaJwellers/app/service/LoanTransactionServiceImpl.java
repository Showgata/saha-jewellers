package com.sahaJwellers.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.repository.LoanTransactionRepository;

@Service
@Transactional
public class LoanTransactionServiceImpl implements LoanTransactionService {

	@Autowired
	private LoanTransactionRepository loanTransactionRepository;

	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#saveLoan(com.sahaJwellers.app.model.LoanTransaction)
	 */
	@Override
	public LoanTransaction saveLoan(LoanTransaction loan){
		return loanTransactionRepository.save(loan);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#deleteLoanTransaction(java.lang.Long)
	 */
	@Override
	public void deleteLoanTransaction(Long id){
		loanTransactionRepository.deleteById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#fetchLoanTransactions(java.lang.Long)
	 */
	@Override
	public List<LoanTransaction> fetchLoanTransactions(Long voucherId){
		return loanTransactionRepository.listLoanTransactionByVoucher(voucherId);
	}
}
