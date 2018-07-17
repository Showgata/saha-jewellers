
package com.sahaJwellers.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mortgage-app/")
public class VoucherWebController {
	
	@RequestMapping(path= {"/voucher"})
	public String createVoucher() {
		System.out.println("voucher");
		return "voucher/create_voucher";
	}
	
	@GetMapping("/")
	public String home() {
		return "home/home";
	}
	
	@GetMapping("/customer")
	public String customer() {
		return "customer/customer";
	}
	
	@GetMapping("/expense-voucher")
	public String index() {
		return "expense/expense_voucher";
	}
	
	@GetMapping("/voucher_transaction")
	public String voucherTransaction() {
		return "voucher/transaction";
	}
	
	@GetMapping("/create_ledger")
	public String createVoucherTransaction() {
		return "voucher/create_ledger";
	}
	
	@GetMapping("/expense-transaction")
	public String expenseTransaction() {
		return "voucher/expense-transaction";
	}
}
