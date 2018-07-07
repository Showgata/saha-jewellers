package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#fetchAll()
	 */
	@Override
	public List<Transaction> fetchAll(){
		return transactionRepository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#saveTransaction(org.sahaJwellers.app.model.Transaction)
	 */
	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#removeTransaction(java.lang.Long)
	 */
	@Override
	public void removeTransaction(Long id) {
		transactionRepository.deleteById(id);
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#findTransactionById(java.lang.Long)
	 */
	@Override
	public Optional<Transaction> findTransactionById(Long id) {
		return transactionRepository.findById(id);
	}
}
