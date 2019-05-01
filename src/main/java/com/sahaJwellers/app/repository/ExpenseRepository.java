package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.Voucher;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	
	
	@Query("select max(e.id) from Expense e")
	public Long getLastExpenseId();
	
	@Transactional
	@Query("select ex,ei from Expense ex,ExpenseInfo ei where ex.expenseInfoId = ei.id")
	public List<Object[]> getAllExpenseDetails();
	
	@Transactional
	@Query("select ex,ei from Expense ex,ExpenseInfo ei where ex.expenseInfoId = ei.id and ex.id=:id")
	public List<Object[]> getOneExpenseDetails(@Param("id") Long id);
	
	
	
	@Query("select ex,ei from Expense ex,ExpenseInfo ei where ex.expenseInfoId = ei.id AND ex.date = :date ORDER BY ex.updateTimestamp DESC")
	public List<Object[]> listExpensesBySpecificDate(@Param("date") Date date);
	
	
	@Query("select ex,ei from Expense ex,ExpenseInfo ei where ex.expenseInfoId = ei.id AND ex.date between :datefrom AND :dateto  ORDER BY ex.updateTimestamp DESC")
	public List<Object[]> listExpensesByDateRange(@Param("dateto") Date dateto,@Param("datefrom") Date datefrom);
	
	@Query("select e from Expense e where e.date >= :date ORDER BY e.createdTimestamp DESC")
	public List<Expense> listExpenseForToday(@Param("date") Date date);
	
	
	@Transactional
	@Query("select weekday(e.date) as day,count(e.id) as noOfVoucher,sum(e.amount),e.date from Expense e where week(e.date)= week(:date) group by DATE(e.date)")
	public List<Object[]> reportWeeklyExpense(@Param("date") Date date);
	
	
	@Transactional
	@Query("select day(e.date) as monthday,count(e.id) as noOfVoucher,sum(e.amount) from Expense e where month(e.date)= :month and year(e.date)= :year group by DATE(e.date)")
	public List<Object[]> reportMonthlyExpense(@Param("month")Integer month,@Param("year")Integer year);
	
	
	@Transactional
	@Query("select month(e.date) as month,count(e.id) as noOfVoucher,sum(e.amount) from Expense e where year(e.date)= :year group by month(e.date)")
	public List<Object[]> reportYearlyExpense(@Param("year")Integer year);
	
	@Transactional
	@Query("select year(e.date) as year,count(e.id) as noOfVoucher,sum(e.amount) from Expense e where year(e.date)>= :lyear and year(e.date)<= :uyear group by year(e.date)")
	public List<Object[]> reportDecadeExpense(@Param("lyear")Integer lyear,@Param("uyear")Integer uyear);
//with date from to 
	@Transactional
	@Query("select e from Expense e where e.date between :date1 and :date2")
	public List<Expense> getdatespecific(@Param("date1")java.sql.Date date1,@Param("date2")java.sql.Date date2);

}
