package com.sahaJwellers.app.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.VoucherService;

@RestController
@RequestMapping("/modgage-app/api/voucher")
public class VoucherRestController {
	
	@Autowired
	private VoucherService voucherService;
	
	@GetMapping("/")
	public List<Voucher> findAll(){
		return voucherService.fetchAllVouchers();
	}
	
	@GetMapping("/default")
	public Voucher defaultValue() {
		return new Voucher();
	}
	
	@PostMapping("/")
	public Voucher saveVoucher(@RequestBody Voucher voucher) {
		return voucherService.saveOrUpdate(voucher);
	}
	
	@GetMapping("/{id}")
	public Voucher findVoucherById(@PathVariable("id") Long id) {
		Optional<Voucher> voucher = voucherService.findVoucherById(id);
		if(voucher.isPresent()) {
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
