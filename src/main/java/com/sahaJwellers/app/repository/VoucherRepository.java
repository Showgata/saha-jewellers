package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	@Query("select max(v.id) from Voucher v where  v.type = :type")
	public Long getLastVoucherIdByType(@Param("type") String type);
	
	@Transactional
	@Modifying
	@Query("delete from Voucher v where v.sl_id = :id and v.type = :type")
	public void deleteVoucherByVoucherId(@Param("id") Long id,@Param("type") String type);
	
	@Query("select v from Voucher v where v.id = :id")
	public Optional<Voucher> findByVoucherId(@Param("id")Long id);
	
	@Query("select v from Voucher v where v.sl_id = :id")
	public Optional<Voucher> findByVoucherSlId(@Param("id")Long slid);
	
	@Query("select v from Voucher v where v.transaction = :transaction")
	public Optional<Voucher> findVoucherByTransactionId(@Param("transaction") Transaction transaction);
	
	@Query("select v from Voucher v where v.date >= :date AND v.type = :type ORDER BY v.updateTimestamp DESC")
	public List<Voucher> listVoucherForTodayByType(@Param("date") Date date,@Param("type") String type);
	
	@Query("select v from Voucher v where v.type = :type ORDER BY v.updateTimestamp DESC")
	public List<Voucher> listVoucherByType(@Param("type") String type);
	
	
	@Query("select v from Voucher v where v.date = :date AND v.type = :type ORDER BY v.createdTimestamp DESC")
	public List<Voucher> getVoucherByDateAndType(@Param("date") Date date,@Param("type") String type);
	
	//=======================================================================================
	
	@Query("select v from Voucher v where v.date = :date AND v.type = :type ORDER BY v.createdTimestamp DESC")
	public List<Voucher> listExpensesBySpecificDate(@Param("date") Date date,@Param("type") String type);
	
	@Query("select v from Voucher v where v.type = :type AND v.date between :datefrom AND :dateto  ORDER BY v.createdTimestamp DESC")
	public List<Voucher> listExpensesByDateRange(@Param("type") String type,@Param("dateto") Date dateto,@Param("datefrom") Date datefrom);
	
	
	
	@Transactional
	@Modifying
	@Query("delete from Voucher t  where t.date >= :datefrom AND t.date <= :dateto")
	public void deleteSpecificVoucherRange(@Param("datefrom")Date datefrom,@Param("dateto")Date dateto);
	
	@Transactional
	@Modifying
	@Query("delete from Voucher t where t.date = :date")	
	public void deleteSpecificVoucher(@Param("date") Date date);
	
	

	@Transactional
	@Modifying
	@Query("delete from Voucher t where customer_id = :id")
	public void deleteVoucherByCustomerId(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query("delete from Voucher t where transaction_id = :id")
	public void deleteVoucherByTransactionId(@Param("id") Long id);
	
	
	
	@Query("select t from Voucher t where customer_id = :id and t.type = :type ORDER BY t.createdTimestamp DESC")
	public List<Voucher> findVoucherByCustomerIdAndType(@Param("id") Long id,@Param("type") String type);
	
	
	@Query("select t from Voucher t where transaction_id = :id and t.type = :type ORDER BY t.createdTimestamp DESC")
	public Voucher findVoucherByTransactionIdAndType(@Param("id") Long id,@Param("type") String type);
	
	//Custom Report
	@Transactional
	@Query("select l,t from Voucher t,LoanTransaction l where l.voucherId = t.id and t.type = :type and l.flag= :flag ORDER BY l.createdTimestamp DESC")
	public List<Object[]> filteredData(@Param("flag") Integer flag,@Param("type") String type);
	
	
	@Transactional
	@Query("select l,t from Voucher t,LoanTransaction l where l.voucherId = t.id and t.type = :type and l.voucherId = :id ORDER BY l.createdTimestamp DESC")
	public List<Object[]> filteredDataByIdAndType(@Param("type") String type,@Param("id")Long voucherId);


	
	@Transactional
	@Query("select l,t from Voucher t,LoanTransaction l where l.voucherId = t.id and l.voucherId = :id ORDER BY l.createdTimestamp DESC")
	public List<Object[]> filterById(@Param("id") Long id);

	
	//Transaction History
	@Transactional
	@Query("select l,t from Voucher t,LoanTransaction l where l.voucherId = t.id and t.type = :type ORDER BY l.createdTimestamp DESC")
	public List<Object[]> filterByType(@Param("type") String type);
	
	@Transactional
	@Query("select l,t from Voucher t,LoanTransaction l where l.voucherId = t.id and l.id = :id and t.type = :type ORDER BY l.createdTimestamp DESC")
	public List<Object[]> updateByIdAndType(@Param("type") String type,@Param("id") Long id);
	
	
	
	@Transactional
	@Query("select weekday(v.date) as day,count(v.id) as noOfVoucher,sum(v.transaction.amount),v.date from Voucher v where week(v.date)=week(:date) and type =:type group by DATE(v.date)")
	public List<Object[]> reportWeekly(@Param("type") String type,@Param("date") Date date);
	
	
	@Transactional
	@Query("select day(v.date) as monthday,count(v.id) as noOfVoucher,sum(v.transaction.amount) from Voucher v where month(v.date)= :month and year(v.date)= :year and type =:type group by date(v.date)")
	public List<Object[]> reportMonthly(@Param("type") String type,@Param("month")Integer month,@Param("year")Integer year);
	
	
	@Transactional
	@Query("select month(v.date) as month,count(v.id) as noOfVoucher,sum(v.transaction.amount) from Voucher v where year(v.date)= :year and type =:type group by month(v.date)")
	public List<Object[]> reportYearly(@Param("type") String type,@Param("year")Integer year);
	
	
	@Transactional
	@Query("select year(v.date) as year,count(v.id) as noOfVoucher,sum(v.transaction.amount) from Voucher v where year(v.date)>= :lyear and year(v.date)<= :uyear and type =:type group by year(v.date)")
	public List<Object[]> reportDecade(@Param("type") String type,@Param("lyear")Integer lyear,@Param("uyear")Integer uyear);
//===============all data from to date to ========================================================//
	@Transactional
	@Query("select v from Voucher v where v.date between :datefrom and :dateto ")
	public List<Voucher> specificDate(@Param("datefrom") java.sql.Date datefrom,@Param("dateto")  java.sql.Date dateto);
	
	
	
	
	
	
	
	

}
