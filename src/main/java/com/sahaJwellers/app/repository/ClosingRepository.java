package com.sahaJwellers.app.repository;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.ClosingBalance;
import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.TodayBalance;

@Repository
public interface ClosingRepository extends JpaRepository<ClosingBalance, Long>{
	
	/* Boilerplate code already present like insert,update,delete */
	
	
	@Query("select cb from ClosingBalance cb")
	public List<TodayBalance> getTodaysBalance();

	@Query("select cb.cbalance from ClosingBalance cb where cb.date = :date ")
	public List<ClosingBalance> yesterdayBalance(@Param("date") String date);
	
	@Query("select cb from ClosingBalance cb where cb.date between :date1 and :date2 ")
	public List<ClosingBalance> specific(@Param("date1") String date1,@Param("date2") String date2);
	
}

