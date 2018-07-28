package com.sahaJwellers.app.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.VoucherService;

@RestController
@RequestMapping("/mortgage-app/api/loan")
public class LoanRestController {

	@Autowired
	private VoucherService voucherService;
	
	@GetMapping("/loan-give-today")
	public List<Voucher> findTodayLoanGiveVoucher(){
		return voucherService.fetchAllTodaysLoanGiveVoucher();
	}
	
	@GetMapping("/loan-take-today")
	public List<Voucher> findTodayLoanTakeVoucher(){
		return voucherService.fetchAllTodaysLoanTakeVoucher();
	}
	
	
}
