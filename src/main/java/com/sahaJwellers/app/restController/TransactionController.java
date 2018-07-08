package com.sahaJwellers.app.restController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Mortgage;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.service.TransactionService;

@RestController
@RequestMapping("/modgage-app/api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/")
	public List<Transaction> findAllTransaction() {
		return transactionService.fetchAll();
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
}
