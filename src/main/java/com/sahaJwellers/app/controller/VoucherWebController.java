package com.sahaJwellers.app.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mortgage-app/web/")
public class VoucherWebController {
	
	@ModelAttribute
	public void addCommons(Model model){
		model.addAttribute("menu", "home");
	}
	
	
	@RequestMapping(path= {"/voucher"})
	public String createVoucher(Model model) {
		System.out.println("voucher");
		model.addAttribute("menu", "goldmortgage");
		return "voucher/create_voucher";
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("menu", "home");
		return "home/home";
	}
	
	@GetMapping("/customer")
	public String customer(Model model) {
		model.addAttribute("menu", "customer");
		return "customer/customer";
	}
	
	@GetMapping("/expense-voucher")
	public String index(Model model) {
		model.addAttribute("menu", "expense");
		return "expense/expense_voucher";
	}
	
	@GetMapping("/voucher_transaction")
	public String voucherTransaction(Model model) {
		model.addAttribute("menu", "goldmortgage");
		return "voucher/transaction";
	}
	
	@GetMapping("/create_ledger")
	public String createVoucherTransaction(Model model) {
		model.addAttribute("menu", "custom");
		return "voucher/create_ledger";
	}
	
	@GetMapping("/expense-transaction")
	public String expenseTransaction(Model model) {
		model.addAttribute("menu", "expense");
		return "expense/expense_transaction";
	}
	
	@GetMapping("/loan-give")
	public String createLoanGive(Model model) {
		model.addAttribute("menu", "loan-give");
		return "loan/create_loan_give";
	}
	
	@GetMapping("/loan-take")
	public String createLoanTake(Model model) {
		model.addAttribute("menu", "loan-take");
		return "loan/create_loan_take";
	}
	
	@GetMapping("/loan-give-transaction")
	public String createLoanGiveTransaction(Model model) {
		model.addAttribute("menu", "loan-give");
		return "loan/loan_give_trans";
	}
	
	@GetMapping("/loan-take-transaction")
	public String createLoanTakeTransaction(Model model) {
		model.addAttribute("menu", "loan-take");
		return "loan/loan_take_trans";
	}
	
	@GetMapping("/daily")
	public String dailyReport(Model model) {
		model.addAttribute("menu", "report");
		return "report/daily";
	}
	
	@GetMapping("/monthly")
	public String monthlyReport(Model model) {
		model.addAttribute("menu", "report");
		return "report/monthly";
	}
	@GetMapping("/decade")
	public String decadeReport(Model model) {
		model.addAttribute("menu", "report");
		return "report/decade";
	}
	
	@GetMapping("/weekly")
	public String weeklyReport(Model model) {
		model.addAttribute("menu", "report");
		return "report/weekly";
	}
	
	@GetMapping("/yearly")
	public String yearlyReport(Model model) {
		model.addAttribute("menu", "report");
		return "report/yearly";
	}
	@GetMapping("/create_capital")
	public String capitalReport(Model model) {
		model.addAttribute("menu", "capital");
		return "capital/create_capital";
	}
	@GetMapping("/capital_trans")
	public String ctransReport(Model model) {
		model.addAttribute("menu", "capital");
		return "capital/create_capital_trans";
	}
	@GetMapping("/cash_ledger")
	public String cashLedger(Model model) {
		model.addAttribute("menu","custom");
		return "custom/cash_ledger";
	}
	@GetMapping("/custom_report")
	public String customReport(Model model) {
		model.addAttribute("menu","custom");
		return "custom/custom_report";
	}
	@GetMapping("/customer_credit_list")
	public String customerCreditList(Model model) {
		model.addAttribute("menu","custom");
		return "custom/customer_credit_list";
	}
	@GetMapping("/exp")
	public String exp(Model model) {
		model.addAttribute("menu","expense");
		return "expense/expense_voucher2";
	}
	@GetMapping("/settings")
	public String set() {
		return "/settings";
	}
}
