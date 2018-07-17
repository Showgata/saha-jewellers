package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

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
		if(voucher.getId() != null){
		return voucherRepository.saveAndFlush(voucher);
		} else {
		return voucherRepository.save(voucher);
		}
	}
	
	@Override
	public Optional<Voucher> findVoucherById(Long id) {
		return voucherRepository.findById(id);
	}
	
	@Override
	public void removeVoucher(Long id) {
		voucherRepository.deleteById(id);
	}
	
	@Override
	public Optional<Voucher> findVoucherByTransactionId(Transaction id){
		return voucherRepository.findVoucherByTransactionId(id);
	}
	
	@Override
	public List<Voucher> fetchAllTodaysExpenseVoucher(){
		return voucherRepository.listVoucherForTodayByType(DateUtil.atStartOfDay(new Date()), "expense");
	}
}