package com.sahaJwellers.app.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.LoanTransactionService;
import com.sahaJwellers.app.service.TransactionService;
import com.sahaJwellers.app.service.VoucherService;

@RestController
@RequestMapping("/mortgage-app/api/voucher")
public class VoucherRestController {

	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LoanTransactionService loanTransactionService;
	
	@GetMapping("/")
	public List<Voucher> findAll() {
		List<Voucher> vouchers = voucherService.fetchAllVouchers();
		return vouchers;
	}
	
	@GetMapping("/loan/{voucherId}")
	public List<LoanTransaction> fetchAllLoanTransaction(@PathVariable("voucherId") Long voucherId){
		return loanTransactionService.fetchLoanTransactions(voucherId);
	}
	
	@PostMapping("/loan/")
	public LoanTransaction saveLoan(@RequestBody LoanTransaction loanTransaction){
		return loanTransactionService.saveLoan(loanTransaction);
	}
	
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping("/default")
	public Voucher defaultValue() {
		return new Voucher();
	}
	
	@GetMapping("/expense-for-today")
	public List<Voucher> findTodaysExpenseVoucher(){
		return voucherService.fetchAllTodaysExpenseVoucher();
	}
	
	@GetMapping("/expense")
	public List<Voucher> findExpenseVoucher(){
		return voucherService.fetchAllVoucherInDescOrder("expense");
	}
	
	@GetMapping("/expense/{id}")
	public List<Voucher> findAllExpenseByVoucherId(@PathVariable("id") Long id){
		Voucher voucher = voucherService.findVoucherById(id).get();
		return voucherService.fetchVoucherByDateAndType("expense", voucher.getUpdateTimestamp());
	}
	
	@GetMapping("/capital")
	public List<Voucher> findAllCapital(){
		return voucherService.fetchAllVoucherInDescOrder("capital");
	}
	
	@GetMapping("/capital/{id}")
	public List<Voucher> findAllCapitalByVoucherId(@PathVariable("id") Long id){
		Voucher voucher = voucherService.findVoucherById(id).get();
		return voucherService.fetchVoucherByDateAndType("capital", voucher.getUpdateTimestamp());
	}

	@GetMapping("/capital-for-today")
	public List<Voucher> fidAllCapitalForToday(){
		return voucherService.fetchAllCapitalVoucherForToday();
	}
	
	
	@GetMapping("/mortgage-for-today")
	public List<Voucher> findTodaysMortgageVoucher(){
		return voucherService.fetchAllTodaysMortgageVoucher();
	}
	
	

	@PostMapping("/")
	public Voucher saveVoucher(@RequestBody Voucher voucher) {
		System.out.println(voucher);
		return voucherService.saveOrUpdate(voucher);
	}

	@GetMapping("/{id}")
	public Voucher findVoucherById(@PathVariable("id") Long id) {
		Optional<Voucher> voucher = voucherService.findVoucherById(id);
		if (voucher.isPresent()) {
			return voucher.get();
		} else {
			return new Voucher();
		}
	}

	@GetMapping("/transaction/{transactionId}")
	public Voucher findVoucherByTransactionId(@PathVariable("transactionId") Long transactionId) {
		Optional<Transaction> transaction = transactionService.findTransactionById(transactionId);
		Optional<Voucher> voucher = voucherService.findVoucherByTransactionId(transaction.get());
		if (voucher.isPresent()) {
			return voucher.get();
		} else {
			return new Voucher();
		}

	}

	@PostMapping("/{id}")
	public void deleteVoucher(@PathVariable("id") Long id) {
		voucherService.removeVoucher(id);
	}
}
