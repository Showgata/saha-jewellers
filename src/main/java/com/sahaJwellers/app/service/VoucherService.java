package com.sahaJwellers.app.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;

public interface VoucherService {

	Optional<Voucher> findVoucherById(Long id);
	Optional<Voucher> findVoucherBySlId(Long id);

	List<Voucher> fetchAllVouchers();

	Voucher saveOrUpdate(Voucher voucher);

	void removeVoucher(Long id,String type);

	Optional<Voucher> findVoucherByTransactionId(Transaction trans);

	List<Voucher> fetchAllTodaysExpenseVoucher();

	List<Voucher> fetchAllTodaysMortgageVoucher();

	List<Voucher> fetchAllVoucherInDescOrder(String type);

	List<Voucher> fetchAllTodaysLoanTakeVoucher();

	List<Voucher> fetchAllTodaysLoanGiveVoucher();

	List<Voucher> fetchAllCapitalVoucherForToday();

	List<Voucher> fetchVoucherByDateAndType(String type, Date date);
	
	//===================================================
	
	List<Voucher> fetchECBySpecificDate(String type, Date date);
	List<Voucher> fetchECByDateRange(String type, Date dateto,Date datefrom);
	void removeSpecificVoucherRange(Date datefrom,Date dateto);
	void removeSpecificVoucher(Date date);
	void removeSpecificVoucherByCustomer(Long id);
	List<Voucher> findVoucherByCustomerIdAndType(Long id,String type);
	public List<Voucher> findVouchersByRemainderDate(Date remainderDate,String type);
	Voucher findVoucherByTransactionIDAndType(Long id,String type);
	void updateRemainderDate(Date date,Long id);
	List<Voucher> getAllVouchers();
	
	List<Object[]> getFilteredData(Integer flag,String type);
	List<Object[]> filterById(Long id);
	List<Object[]> getFilteredDataByTypeAndId(String type,Long voucherId);
	List<Object[]> filterByType(String type);
	List<Object[]> filterByTypeAndLoanId(String type,Long id);
	
	List<Voucher> fetchAllMortgageVoucher();
	
	//For daily report
	List<Voucher> getDailyReport(String type, Date date);
	
	//For weekly report
	List<Object[]> reportWeekly(String type, Date date);
	
	//For monthly report
	List<Object[]> reportMonthly(String type,Integer month,Integer year);
	
	//For yearly report
	List<Object[]> reportYearly(String type,Integer year);
	
	//For decade report
	List<Object[]> reportDecade(String type,Integer lyear,Integer uyear);
	
	//all from date from to date to
	List<Voucher>  getdatespecific(java.sql.Date date1, java.sql.Date date2);
	
	

}