package com.sahaJwellers.app.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.TodayBalance;

public interface BalanceService {

		List<TodayBalance> findTodaysBalance();
	
		List<TodayBalance> updateBalance(Long cashin,Long cashout,Long balance);
		
		
}

