package com.sahaJwellers.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahaJwellers.app.model.ExpenseInfo;

public interface ExpenseInfoRepository extends JpaRepository<ExpenseInfo, Long>{
	
	
}
