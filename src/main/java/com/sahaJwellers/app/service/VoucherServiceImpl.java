package com.sahaJwellers.app.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Mortgage;
import com.sahaJwellers.app.model.Product;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.CustomerRepository;
import com.sahaJwellers.app.repository.MortgageRepository;
import com.sahaJwellers.app.repository.ProductRepository;
import com.sahaJwellers.app.repository.TransactionRepository;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MortgageRepository mortgageRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	
	@Override
	public List<Voucher> fetchAllVouchers(){
		return voucherRepository.findAll(new Sort(Sort.Direction.DESC, "updateTimestamp"));
	}
	
	
	
	@Override
	public Voucher saveOrUpdate(Voucher voucher) {
			
		System.out.println("SlId-->"+voucher.getSl_id()+"\\ id"+voucher.getId());
		
		if(voucher.getSl_id()==null && voucher.getId()==null) {
			
			Long id = voucherRepository.getLastVoucherIdByType(voucher.getType());
			
			
			

			if(id == null || id == 0) {
				id =1l;
				}
			else {id++;}
			
			Customer customer = voucher.getCustomer();
			Mortgage mortgage = voucher.getMortgage();
			Transaction transaction = voucher.getTransaction();
			
			Customer modCust = setCustomerDetails(customer);
			Mortgage modMort = setMortgageDetails(mortgage);
			Transaction modTrans =setTransactionDetails(transaction);
			
			voucher.setCustomer(modCust);
			voucher.setMortgage(modMort);
			voucher.setTransaction(modTrans);
			
			if(voucher.getSerial() == null) voucher.setSerial("-1"); 
			
				
				
			voucher.setId(id);
			
			System.out.println("if null SlId-->"+voucher.getSl_id()+"\\ id"+voucher.getId());
		}else {
			
			Voucher newVoucher = voucherRepository.getOne(voucher.getSl_id());
			newVoucher.setDate(voucher.getDate());
			
			Customer customer = voucher.getCustomer();
			Mortgage mortgage = voucher.getMortgage();
			Transaction transaction = voucher.getTransaction();
			
			Customer modCust = setCustomerDetails(customer);
			Mortgage modMort = setMortgageDetails(mortgage);
			Transaction modTrans =setTransactionDetails(transaction);
			
			newVoucher.setCustomer(modCust);
			newVoucher.setMortgage(modMort);
			newVoucher.setTransaction(modTrans);
			
			voucher = newVoucher;
			
			System.out.println("not null SlId-->"+voucher.getSl_id()+"\\ id"+voucher.getId());
		}
		
		
		return voucherRepository.save(voucher);
		/*}*/
	}
	
	
	private Customer setCustomerDetails(Customer customer) {
		
		if(customer != null && customer.getCustomerId() != null) {
			Customer cust = customerRepository.findByCId(customer.getCustomerId());
			cust.setAddress(customer.getAddress());
			cust.setCustomerName(customer.getCustomerName());
			cust.setMobile(customer.getMobile());
			customerRepository.save(cust);
			customer = cust;
		}
		
		return customer;
	}
	
	
	
private Mortgage setMortgageDetails(Mortgage mortgage) {
		
	if(mortgage != null && mortgage.getMortgageId() != null) {	
		Mortgage mort = mortgageRepository.getOne(mortgage.getMortgageId());
		mort.setAna(mortgage.getAna());
		mort.setBori(mortgage.getBori());
		mort.setGram(mortgage.getGram());
		mort.setInterestRate(mortgage.getInterestRate());
		mort.setLoanAmount(mortgage.getLoanAmount());
		mort.setPoint(mortgage.getPoint());
		mort.setRatti(mortgage.getRatti());
		Product product = mortgage.getProduct();
		
		if(product != null && product.getProductId() != null) {
			Product prod = productRepository.getOne(product.getProductId());
			prod.setProductName(product.getProductName());
			prod.setQuantity(product.getQuantity());
			productRepository.save(prod);
			product = prod;
			
			}
		mort.setProduct(product);
		mortgageRepository.save(mort);
		mortgage = mort;
		}
		
		return mortgage;
	}



	private Transaction setTransactionDetails(Transaction transaction)
	{
		if(transaction != null && transaction.getId()!=null) {
			
			Transaction trans = transactionRepository.getOne(transaction.getId());
			trans.setAmount(transaction.getAmount());
			trans.setId(transaction.getId());
			
			if(transaction.getAccount()!=null) {
				trans.setAccount(transaction.getAccount());
			}
			
			transactionRepository.save(trans);
			transaction=trans;
		}
		
		return transaction;
	}
	
	
	
	@Override
	public List<Voucher> getAllVouchers() {
		return voucherRepository.findAll();
	}



	@Override
	public Optional<Voucher> findVoucherById(Long id) {
		return voucherRepository.findByVoucherId(id);
	}
	
	@Override
	public Optional<Voucher> findVoucherBySlId(Long slid) {
		return voucherRepository.findByVoucherSlId(slid);
	}
	
	@Override
	public void removeVoucher(Long id,String type) {
		voucherRepository.deleteVoucherByVoucherId(id,type);
	}
	
	@Override
	public Optional<Voucher> findVoucherByTransactionId(Transaction id){
		return voucherRepository.findVoucherByTransactionId(id);
	}
	
	@Override
	public List<Voucher> fetchAllTodaysExpenseVoucher(){
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "expense");
	}
	
	@Override
	public List<Voucher> fetchAllTodaysMortgageVoucher(){
		return voucherRepository.listVoucherForTodayByType(new java.util.Date(), "mortgage");
	}
	
	@Override
	public List<Voucher> fetchAllMortgageVoucher(){
		return voucherRepository.listVoucherByType("mortgage");
	}
	
	@Override
	public List<Voucher> fetchAllCapitalVoucherForToday(){
		return voucherRepository.listVoucherForTodayByType(new java.util.Date(), "capital");
	}
	
	@Override
	public List<Voucher> fetchVoucherByDateAndType(String type, Date date){
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(date), type);
	}
	
	/*Actually fetches all the loan give irrespective of date*/
	@Override
	public List<Voucher> fetchAllTodaysLoanGiveVoucher(){
		System.out.println("date=>"+DateUtil.atStartOfDay(new Date()));
		return voucherRepository.listVoucherForTodayByType(new java.util.Date(), "loan_give");
	}
	
	@Override
	public List<Voucher> fetchAllTodaysLoanTakeVoucher(){
		System.out.println("date=>"+DateUtil.atStartOfDay(new Date()));
		return voucherRepository.listVoucherForTodayByType(new java.util.Date(), "loan_take");
	}
	
	@Override
	public List<Voucher> fetchAllVoucherInDescOrder(String type){
		return voucherRepository.listVoucherByType(type);
	}
	
	//==================================================================================
	
	//EXPENSES
	@Override	
	public List<Voucher> fetchECBySpecificDate(String type, Date date)
	{
		return voucherRepository.listExpensesBySpecificDate(date, type);
		
	}
	
	//EXPENSES
	@Override	
	public List<Voucher> fetchECByDateRange(String type, Date dateto,Date datefrom)
	{
		return voucherRepository.listExpensesByDateRange(type,dateto,datefrom);
		
	}


	//====================================================================================
	
	// deleting voucher by date
	@Override
	public void removeSpecificVoucherRange(Date datefrom, Date dateto) {
		voucherRepository.deleteSpecificVoucherRange(datefrom, dateto);
		
	}



	@Override
	public void removeSpecificVoucher(Date date) {
		voucherRepository.deleteSpecificVoucher(date);
		
	}



	@Override
	public void removeSpecificVoucherByCustomer(Long id) {
		voucherRepository.deleteVoucherByCustomerId(id);
		
	}



	@Override
	public List<Voucher> findVoucherByCustomerIdAndType(Long id, String type) {
		// TODO Auto-generated method stub
		return voucherRepository.findVoucherByCustomerIdAndType(id,type);
	}
	
	
	
	@Override
	public List<Voucher> findVouchersByRemainderDate(Date reminderDate,String type) {
		
		List<Transaction> allTrans = transactionRepository.getTransactionByRemainderDate(reminderDate);
		List<Voucher> allVouchers = new ArrayList<Voucher>();
		
		
		if(!allTrans.isEmpty()) {
		for (Transaction trans : allTrans) {
		    
			Voucher v = findVoucherByTransactionIDAndType(trans.getId(),type);
			
			allVouchers.add(v);
			
		}
		
		}
		return allVouchers;
		
		
	}



	@Override
	public Voucher findVoucherByTransactionIDAndType(Long id,String type) {
		// TODO Auto-generated method stub
		return voucherRepository.findVoucherByTransactionIdAndType(id,type);
	}



	@Override
	public void updateRemainderDate(Date date,Long id) {
		
		
		Optional<Transaction> old = transactionRepository.findById(id);
		
		Transaction trans = old.get();
		
		
		Transaction t = new Transaction();
		t.setAccount(trans.getAccount());
		t.setAddress(trans.getAddress());
		t.setAmount(trans.getAmount());
		t.setCustomerId(trans.getCustomerId());
		t.setExpense(trans.getExpense());
		t.setId(trans.getId());
		t.setMobile(trans.getMobile());
		t.setNote(trans.getNote());
		t.setReminderDate(date);
		t.setStorage(t.getStorage());
		t.setTransactionDate(trans.getTransactionDate());
		t.setTransactionSerial(trans.getTransactionSerial());
		
		transactionRepository.save(t);
		
	}


	//=========================================================================================
	@Override
	public List<Object[]> getFilteredData(Integer flag, String type) {
		// TODO Auto-generated method stub
		
		return voucherRepository.filteredData(flag, type);
	
	}



	@Override
	public List<Object[]> filterById(Long id) {
		// TODO Auto-generated method stub
		return voucherRepository.filterById(id);
	}
	
	

	@Override
	public List<Object[]> filterByType(String type) {
		
		return voucherRepository.filterByType(type);
	}



	@Override
	public List<Object[]> filterByTypeAndLoanId(String type, Long id) {
		// TODO Auto-generated method stub
		return voucherRepository.updateByIdAndType(type,id);
	}

	

	//For daily report
	@Override
	public List<Voucher> getDailyReport(String type, Date date) {
		return voucherRepository.getVoucherByDateAndType(date, type);
	}



	//For Weekly report
	@Override
	public List<Object[]> reportWeekly(String type, Date date) {
		// TODO Auto-generated method stub
		return voucherRepository.reportWeekly(type, date);
	}



	@Override
	public List<Object[]> getFilteredDataByTypeAndId(String type, Long voucherId) {
		// TODO Auto-generated method stub
		return voucherRepository.filteredDataByIdAndType(type,voucherId);
	}


	//For Monthly report
	@Override
	public List<Object[]> reportMonthly(String type, Integer month, Integer year) {
		// TODO Auto-generated method stub
		return voucherRepository.reportMonthly(type, month, year);
	}


	//For Yearly report
	@Override
	public List<Object[]> reportYearly(String type,Integer year) {
		// TODO Auto-generated method stub
		return voucherRepository.reportYearly(type, year);
	}


	//For Decade report
	@Override
	public List<Object[]> reportDecade(String type, Integer lyear, Integer uyear) {
		// TODO Auto-generated method stub
		return voucherRepository.reportDecade(type, lyear, uyear)
;
	}



	
	//=====================Date from to Date to ===============================================//
	
	@Override
	public List<Voucher> getdatespecific(java.sql.Date date1, java.sql.Date date2)
	{
		return voucherRepository.specificDate(date1,date2);
	}

	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
}