package com.sahaJwellers.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.repository.TransactionRepository;
import com.sahaJwellers.app.repository.VoucherRepository;
import com.sahaJwellers.app.util.DateUtil;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#fetchAll()
	 */
	@Override
	public List<Transaction> fetchAll(){
		return transactionRepository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#saveTransaction(org.sahaJwellers.app.model.Transaction)
	 */
	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#removeTransaction(java.lang.Long)
	 */
	@Override
	public void removeTransaction(Long id) {
		transactionRepository.deleteById(id);
	}
	
	/* (non-Javadoc)
	 * @see org.sahaJwellers.app.service.TransactionService#findTransactionById(java.lang.Long)
	 */
	@Override
	public Optional<Transaction> findTransactionById(Long id) {
		return transactionRepository.findById(id);
	}
	
	@Override
	public List<Transaction> findAllTransaction(Date date){ 
		return transactionRepository.findAllTransactionByDate(date);
	}

	//=================================================MODIFICATION
	@Override
	public void removeSpecificTransactionsRange(Date dateFrom, Date dateTo) {
		transactionRepository.deleteSpecificTransactionsRange(dateFrom, dateTo);
		
		//also delete voucher bcs it contains transaction id
		voucherRepository.deleteSpecificVoucherRange(dateFrom,dateTo);
		
	}
	
	
	@Override
	public void removeSpecificTransaction(Date date) 
	{
		
		transactionRepository.deleteSpecificTransaction(date);
		voucherRepository.deleteSpecificVoucher(date);
		
	}
	
	
	@Override
	public void removeByTransactionNo(String serialNo) 
	{
		Transaction ts = getTransactionBySerial(serialNo);
		
		if(ts !=null)
		{
			Long id = ts.getId();
			voucherRepository.deleteVoucherByTransactionId(id);
			transactionRepository.deleteByTransactionNo(serialNo);
			
		}
		
		
	}
	
	
	
	@Override
	public void removeByCustomerId(Long id) 
	{
		voucherRepository.deleteVoucherByCustomerId(id);
		transactionRepository.deleteByCustomerID(id);
		
	}

	@Override
	public Transaction getTransactionBySerial(String serial) {
		return transactionRepository.getTransactionBySerial(serial);
	}

	@Override
	public List<Transaction> getTransactionByRemainderDate(Date reminderDate) {
		
		return transactionRepository.getTransactionByRemainderDate(reminderDate);
	}
	
	
	
	
	
	
	
	
}
