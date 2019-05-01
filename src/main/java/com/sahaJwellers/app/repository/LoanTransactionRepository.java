package com.sahaJwellers.app.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.LoanTransaction;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Long>{
	
	
	@Query("select t from LoanTransaction t")
	public List<LoanTransaction> findAll();	
	
	//rajat
	@Query("select t from LoanTransaction t where t.date BETWEEN :datefrom AND :dateto")
	public List<LoanTransaction> getspecific(@Param("datefrom") java.sql.Date df1,@Param("dateto") java.sql.Date df2);
	
	//todays tansaction
	@Query("select t from LoanTransaction t where t.date >= :date ORDER BY t.createdTimestamp DESC")
	public List<LoanTransaction> getTodaysLoan(@Param("date") Date date);
	
	
	
	@Query("select t from LoanTransaction t where t.date >= :date  ORDER BY t.createdTimestamp DESC")
	public List<LoanTransaction> listLoanTransactionByDate(@Param("date") Date date);	

	@Query("select t from LoanTransaction t where t.voucherId = :voucherId  ORDER BY t.createdTimestamp DESC")
	public List<LoanTransaction> listLoanTransactionByVoucher(@Param("voucherId") Long voucherId);
	
	//===================================================================================
	
	@Query("select t from LoanTransaction t where t.voucherId = :voucherId ORDER BY t.createdTimestamp DESC")
	public List<LoanTransaction> findFirstLoanTransactionByVoucher(@Param("voucherId") Long voucherId);
	
	@Query("select t from LoanTransaction t where t.flag = :flag ORDER BY t.createdTimestamp DESC")
	public List<LoanTransaction> listLoanTransactionByFlag(@Param("flag") int flag);
	
	@Query("select t,v from LoanTransaction t,Voucher v where t.voucherId = v.id and flag = :flag and t.voucherId = :voucherId  ORDER BY t.createdTimestamp DESC")
	public List<Object[]> listLoanTransactionByFlagAndVoucherId(@Param("flag") int flag,@Param("voucherId") Long voucherId);
	
	//==================================
	@Query("select SUM(t.paidAmount) from LoanTransaction t where t.voucherId = :voucherId")
	public BigDecimal totalSumOfVoucherId(@Param("voucherId") Long voucherId);
	
	@Query("select SUM(t.interestPaidAmount) from LoanTransaction t where t.voucherId = :voucherId")
	public BigDecimal totalSumInterestOfVoucherId(@Param("voucherId") Long voucherId);
	
	@Modifying
	@Query("UPDATE LoanTransaction t SET t.previousLoanAmount = t.previousLoanAmount + :amnt,t.interestPreviousLoanAmount = t.interestPreviousLoanAmount + :intamnt where t.voucherId = :voucherId and id> :id")
	public void updateAmounts(@Param("voucherId") Long voucherId,@Param("id") Long id,@Param("amnt") BigDecimal amnt,@Param("intamnt") BigDecimal intAmnt);
	
	@Query("select p,v from LoanTransaction p left join LoanTransaction p2 on p.voucherId = p2.voucherId AND p.createdTimestamp < p2.createdTimestamp " + 
			"left join Voucher v on p.voucherId=v.id  where p2.createdTimestamp is NULL AND p.flag =:flag AND p.type =:type group by p.voucherId")
	public List<LoanTransaction> getRecentTransactionOfAllVouchers(@Param("flag") int flag,@Param("type")String type);
	
	
	@Query("select p,v from LoanTransaction p left join LoanTransaction p2 on p.voucherId = p2.voucherId AND p.createdTimestamp < p2.createdTimestamp " + 
			"left join Voucher v on p.voucherId=v.id  where p2.createdTimestamp is NULL AND p.flag =:flag AND p.voucherId =:id group by p.voucherId")
	public List<LoanTransaction> getRecentTransactionOfSpecificVoucher(@Param("flag") int flag,@Param("id")Long voucherId);
	
	
	@Modifying
	@Query("UPDATE LoanTransaction t SET t.previousLoanAmount = t.previousLoanAmount - :amnt,t.interestPreviousLoanAmount = t.interestPreviousLoanAmount - :intamnt where t.voucherId = :voucherId and id> :id")
	public void updateAmountsAfterDeletion(@Param("voucherId") Long voucherId,@Param("id") Long id,@Param("amnt") BigDecimal amnt,@Param("intamnt") BigDecimal intAmnt);
	
	
	@Modifying
	@Query("UPDATE LoanTransaction t SET t.balance = t.balance - (:amnt + :intamnt) where t.voucherId = :voucherId and id> :id")
	public void updateBalanceAfterDeletion(@Param("voucherId") Long voucherId,@Param("id") Long id,@Param("amnt") BigDecimal amnt,@Param("intamnt") BigDecimal intAmnt);
	
	
	@Modifying
	@Query("UPDATE LoanTransaction t SET t.balance = t.balance + (:amnt + :intamnt) where t.voucherId = :voucherId and id> :id")
	public void updateBalanceAfterUpdate(@Param("voucherId") Long voucherId,@Param("id") Long id,@Param("amnt") BigDecimal amnt,@Param("intamnt") BigDecimal intAmnt);
	
	
	
	/*Reports*/
	
	
	@Query("select v,l.voucherId,l.paidAmount,l.interestPaidAmount from Voucher v,LoanTransaction l where l.voucherId = v.id and l.date =:date and l.type =:type")
	public List<Object[]> getDailyTransactionUpdate(@Param("type")String type,@Param("date")Date date);
	
	@Transactional
	@Query("select weekday(v.date) as day,count(v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanTransaction v where week(v.date)=week(:date) and type =:type group by DATE(v.date)")
	public List<Object[]> reportWeeklyTransactionUpdate(@Param("type") String type,@Param("date") Date date);
	
	@Query("select day(v.date) as day,count (v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanTransaction v where month(v.date)= :month and year(v.date)= :year and type =:type group by DATE(v.date)")
	public List<Object[]> reportMonthlyTransactionUpdate(@Param("type") String type,@Param("month")Integer month,@Param("year")Integer year);
	
	@Query("select month(v.date) as month,count (v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanTransaction v where year(v.date)= :year and type =:type group by month(v.date)")
	public List<Object[]> reportYearlyTransactionUpdate(@Param("type") String type,@Param("year")Integer year);
	
	
	@Query("select year(v.date) as month,count (v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanTransaction v where year(v.date)>= :lyear and year(v.date)<= :uyear and type =:type group by month(v.date)")
	public List<Object[]> reportDecadeTransactionUpdate(@Param("type") String type,@Param("lyear")Integer lyear,@Param("uyear")Integer uyear);
	
	
	
	@Query("select p,v from LoanTransaction p left join LoanTransaction p2 on p.voucherId = p2.voucherId AND p.createdTimestamp < p2.createdTimestamp " + 
			"left join Voucher v on p.voucherId=v.id  where p2.createdTimestamp is NULL AND p.flag =:flag AND p.type =:type AND p.date =:date group by p.voucherId")
	public List<LoanTransaction> getDailyLoan(@Param("flag") int flag,@Param("type")String type,@Param("date")Date date);
	
	
	
	@Transactional
	//@Query("select weekday(v.date) as day,count(distinct v.voucherId) as noOfVoucher,sum(v.paidAmount+v.interestPaidAmount),v.date from LoanTransaction v where week(v.date)=week(:date) and type =:type and flag =:flag group by DATE(v.date)")
	@Query("select weekday(v.date) as day,count(distinct v.voucherId) as noOfVoucher,sum(v.balance),v.date from LoanTransaction v where week(v.date)=week(:date) and type =:type and flag =:flag group by DATE(v.date)")
	public List<Object[]> reportWeeklyLoan(@Param("flag") int flag,@Param("type") String type,@Param("date") Date date);
	
	
	@Query("select day(v.date) as day,count (distinct v.voucherId) as noOfVoucher,sum(v.balance),v.date from LoanTransaction v where month(v.date)= :month and year(v.date)= :year and type =:type and flag =:flag group by DATE(v.date)")
	public List<Object[]> reportMonthlyLoan(@Param("flag") int flag,@Param("type") String type,@Param("month")Integer month,@Param("year")Integer year);
	
	
	@Query("select month(v.date) as month,count (distinct v.voucherId) as noOfVoucher,sum(v.balance),v.date from LoanTransaction v where year(v.date)= :year and type =:type and flag =:flag group by month(v.date)")
	public List<Object[]> reportYearlyLoan(@Param("flag") int flag,@Param("type") String type,@Param("year")Integer year);
	
	
	@Query("select year(v.date) as month,count (distinct v.voucherId) as noOfVoucher,sum(v.balance),v.date from LoanTransaction v where year(v.date)>= :lyear and year(v.date)<= :uyear and type =:type and flag =:flag group by month(v.date)")
	public List<Object[]> reportDecadeLoan(@Param("flag") int flag,@Param("type") String type,@Param("lyear")Integer lyear,@Param("uyear")Integer uyear);


	

	
}
