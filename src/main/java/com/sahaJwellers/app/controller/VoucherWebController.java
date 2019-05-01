package com.sahaJwellers.app.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/update_capital_trans/{id}")
	public String capitalUpdate(@PathVariable("id") Long id,Model model){
		model.addAttribute("menu", "capital");
		model.addAttribute("voucherId",id);
		return "capital/create_capital";
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
	
	
	@GetMapping("/update_expense/{id}")
	public String expenseUpdate(@PathVariable("id") Long id,Model model){
		model.addAttribute("menu", "expense");
		model.addAttribute("voucherId",id);
		return "expense/expense_voucher2";
	}
	
	
	
	
	@GetMapping("/settings")
	public String set() {
		return "/settings";
	}
	
	@GetMapping("/create_user")
	public String createNewUser() {
		return "/create_user";
	}
	
	
	
	
	@GetMapping("/history")
	public String transactionHistory(Model model) {
		model.addAttribute("menu","custom");
		return "custom/transaction_history";
	}
	
	@RequestMapping(value = "/update_transaction",params = { "type","id" }, method = RequestMethod.GET)
	public String updateTransaction(@RequestParam(value = "type") String type,@RequestParam("id") Long id,Model model){
		
		
		
		model.addAttribute("type",type);
		model.addAttribute("voucherId",id);
		
		if(type.equalsIgnoreCase("mortgage"))
		{
			model.addAttribute("menu", "goldmortgage");
			
			return "voucher/transaction";
		}else if(type.equalsIgnoreCase("loan_take"))
		{
			model.addAttribute("menu", "loan-take");
			
			return "loan/loan_take_trans";
		}else {
			model.addAttribute("menu", "loan-give");
			
			return "loan/loan_give_trans";
		}
	
		
		
	}
	
	
	
	@RequestMapping(value = "/specific_transaction",params = { "id","voucherId" }, method = RequestMethod.GET)
	public String updateTransaction(@RequestParam(value = "id") Long id,@RequestParam("voucherId") Long voucherId,Model model){
		model.addAttribute("menu", "custom");
		model.addAttribute("voucherId",voucherId);
		model.addAttribute("id",id);
		return "custom/transaction_history";
	}
	
	
	
	
	
	
	
	
}
