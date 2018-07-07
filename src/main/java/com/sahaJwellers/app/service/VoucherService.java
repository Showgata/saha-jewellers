package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.Voucher;

public interface VoucherService {

	Optional<Voucher> findVoucherById(Long id);

	List<Voucher> fetchAllVouchers();

	Voucher saveOrUpdate(Voucher voucher);

	void removeVoucher(Long id);

}