package com.sahaJwellers.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.TodayBalance;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.BalanceRepository;
import com.sahaJwellers.app.repository.CashRepository;
import com.sahaJwellers.app.repository.CustomerRepository;
import com.sahaJwellers.app.repository.ExpenseRepository;
import com.sahaJwellers.app.repository.LoanTransactionRepository;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

@Service
@Transactional
public class BalanceServiceImpl  implements BalanceService{
	


	@Autowired
	public BalanceRepository balanceRepository;

	
	@Override
	public List<TodayBalance> findTodaysBalance(){
		return balanceRepository.findAll();
		
	}
	
	
	
	
	@Override
	public List<TodayBalance> updateBalance(Long cashin,Long cashout,Long balance){
		List<TodayBalance> tb = balanceRepository.findAll();
		if(tb.isEmpty()) {
			TodayBalance t = new TodayBalance() ;
			t.setBalance(balance);
			t.setCashin(cashin);
			t.setCashout(cashout);
			tb.add(t);
		}
		else {
			tb.clear();
			TodayBalance t = new TodayBalance() ;
			t.setBalance(balance);
			t.setCashin(cashin);
			t.setCashout(cashout);
			tb.add(t);
		}
		return balanceRepository.saveAll(tb);
	}
}

