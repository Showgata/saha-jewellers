package com.sahaJwellers.app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.TakeLoan;



@Repository
public interface LoanTakeRepository extends JpaRepository<TakeLoan, Long> {
	
	@Query("select v from TakeLoan v where v.customerId = :id")
	public List<TakeLoan> findLoanTakeDetailsByCustomerId(@Param("id") long id);
	

	
}
