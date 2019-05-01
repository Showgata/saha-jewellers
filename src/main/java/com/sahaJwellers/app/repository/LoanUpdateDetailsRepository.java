package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.LoanUpdateDetails;

public interface LoanUpdateDetailsRepository extends JpaRepository<LoanUpdateDetails, Long>{
	
	
	@Transactional
	@Query("select v from LoanUpdateDetails v where v.date =:date and type =:type")
	public List<LoanUpdateDetails> reportDailyTransactionUpdate(@Param("type") String type,@Param("date") Date date);
	
	
	@Transactional
	@Query("select weekday(v.date) as day,count(distinct v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanUpdateDetails v where week(v.date)=week(:date) and type =:type  group by DATE(v.date)")
	public List<Object[]> reportWeeklyTransactionUpdate(@Param("type") String type,@Param("date") Date date);
	
	@Query("select day(v.date) as day,count (distinct v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanUpdateDetails v where month(v.date)= :month and year(v.date)= :year and type =:type group by DATE(v.date)")
	public List<Object[]> reportMonthlyTransactionUpdate(@Param("type") String type,@Param("month")Integer month,@Param("year")Integer year);
	
	
	@Query("select month(v.date) as month,count (distinct v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanUpdateDetails v where year(v.date)= :year and type =:type group by month(v.date)")
	public List<Object[]> reportYearlyTransactionUpdate(@Param("type") String type,@Param("year")Integer year);
	
	
	@Query("select year(v.date) as month,count (distinct v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanUpdateDetails v where year(v.date)>= :lyear and year(v.date)<= :uyear and type =:type group by month(v.date)")
	public List<Object[]> reportDecadeTransactionUpdate(@Param("type") String type,@Param("lyear")Integer lyear,@Param("uyear")Integer uyear);
	
	
}
