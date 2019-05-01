package com.sahaJwellers.app.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.ClosingBalance;
import com.sahaJwellers.app.model.Customer;

public interface ClosingBalanceService {

	

	List<ClosingBalance> closingBalance();

	List<ClosingBalance> getdatespecific(java.sql.Date date1, java.sql.Date date2);


}

