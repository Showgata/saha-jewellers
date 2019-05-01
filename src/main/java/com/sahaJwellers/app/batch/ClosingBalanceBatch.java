package com.sahaJwellers.app.batch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sahaJwellers.app.model.ClosingBalance;
import com.sahaJwellers.app.model.TodayBalance;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.BalanceRepository;
import com.sahaJwellers.app.repository.ClosingRepository;
import com.sahaJwellers.app.service.BalanceService;


//Rajat
@Component
public class ClosingBalanceBatch {
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	public BalanceRepository balanceRepository;
	
	@Autowired
	public ClosingRepository closingRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Scheduled(cron = "0 0 0 * * *") //for 30 seconds trigger -> 0,30 * * * * * for 12am fires every day -> 0 0 0 * * *
	public void cronJob() {
	//	logger.info("> cronjob");
//		
//		Collection<TodayBalance> tb = balanceService.findTodaysBalance();
//		List<TodayBalance> alltb = new ArrayList<TodayBalance>();
//		alltb.addAll(tb);
		
		//data pass to ---> closingbalance
		List<TodayBalance> tb = balanceService.findTodaysBalance();
		List<TodayBalance> alltb = new ArrayList<TodayBalance>();
		alltb.addAll(tb);
		
	
		Calendar cal  = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
	
		if(!alltb.isEmpty()) {
			for (TodayBalance tbal : alltb ) {
			    
				ClosingBalance c = new ClosingBalance();
				c.setCbalance(tbal.getBalance());
				c.setDate((cal.getTime()).toString());
		       
				closingRepository.save(c);
				
			}
		}
		
		
		
		
	}

}
