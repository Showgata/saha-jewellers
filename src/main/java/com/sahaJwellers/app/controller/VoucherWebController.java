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
		return "expense/expense_transaction";
	}
	
	@GetMapping("/loan-give")
	public String createLoanGive() {
		return "loan/create_loan_give";
	}
	
	@GetMapping("/loan-take")
	public String createLoanTake() {
		return "loan/create_loan_take";
	}
	
	@GetMapping("/loan-give-transaction")
	public String createLoanGiveTransaction() {
		return "loan/loan_give_trans";
	}
	
	@GetMapping("/loan-take-transaction")
	public String createLoanTakeTransaction() {
		return "loan/loan_take_trans";
	}
	
	@GetMapping("/daily")
	public String dailyReport() {
		return "report/daily";
	}
	
	@GetMapping("/monthly")
	public String monthlyReport() {
		return "report/monthly";
	}
	@GetMapping("/decade")
	public String decadeReport() {
		return "report/decade";
	}
	
	@GetMapping("/weekly")
	public String weeklyReport() {
		return "report/weekly";
	}
	
	@GetMapping("/yearly")
	public String yearlyReport() {
		return "report/yearly";
	}
	@GetMapping("/create_capital")
	public String capitalReport() {
		return "capital/create_capital";
	}
	@GetMapping("/capital_trans")
	public String ctransReport() {
		return "capital/create_capital_trans";
	}
	@GetMapping("/cash_ledger")
	public String cashLedger() {
		return "custom/cash_ledger";
	}
	@GetMapping("/custom_report")
	public String customReport() {
		return "custom/custom_report";
	}
	@GetMapping("/customer_credit_list")
	public String customerCreditList() {
		return "custom/customer_credit_list";
	}
}
