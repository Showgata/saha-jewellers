package com.sahaJwellers.app.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Mortgage;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.service.TransactionService;
import com.sahaJwellers.app.util.DateUtil;

@RestController
@RequestMapping("/modgage-app/api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/")
	public List<Transaction> findAllTransaction() {
		return transactionService.fetchAll();
	}
	
	@GetMapping("/latestTransactions")
	public List<Transaction> fetchAllTransaction(){
		Date date = DateUtil.dateUtil.atStartOfDay(new Date());
		return transactionService.findAllTransaction(date);
	}
	
	@GetMapping("/default")
	public Transaction defaultValue() {
		Transaction t = new Transaction();
		t.setTransactionDate(new Date());
		t.setReminderDate(new Date());
		t.setStorage(1);
		Mortgage m = new Mortgage();
		m.setAna(1.1);
		
		return new Transaction();
	}
	
	@GetMapping("/{id}")
	public Transaction findTransactionById(@PathVariable("id") Long id) {
		Optional<Transaction> transaction = transactionService.findTransactionById(id);
		if(transaction.isPresent()) {
			return transaction.get();
		} else {
			return new Transaction();
		}
	}
	
	@PostMapping("/")
	public Transaction saveTransaction(@RequestBody Transaction transaction) {
		return transactionService.saveTransaction(transaction);
	}
	
	@PostMapping("/{id}")
	public void deleteTransaction(@PathVariable("id") Long id) {
		transactionService.removeTransaction(id);
	}
	
	//===========================================MODIFICATION==========
	
	//remove by specific date
	@RequestMapping(value="/remove",params= {"date"},method=RequestMethod.POST)
	public void deleteSpecificTransaction(@RequestParam(value="date") String date) {
		
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date df1 =format.parse(date);
			System.out.println("date : "+df1);
			transactionService.removeSpecificTransaction(df1);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	//remove by date from and to
	@RequestMapping(value="/remove",params = {"datefrom", "dateto"}, method=RequestMethod.POST)
	public void deleteSpecificTransactionsRange(@RequestParam(value="datefrom") String dateFrom
			,@RequestParam(value="dateto") String dateTo) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date df1 =format.parse(dateFrom);
			Date df2 =format.parse(dateTo);
			
			System.out.println("==== dateTo : "+df1+" date From : "+df2 +"========");
			
			transactionService.removeSpecificTransactionsRange(df1, df2);
			
		} catch (ParseException e) {e.printStackTrace();}
		
		
	}
	
	
	//remove by specific serial no
	@RequestMapping(value = "/remove", params = { "number" }, method = RequestMethod.POST)
	public void deleteByTransactionNo(@RequestParam(value = "number") String number) {

		System.out.println("number : " + number);
		
		transactionService.removeByTransactionNo(number);

	}
	
	
	//deleting by customer id
	@PostMapping("/id/{id}")
	public void removeByCustomerId(@PathVariable("id") Long id) {
		transactionService.removeByCustomerId(id);
	}
	
	
	/*
	//remove by specific id
		@RequestMapping(value = "/remove", params = { "number" }, method = RequestMethod.POST)
		public void deleteByTransactionId(@RequestParam(value = "number") String number) {

			System.out.println("number : " + number);
			
			transactionService.removeByTransactionNo(number);

		}
	
	*/
}
