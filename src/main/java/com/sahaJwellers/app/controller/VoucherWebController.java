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
	
	@GetMapping("/expense-voucher")
	public String index() {
		return "expense/expense_voucher";
	}
	
}
