package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.LoanTakeTransaction;
import com.sahaJwellers.app.model.LoanTransaction;

public interface LoanTakeTransactionRepository extends JpaRepository<LoanTakeTransaction, Long>{
	
	@Query("select t from LoanTakeTransaction t where t.date >= :date  ORDER BY t.updateTimestamp DESC")
	public List<LoanTakeTransaction> listLoanTransactionByDate(@Param("date") Date date);	

	@Query("select t from LoanTakeTransaction t where t.voucherId = :voucherId  ORDER BY t.updateTimestamp DESC")
	public List<LoanTakeTransaction> listLoanTransactionByVoucher(@Param("voucherId") Long voucherId);
	
	@Query("select t from LoanTakeTransaction t where t.id = :id  ORDER BY t.updateTimestamp DESC")
	public LoanTakeTransaction fetchTakeLoanByTransactionID(@Param("id") Long id);
}
