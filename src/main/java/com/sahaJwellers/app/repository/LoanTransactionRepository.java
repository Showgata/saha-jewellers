package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.LoanTransaction;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Long>{
	
	@Query("select t from LoanTransaction t where t.date >= :date  ORDER BY t.updateTimestamp DESC")
	public List<LoanTransaction> listLoanTransactionByDate(@Param("date") Date date);	

	@Query("select t from LoanTransaction t where t.voucherId = :voucherId  ORDER BY t.updateTimestamp DESC")
	public List<LoanTransaction> listLoanTransactionByVoucher(@Param("voucherId") Long voucherId);
}
