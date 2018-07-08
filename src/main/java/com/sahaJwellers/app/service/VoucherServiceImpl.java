package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.VoucherRepository;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Override
	public List<Voucher> fetchAllVouchers(){
		return voucherRepository.findAll(new Sort(Sort.Direction.DESC, "updateTimestamp"));
	}
	
	@Override
	public Voucher saveOrUpdate(Voucher voucher) {
		return voucherRepository.save(voucher);
	}
	
	@Override
	public Optional<Voucher> findVoucherById(Long id) {
		return voucherRepository.findById(id);
	}
	
	@Override
	public void removeVoucher(Long id) {
		voucherRepository.deleteById(id);
	}
	
}
