package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.ExpenseInfo;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.repository.ExpenseInfoRepository;
import com.sahaJwellers.app.repository.ExpenseRepository;
import com.sahaJwellers.app.util.DateUtil;



@Service
@Transactional
public class ExpenseServiceImp implements ExpenseService {

	@Autowired
	private ExpenseInfoRepository expenseInfoRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public Expense saveExpense(Expense e) {
		// TODO Auto-generated method stub
		
		if(e.getId()==null) {
			
			Long id = expenseRepository.getLastExpenseId();

			if(id == null || id == 0) {id =1l;}
			else {id++;}
			
			e.setId(id);
			
		}else {
		
		
		if(e!=null) {
			
			Expense e1 =expenseRepository.getOne(e.getId());
			e1.setExpenseHead(e.getExpenseHead());
			e1.setExpenseInfoId(e.getExpenseInfoId());
			e1.setAmount(e.getAmount());
			e1.setDate(e.getDate());
			e1.setNote(e.getNote());
			e1.setId(e.getId());
			
			
			return e1;

		}
		
		}
		
		return expenseRepository.save(e) ;
	}

	@Override
	public ExpenseInfo saveExpenseInfo(ExpenseInfo ei) {
		
		
			if(ei!=null && ei.getId()!=null) {
			
			ExpenseInfo e1 =expenseInfoRepository.getOne(ei.getId());
			e1.setAddress(ei.getAddress());
			e1.setExpenseDescription(ei.getExpenseDescription());
			e1.setMobileNo(ei.getMobileNo());
			e1.setExpenseName(e1.getExpenseName());
			e1.setId(ei.getId());
					
			return e1;

		}
		
		
		
		
		
		return expenseInfoRepository.save(ei);
	}

	@Override
	public void deleteExpense(Long id) {
		// TODO Auto-generated method stub
		expenseRepository.deleteById(id);
		
		
	}

	@Override
	public void deleteExpenseInfo(Long id) {
		// TODO Auto-generated method stub
		expenseInfoRepository.deleteById(id);
		
	}

	@Override
	public List<ExpenseInfo> fetchAllExpenseInfo() {
		// TODO Auto-generated method stub
		return expenseInfoRepository.findAll();
	}

	@Override
	public List<Expense> fetchAllExpense() {
		// TODO Auto-generated method stub
		return expenseRepository.findAll();
	}

	@Override
	public Expense getExpenseById(Long voucherId) {
		// TODO Auto-generated method stub
		return expenseRepository.getOne(voucherId);
	}
	
	
	@Override
	public ExpenseInfo getExpenseInfoById(Long voucherId) {
		// TODO Auto-generated method stub
		return expenseInfoRepository.getOne(voucherId);
	}

	@Override
	public Expense updateExpense(Expense e) {
		// TODO Auto-generated method stub
		
		
		
		
		return null;
	}

	@Override
	public List<Object[]> getAllExpenseDetails() {
		// TODO Auto-generated method stub
		return expenseRepository.getAllExpenseDetails();
	}

	@Override
	public List<Object[]> getOneExpenseDetail(Long id) {
		// TODO Auto-generated method stub
		return expenseRepository.getOneExpenseDetails(id);
	}

	@Override
	public List<Object[]> listExpensesBySpecificDate(Date date) {
		// TODO Auto-generated method stub
		return expenseRepository.listExpensesBySpecificDate(date);
	}

	@Override
	public List<Object[]> listExpensesByDateRange(Date dateto, Date datefrom) {
		// TODO Auto-generated method stub
		return expenseRepository.listExpensesByDateRange(dateto, datefrom);
	}

	@Override
	public List<Expense> listExpenseForToday() {
		// TODO Auto-generated method stub
		return expenseRepository.listExpenseForToday(new java.util.Date()); //check here
	}

	@Override
	public List<Object[]> reportWeeklyExpense(Date date) {
		// TODO Auto-generated method stub
		return expenseRepository.reportWeeklyExpense(date);
	}

	@Override
	public List<Object[]> reportMonthlyExpense(Integer month, Integer year) {
		// TODO Auto-generated method stub
		return expenseRepository.reportMonthlyExpense(month, year);
	}

	@Override
	public List<Object[]> reportYearlyExpense(Integer year) {
		// TODO Auto-generated method stub
		return expenseRepository.reportYearlyExpense(year);
	}

	@Override
	public List<Object[]> reportDecadeExpense(Integer lyear, Integer uyear) {
		// TODO Auto-generated method stub
		return expenseRepository.reportDecadeExpense(lyear, uyear);
	}

	
	//expense with from and to
	
	@Override
	public List<Expense> getdatespecific(java.sql.Date date1, java.sql.Date date2)
	{
		return expenseRepository.getdatespecific(date1, date2);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#saveLoan(com.sahaJwellers.app.model.LoanTransaction)
	 */
	
	
}
