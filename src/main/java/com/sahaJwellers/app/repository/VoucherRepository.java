package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	@Query("select v from Voucher v where v.transaction = :transaction")
	public Optional<Voucher> findVoucherByTransactionId(@Param("transaction") Transaction transaction);
	
	@Query("select v from Voucher v where v.date >= :date AND v.type = :type ORDER BY v.updateTimestamp DESC")
	public List<Voucher> listVoucherForTodayByType(@Param("date") Date date,@Param("type") String type);
	
	@Query("select v from Voucher v where v.type = :type ORDER BY v.updateTimestamp DESC")
	public List<Voucher> listVoucherByType(@Param("type") String type);
	
}
