package com.sahaJwellers.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	

}
