package com.sahaJwellers.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.LoanTakeTransaction;
import com.sahaJwellers.app.repository.LoanTakeTransactionRepository;

@Service
@Transactional
public class LoanTakeTransactionServiceImpl implements LoanTakeTransactionService {

	@Autowired
	private LoanTakeTransactionRepository ltr;

	
	@Override
	public LoanTakeTransaction saveLoan(LoanTakeTransaction loan){
		
		if(loan.getVoucherId() != null && ltr.existsById(loan.getVoucherId()))
		{
			LoanTakeTransaction tl = ltr.getOne(loan.getVoucherId());
			
			tl.setDate(loan.getDate());
			tl.setDueAmount(loan.getDueAmount());
			tl.setInterest_amount(loan.getInterest_amount());
			tl.setInterestDueAmount(loan.getInterestDueAmount());
			tl.setInterestPreviousLoanAmount(loan.getInterestPreviousLoanAmount());
			tl.setInterestPaidAmount(loan.getInterestPaidAmount());
			
			tl.setPaidAmount(loan.getPaidAmount());
			tl.setTransaction_amount(loan.getTransaction_amount());
			tl.setVoucherId(loan.getVoucherId());
			tl.setFlag(loan.getFlag());
			
			
			return tl;
		}
		
		
		
		
		
		return ltr.save(loan);
	}
	
	
	@Override
	public void deleteLoanTransaction(Long id){
		ltr.deleteById(id);
	}
	
	
	@Override
	public List<LoanTakeTransaction> fetchLoanTransactions(Long voucherId){
		return ltr.listLoanTransactionByVoucher(voucherId);
	}


	@Override
	public LoanTakeTransaction fetchTakeLoanByTransactionID(Long id) {
		
		if(ltr.existsById(id))
		{
			System.out.println("in service impl = "+id);
			return ltr.fetchTakeLoanByTransactionID(id);
		}

		return null;
	}
	
	
}

