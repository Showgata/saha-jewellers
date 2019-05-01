package com.sahaJwellers.app.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.ClosingBalance;
import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.repository.ClosingRepository;
import com.sahaJwellers.app.repository.CustomerRepository;

@Service
@Transactional
public class ClosingBalanceServiceImpl  implements ClosingBalanceService{
	
	@Autowired
	public ClosingRepository closingRepository;
	
	public List<ClosingBalance> closingBalance(){

		//yyyy-mm-dd hh:mm:ss
		
		Date date = DateUtils.addDays(new Date(), -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		//Calendar cal  = Calendar.getInstance();
		//cal.add(Calendar.DATE, -1);
		//new java.util.Date()
		//cal.getTime()
		//java.sql.Timestamp.valueOf
		return closingRepository.yesterdayBalance(sdf.format(date) );
	}
	

	/*public CustomerServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}*/
	
	//date from to date to
	
	public List<ClosingBalance> getdatespecific(java.sql.Date date1, java.sql.Date date2){
		return closingRepository.specific((date1).toString(),(date2).toString());
	}
	
}

