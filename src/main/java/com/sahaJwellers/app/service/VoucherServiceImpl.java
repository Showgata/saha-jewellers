package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Mortgage;
import com.sahaJwellers.app.model.Product;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.CustomerRepository;
import com.sahaJwellers.app.repository.MortgageRepository;
import com.sahaJwellers.app.repository.ProductRepository;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MortgageRepository mortgageRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Voucher> fetchAllVouchers(){
		return voucherRepository.findAll(new Sort(Sort.Direction.DESC, "updateTimestamp"));
	}
	
	@Override
	public Voucher saveOrUpdate(Voucher voucher) {			
		Customer customer = voucher.getCustomer();
		if(customer != null && customer.getCustomerId() != null) {
			Customer cust = customerRepository.getOne(customer.getCustomerId());
			cust.setAddress(customer.getAddress());
			cust.setCustomerName(customer.getCustomerName());
			cust.setMobile(customer.getMobile());
			cust.setNote(customer.getNote());
			cust.setReferences(customer.getReferences());
			customerRepository.save(customer);
			customer = cust;
		}
		voucher.setCustomer(customer);
		
		Mortgage mortgage = voucher.getMortgage();
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
		voucher.setMortgage(mortgage);
		
		return voucherRepository.save(voucher);
		/*}*/
	}
	
	@Override
	public Optional<Voucher> findVoucherById(Long id) {
		return voucherRepository.findById(id);
	}
	
	@Override
	public void removeVoucher(Long id) {
		voucherRepository.deleteById(id);
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
		System.out.println("date=>"+DateUtil.atStartOfDay(new Date()));
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "mortgage");
	}
	
	@Override
	public List<Voucher> fetchAllCapitalVoucherForToday(){
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "capital");
	}
	
	@Override
	public List<Voucher> fetchVoucherByDateAndType(String type, Date date){
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(date), type);
	}
	
	@Override
	public List<Voucher> fetchAllTodaysLoanGiveVoucher(){
		System.out.println("date=>"+DateUtil.atStartOfDay(new Date()));
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "loan_give");
	}
	
	@Override
	public List<Voucher> fetchAllTodaysLoanTakeVoucher(){
		System.out.println("date=>"+DateUtil.atStartOfDay(new Date()));
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "loan_take");
	}
	
	@Override
	public List<Voucher> fetchAllVoucherInDescOrder(String type){
		return voucherRepository.listVoucherByType(type);
	}
}