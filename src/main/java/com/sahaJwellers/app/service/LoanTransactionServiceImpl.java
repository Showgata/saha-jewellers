package com.sahaJwellers.app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.LoanUpdateDetails;
import com.sahaJwellers.app.repository.LoanTransactionRepository;
import com.sahaJwellers.app.repository.LoanUpdateDetailsRepository;

@Service
@Transactional
public class LoanTransactionServiceImpl implements LoanTransactionService {

	@Autowired
	private LoanTransactionRepository loanTransactionRepository;
	
	@Autowired
	private LoanUpdateDetailsRepository loanUpdateDetailsRepository;

	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#saveLoan(com.sahaJwellers.app.model.LoanTransaction)
	 */
	@Override
	public LoanTransaction saveLoan(LoanTransaction loan){
		
		
		
		//System.out.println("==================================== lt id ="+loan.getId());
		
		if(loan !=null && loan.getId()!=null)
		{
			LoanTransaction lt = loanTransactionRepository.getOne(loan.getId());
			
			lt.setId(loan.getId());
			lt.setDate(loan.getDate());
			
			lt.setDueAmount(loan.getDueAmount());
			lt.setFlag(loan.getFlag());
			lt.setInterest_amount(loan.getInterest_amount());
			lt.setInterestDueAmount(loan.getInterestDueAmount());
			lt.setInterestPaidAmount(loan.getInterestPaidAmount());
			lt.setInterestPreviousLoanAmount(loan.getInterestPreviousLoanAmount());
			lt.setPaidAmount(loan.getPaidAmount());
			lt.setPreviousLoanAmount(loan.getPreviousLoanAmount());
			lt.setTransaction_amount(lt.getTransaction_amount());
			lt.setType(lt.getType());
			
			System.out.println("Balance="+loan.getBalance());
			
			lt.setBalance(loan.getBalance());
			
			LoanUpdateDetails lud = new LoanUpdateDetails(lt);
			loanUpdateDetailsRepository.save(lud);
			System.out.println("lt s balance ="+lud.toString());
			return lt;
		}
		
		return loanTransactionRepository.save(loan);
	}
	
	
	
	@Override
	public LoanTransaction updateLoan(LoanTransaction loan) {
		
		//not needed
		
		
		
		
		return loanTransactionRepository.save(loan);
	}


	@Override
	public List<LoanTransaction> getTodaysLoan() {
		// TODO Auto-generated method stub
		return loanTransactionRepository.getTodaysLoan(new java.util.Date());
	}

 //rajat
	@Override
	public List<LoanTransaction> getdatespecific(java.sql.Date df1,java.sql.Date df2)
	{
		System.out.print("the dates are :"+df1+"& like :"+df2 +"end here" );
		return loanTransactionRepository.getspecific(df1,df2);
	}



	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#deleteLoanTransaction(java.lang.Long)
	 */
	@Override
	public void deleteLoanTransaction(Long id){
		loanTransactionRepository.deleteById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.LoanTransactionService#fetchLoanTransactions(java.lang.Long)
	 */
	@Override
	public List<LoanTransaction> fetchLoanTransactions(Long voucherId){
		return loanTransactionRepository.listLoanTransactionByVoucher(voucherId);
	}

	@Override
	public List<LoanTransaction> getOneLoanTransactionByVoucher(Long voucherId) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.findFirstLoanTransactionByVoucher(voucherId);
	}
	
	
	@Override
	public List<LoanTransaction> fetchAllLoanTransactions(){
		return loanTransactionRepository.findAll();
	}



	@Override
	public List<LoanTransaction> fetchAllLoanTransactionsByFlag(Integer flag) {
		return loanTransactionRepository.listLoanTransactionByFlag(flag);
	}



	@Override
	public List<Object[]> listLoanTransactionByFlagAndVoucherId(Integer flag, Long voucherId) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.listLoanTransactionByFlagAndVoucherId(flag, voucherId);
	}



	@Override
	public BigDecimal totalSumOfVoucherId(Long voucherId) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.totalSumOfVoucherId(voucherId);
	}


	@Override
	public void updateAmounts(Long voucherId, Long id, BigDecimal amnt,BigDecimal intAmnt) {
		// TODO Auto-generated method stub
		loanTransactionRepository.updateAmounts(voucherId, id, amnt,intAmnt);
		loanTransactionRepository.updateBalanceAfterUpdate(voucherId, id, amnt,intAmnt);
	}



	@Override
	public List<LoanTransaction> getRecentTransactionOfAllVouchers(Integer flag,String type) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.getRecentTransactionOfAllVouchers(flag,type);
	}



	@Override
	public void deleteAndUpdateAmount(Long id, BigDecimal paidAmt, BigDecimal paidInterestAmt, Long voucherId) {
		// TODO Auto-generated method stub
		deleteLoanTransaction(id);
		loanTransactionRepository.updateAmountsAfterDeletion(voucherId,id,paidAmt,paidInterestAmt);
		loanTransactionRepository.updateBalanceAfterDeletion(voucherId,id,paidAmt,paidInterestAmt);
		
	}
	
	
	@Override
	public BigDecimal totalSumInterestOfVoucherId(Long voucherId) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.totalSumInterestOfVoucherId(voucherId);
	}



	@Override
	public List<LoanTransaction> getRecentTransactionOfSpecificVoucher(int flag, Long voucherId) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.getRecentTransactionOfSpecificVoucher(flag, voucherId);
	}


	
	/*REPORTS*/

	@Override
	public List<LoanTransaction> getDailyLoan(int flag, String type, Date date) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.getDailyLoan(flag, type, date);
	}



	@Override
	public List<Object[]> reportWeeklyLoan(int flag, String type, Date date) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportWeeklyLoan(flag, type, date);
	}



	@Override
	public List<Object[]> reportMonthlyLoan(int flag, String type, Integer month, Integer year) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportMonthlyLoan(flag, type, month, year);
	}



	@Override
	public List<Object[]> reportYearlyLoan(int flag, String type, Integer year) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportYearlyLoan(flag, type, year);
	}



	@Override
	public List<Object[]> reportDecadeLoan(int flag, String type, Integer lyear, Integer uyear) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportDecadeLoan(flag, type, lyear, uyear);
	}


	
	@Override
	public List<Object[]> getDailyTransactionUpdate(String type, Date date) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.getDailyTransactionUpdate(type, date);
	}

	@Override
	public List<Object[]> reportWeeklyTransactionUpdate(String type, Date date) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportWeeklyTransactionUpdate(type, date);
	}

	@Override
	public List<Object[]> reportMonthlyTransactionUpdate(String type, Integer month, Integer year) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportMonthlyTransactionUpdate(type, month, year);
	}

	@Override
	public List<Object[]> reportYearlyTransactionUpdate(String type, Integer year) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportYearlyTransactionUpdate(type, year);
	}

	@Override
	public List<Object[]> reportDecadeTransactionUpdate(String type, Integer lyear, Integer uyear) {
		// TODO Auto-generated method stub
		return loanTransactionRepository.reportDecadeTransactionUpdate(type, lyear, uyear);
	}
	
	
	
	
	
	
	
}
