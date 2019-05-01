package com.sahaJwellers.app.restController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.LoanTakeTransaction;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.LoanUpdateDetails;
import com.sahaJwellers.app.model.TakeLoan;
import com.sahaJwellers.app.model.Transaction;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.LoanTakeService;
import com.sahaJwellers.app.service.LoanTakeTransactionService;
import com.sahaJwellers.app.service.LoanTransactionService;
import com.sahaJwellers.app.service.LoanUpdateDetailsService;
import com.sahaJwellers.app.service.TransactionService;
import com.sahaJwellers.app.service.VoucherService;

@RestController
@RequestMapping("/mortgage-app/api/voucher")
public class VoucherRestController {

	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LoanTransactionService loanTransactionService;
	
	@Autowired
	private LoanUpdateDetailsService loanUpdateDetailsService;
	
	/*
	@RequestMapping(value = "/filter", params = { "flag","type" }, method = RequestMethod.GET)
	public List<Voucher> getFilteredData(@RequestParam(value = "flag") String flag, @RequestParam(value = "type") String type) {
		
		//System.out.println("======================= flag"+flag+"============= type="+type);
		
		List<Voucher> vouchers = voucherService.fetchAllVouchers();
		return vouchers;
	}*/
	
	
	@GetMapping("/")
	public List<Voucher> findAll() {
		List<Voucher> vouchers = voucherService.fetchAllVouchers();
		return vouchers;
	}
	
	//For custom report
	@RequestMapping(value = "/filter",params = { "flag","type" }, method = RequestMethod.GET)
	public List<Object[]> getAll(@RequestParam(value = "flag") Integer flag,@RequestParam(value = "type") String type) {
		List<Object[]> vouchers = voucherService.getFilteredData(flag,type);
		return vouchers;
	}
	
	
	//For custom report
		@RequestMapping(value = "/filterByTypeAndId",params = {"voucherId","type" }, method = RequestMethod.GET)
		public List<Object[]> getAllByTypeAndId(@RequestParam(value = "type") String type
				,@RequestParam(value = "voucherId") Long voucherId) {
			
			List<Object[]> vouchers = voucherService.getFilteredDataByTypeAndId(type,voucherId);
			
			return vouchers;
		}
	
	
	@RequestMapping(value = "/filterById",params = { "id" }, method = RequestMethod.GET)
	public List<Object[]> filterById(@RequestParam(value = "id") Long id) {
		List<Object[]> vouchers = voucherService.filterById(id);
		return vouchers;
	}
	
	//get via  id
	@GetMapping("/loan/{voucherId}")
	public List<LoanTransaction> fetchAllLoanTransactionById(@PathVariable("voucherId") Long voucherId){
		return loanTransactionService.fetchLoanTransactions(voucherId);
	}
	//get all
	@GetMapping("/loan/")
	public List<LoanTransaction> fetchAllLoanTransaction(){

		return loanTransactionService.fetchAllLoanTransactions();
	}
	
	
	@RequestMapping(value = "/loan/flag", params = { "flag","voucherId"}, method = RequestMethod.GET)
	public List<Object[]> listLoanTransactionByFlagAndVoucherId(@RequestParam(value = "flag") Integer flag,@RequestParam(value = "voucherId") Long voucherId){
		
		System.out.println(flag+"===="+voucherId);
		
		return loanTransactionService.listLoanTransactionByFlagAndVoucherId(flag, voucherId);
	}
	
	@PostMapping("/loan/")
	public LoanTransaction saveLoan(@RequestBody LoanTransaction loanTransaction){
		
		System.out.println("Get Balance is controller="+loanTransaction.getBalance());
		
		
		return loanTransactionService.saveLoan(loanTransaction);
	}
	
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping("/default")
	public Voucher defaultValue() {
		return new Voucher();
	}
	
	@GetMapping("/expense-for-today")
	public List<Voucher> findTodaysExpenseVoucher(){
		return voucherService.fetchAllTodaysExpenseVoucher();
	}
	
	@GetMapping("/expense")
	public List<Voucher> findExpenseVoucher(){
		return voucherService.fetchAllVoucherInDescOrder("expense");
	}
	
	@GetMapping("/expense/{id}")
	public List<Voucher> findAllExpenseByVoucherId(@PathVariable("id") Long id){
		Voucher voucher = voucherService.findVoucherById(id).get();
		return voucherService.fetchVoucherByDateAndType("expense", voucher.getUpdateTimestamp());
	}
	
	@GetMapping("/expense/id/{id}")
	public Voucher findSingleExpenseByVoucherId(@PathVariable("id") Long id){
		Voucher voucher = voucherService.findVoucherById(id).get();
		return voucher;
	}
	
	@GetMapping("/capital")
	public List<Voucher> findAllCapital(){
		return voucherService.fetchAllVoucherInDescOrder("capital");
	}
	
	@GetMapping("/capital/{id}")
	public List<Voucher> findAllCapitalByVoucherId(@PathVariable("id") Long id){
		Voucher voucher = voucherService.findVoucherById(id).get();
		return voucherService.fetchVoucherByDateAndType("capital", voucher.getUpdateTimestamp());
	}

	@GetMapping("/capital-for-today")
	public List<Voucher> fidAllCapitalForToday(){
		return voucherService.fetchAllCapitalVoucherForToday();
	}
	
	
	@GetMapping("/mortgage-for-today")
	public List<Voucher> findTodaysMortgageVoucher(){
		return voucherService.fetchAllTodaysMortgageVoucher();
	}
	
	
	@GetMapping("/mortgage")
	public List<Voucher> findAllMortgageVoucher(){
		return voucherService.fetchAllMortgageVoucher();
	}
	


	@PostMapping("/")
	public Voucher saveVoucher(@RequestBody Voucher voucher) {
		System.out.println(voucher);
		return voucherService.saveOrUpdate(voucher);
	}
	
	

	@GetMapping("/{id}")
	public Voucher findVoucherById(@PathVariable("id") Long id) {
		Optional<Voucher> voucher = voucherService.findVoucherById(id);
		if (voucher.isPresent()) {
			return voucher.get();
		} else {
			return new Voucher();
		}
	}
	
	@GetMapping("/update/{SlId}")
	public Voucher findVoucherBySlId(@PathVariable("SlId") Long id) {
		Optional<Voucher> voucher = voucherService.findVoucherBySlId(id);
		if (voucher.isPresent()) {
			return voucher.get();
		} else {
			return new Voucher();
		}
	}

	@GetMapping("/transaction/{transactionId}")
	public Voucher findVoucherByTransactionId(@PathVariable("transactionId") Long transactionId) {
		Optional<Transaction> transaction = transactionService.findTransactionById(transactionId);
		Optional<Voucher> voucher = voucherService.findVoucherByTransactionId(transaction.get());
		if (voucher.isPresent()) {
			return voucher.get();
		} else {
			return new Voucher();
		}

	}
	
	
	@RequestMapping(value = "/remove", params = { "id","type" }, method = RequestMethod.POST)
	public void deleteVoucher(@RequestParam(value = "id") Long id,@RequestParam(value = "type") String type) {
		voucherService.removeVoucher(id,type);
	}
	
	
	
	@PostMapping("/loan/delete/{id}")
	public void deleteLoanTransaction(@PathVariable("id") Long id) {
		loanTransactionService.deleteLoanTransaction(id);
	}
	
	//======================================================================================
	//EXPENSE AND CAPITAL
	//search by specific date
	@RequestMapping(value = "/ec/search", params = { "date","type" }, method = RequestMethod.GET)
	public List<Voucher> getECOfSpecificDate(@RequestParam(value = "date") String date,@RequestParam(value = "type") String type){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {

            Date d = formatter.parse(date);
            System.out.println(d);
            System.out.println(formatter.format(d));
            
            return voucherService.fetchECBySpecificDate(type, d);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
		
	}
	
	
	
	//search by dateto and from
	@RequestMapping(value = "/ec/search", params = { "dateto","datefrom","type" }, method = RequestMethod.GET)
	public List<Voucher> getECByDateRange(@RequestParam(value = "dateto") String dateto,@RequestParam(value = "datefrom") String datefrom,@RequestParam(value = "type") String type){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {

            Date dto = formatter.parse(dateto);
            Date dfrom =formatter.parse(datefrom);
            
            return voucherService.fetchECByDateRange(type, dto,dfrom);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
		
	}
	//================================================================================================================
	
	
	
	//LOAN TAKE
	/*
	@PostMapping("/takeloan")
	public TakeLoan saveVoucher(@RequestBody TakeLoan loan) {
		System.out.println(loan);
		return loanTakeService.saveLoan(loan);
	}
	*/
	
	@GetMapping("/takeloan")
	public List<Voucher> getAllTakeLoanDetails() {
		
		return voucherService.fetchAllVoucherInDescOrder("loan_take");
	}
	
	/*
	@GetMapping("/takeloan/{id}")
	public Optional<TakeLoan>  getTakeLoanDetail(@PathVariable("id") Long id) {
		
		Optional<TakeLoan> op =loanTakeService.fetchTakeLoanDetail(id);
		
		TakeLoan tl=op.get();
		
		System.out.println("======================\n"+tl.getRemainderDate()+"\n"+tl.getCurrency()+"\n========================");
		
		return op;
	}
	
	
	@PostMapping("/takeloan/{id}")
	public void deleteTakeLoanDetail(@PathVariable("id") Long id) {
		
		loanTakeService.deleteLoanTakeDetails(id);
	}
	
	
	
	@PostMapping("/takeloan/transaction")
	public LoanTakeTransaction saveVoucher(@RequestBody LoanTakeTransaction loan) {
		System.out.println(loan);
		return loanTakeTransactionService.saveLoan(loan);
	}
	
	
	@GetMapping("/takeloan/transaction/{id}")
	public List<LoanTakeTransaction> getTakeLoanTransactionDetails(@PathVariable("id") Long voucherId) {
		
		return loanTakeTransactionService.fetchLoanTransactions(voucherId);
	}
	
	
	@GetMapping("/takeloan/transaction/id/{id}")
	public LoanTakeTransaction getTakeLoanByTransactionID(@PathVariable("id") Long id) {
		System.out.println("in controller = "+id);
		return loanTakeTransactionService.fetchTakeLoanByTransactionID(id);
	}
	
	
	@PostMapping("/takeloan/transaction/id/")
	public LoanTakeTransaction saveOrUpdateTakeLoanByTransactionID(@RequestBody LoanTakeTransaction loan) {
		return loanTakeTransactionService.saveLoan(loan);
	}
	*/
	
	
	//==================================================================================
	//LOAN_GIVE
	//Error
	@GetMapping("/loangive/")
	public List<Voucher> fetchAllTodaysLoanGiveVoucher() {
		
		return voucherService.fetchAllTodaysLoanTakeVoucher();
	}
	
	
	
		@GetMapping("/loanGive")
		public List<Voucher> fetchLoanGiveVoucher(){
			return voucherService.fetchAllVoucherInDescOrder("loan_give");
		}
		
	
	
	//==================================================================================
	//
	
	@GetMapping("/loan/id/{voucherId}")
	public List<LoanTransaction> fetchOneLoanTransaction(@PathVariable("voucherId") Long voucherId){
		return loanTransactionService.getOneLoanTransactionByVoucher(voucherId);
	}
	
	//=====================================================================================
	
	@RequestMapping(value = "/id", params = { "cid","type"}, method = RequestMethod.GET)
	public List<Voucher> fetchVoucherByCustomerIdAndType(@RequestParam(value = "cid") Long customerId,@RequestParam(value = "type")String type) {
		
		return voucherService.findVoucherByCustomerIdAndType(customerId,type);
	}
	
	//=================================================================================
	
	
	@RequestMapping(value = "/rdate", params = { "date","type"}, method = RequestMethod.GET)
	public List<Voucher> fetchVoucherByRemainderDate(@RequestParam(value = "date") Date reminderDate ,@RequestParam(value = "type")String type) {
		
		return voucherService.findVouchersByRemainderDate(reminderDate,type);
	}
		
	
	//update reminder date
	@RequestMapping(value = "/reminderDate", params = { "date","id"}, method = RequestMethod.POST)
	public void updateRemainderDate(@RequestParam(value = "date") Date reminderDate,@RequestParam(value = "id")Long id) {
		
		voucherService.updateRemainderDate(reminderDate,id);
	}
	
	//=====================================================================================
	
	/*Transaction History*/
	
	@RequestMapping(value = "/filterByType",params = { "type" }, method = RequestMethod.GET)
	public List<Object[]> getAllTransactionByType(@RequestParam(value = "type") String type) {
		List<Object[]> vouchers = voucherService.filterByType(type);
		return vouchers;
	}
	
	
	@RequestMapping(value = "/updateByIDAndType", params = { "id","type"}, method = RequestMethod.GET)
	public List<Object[]> updateByIdAndType(@RequestParam(value = "type")String type,@RequestParam(value = "id") Long id) {
		
		
		return voucherService.filterByTypeAndLoanId(type,id);
	}
	
	@GetMapping("/loan/sum/{voucherId}")
	public BigDecimal getSumByVoucherId(@PathVariable("voucherId") Long voucherId){
		return loanTransactionService.totalSumOfVoucherId(voucherId);
	}
	
	
	@GetMapping("/loan/sum-interest/{voucherId}")
	public BigDecimal getSumInterestByVoucherId(@PathVariable("voucherId") Long voucherId){
		return loanTransactionService.totalSumInterestOfVoucherId(voucherId);
	}
	
	@RequestMapping(value = "/updateAmounts", params = { "voucherId","id","amnt"}, method = RequestMethod.POST)
	public void updatePrevLoanAmtAfterId(@RequestParam(value = "voucherId") Long voucherId ,@RequestParam(value = "id")Long id,@RequestParam(value = "amnt") BigDecimal amnt 
				,@RequestParam(value = "intAmnt") BigDecimal intAmnt) {
		loanTransactionService.updateAmounts(voucherId, id, amnt,intAmnt);
		
	}
	
	//=====================================================================================
	
	/*RECEIVE TRANSACTION*/
	@RequestMapping(value = "/getRecentTransactions", params = {"flag","type"}, method = RequestMethod.GET)
	public List<LoanTransaction> getRecentTransaction(@RequestParam(value = "flag")Integer flag,@RequestParam(value = "type")String type) {
		
		
		return loanTransactionService.getRecentTransactionOfAllVouchers(flag,type);
	}
	
	
	
	@RequestMapping(value = "/getSpecificTrans", params = {"flag","id"}, method = RequestMethod.GET)
	public List<LoanTransaction> getSpecificTransactionss(@RequestParam(value = "flag")Integer flag,
			@RequestParam(value = "id")Long voucherId) {
		
		
		return loanTransactionService.getRecentTransactionOfSpecificVoucher(flag,voucherId);
	}
	//===============================================================================
	//For Daily reports
	@RequestMapping(value = "/getDailyReport", params = {"type","date"}, method = RequestMethod.GET)
	public List<Voucher> getVoucherByDate(@RequestParam(value = "date")Date date,
			@RequestParam(value = "type")String type) {
		
		
		return voucherService.getDailyReport(type, date);
	}
	
	@RequestMapping(value = "/getDailyLoanUpdateReport", params = {"type","date"}, method = RequestMethod.GET)
	public List<Object[]> getDailyLoanUpdateReport(@RequestParam(value = "date")Date date,
			@RequestParam(value = "type")String type) {
		
		System.out.println("Date="+date+" | Type="+type);
		
		
		return loanTransactionService.getDailyTransactionUpdate(type, date);
	}
	
	@RequestMapping(value = "/getDailyLoanReport", params = {"flag","type","date"}, method = RequestMethod.GET)
	public List<LoanTransaction> getDailyLoanReport(@RequestParam(value = "date")Date date,
			@RequestParam(value = "type")String type,@RequestParam(value = "flag")Integer flag) {
		
		
		return loanTransactionService.getDailyLoan(flag, type, date);
	}
	
	
	//======================================================================================
	
	//For Weekly Reports 
	@RequestMapping(value = "/getWeeklyReport", params = {"type","date"}, method = RequestMethod.GET)
	public List<Object[]> getWeeklyReport(@RequestParam(value = "type")String type,@RequestParam(value = "date")Date date) {
	
		System.out.println(type+" Date : "+date);
		return voucherService.reportWeekly(type, date);
	}
	
	
	@RequestMapping(value = "/getWeeklyLoanUpdateReport", params = {"type","date"}, method = RequestMethod.GET)
	public List<Object[]> getWeeklyLoanUpdateReport(@RequestParam(value = "type")String type,@RequestParam(value = "date")Date date) {
	
		System.out.println("Loan Update Lgs ="+type+" Date : "+date);
		return loanTransactionService.reportWeeklyTransactionUpdate(type, date);
	}
	
	@RequestMapping(value = "/getWeeklyLoanReport", params = {"flag","type","date"}, method = RequestMethod.GET)
	public List<Object[]> getWeeklyLoanReport(@RequestParam(value = "flag")Integer flag,@RequestParam(value = "type")String type,@RequestParam(value = "date")Date date) {
	
		System.out.println(type+" Date : "+date);
		return loanTransactionService.reportWeeklyLoan(flag, type, date);
	}
	//==========================================================================================
	
	//For Monthly Reports 
	@RequestMapping(value = "/getMonthlyReport", params = {"type","month","year"}, method = RequestMethod.GET)
	public List<Object[]> getMonthlyReport(@RequestParam(value = "type")String type,@RequestParam(value = "month")Integer month,@RequestParam(value = "year")Integer year) {
	
		System.out.println(type+" Month : "+month +" Year :"+year);
		return voucherService.reportMonthly(type, month, year);
	}
	
	
	@RequestMapping(value = "/getMonthlyLoanUpdateReport", params = {"type","month","year"}, method = RequestMethod.GET)
	public List<Object[]> getMonthlyLoanUpdateReport(@RequestParam(value = "type")String type,@RequestParam(value = "month")Integer month,@RequestParam(value = "year")Integer year) {
	
		System.out.println(type+" Month : "+month +" Year :"+year);
		return loanTransactionService.reportMonthlyTransactionUpdate(type, month, year);
	}
	
	@RequestMapping(value = "/getMonthlyLoanReport", params = {"flag","type","month","year"}, method = RequestMethod.GET)
	public List<Object[]> getMonthlyLoanReport(@RequestParam(value = "flag")Integer flag,@RequestParam(value = "type")String type,
			@RequestParam(value = "month")Integer month,@RequestParam(value = "year")Integer year) {
	
		System.out.println(type+" Date : "+month+""+year);
		return loanTransactionService.reportMonthlyLoan(flag, type, month, year);
	}
	
	
	//===========================================================================================
	
	
		//For Yearly Reports 
		@RequestMapping(value = "/getYearlyReport", params = {"type","year"}, method = RequestMethod.GET)
		public List<Object[]> getYearlyReport(@RequestParam(value = "type")String type,@RequestParam(value = "year")Integer year) {
		
			System.out.println(type+" Year :"+year);
			return voucherService.reportYearly(type, year);
		}
		
		
		@RequestMapping(value = "/getYearlyLoanUpdateReport", params = {"type","year"}, method = RequestMethod.GET)
		public List<Object[]> getYearlyLoanUpdateReport(@RequestParam(value = "type")String type,@RequestParam(value = "year")Integer year) {
		
			System.out.println(type+" Year :"+year);
			return loanTransactionService.reportYearlyTransactionUpdate(type, year);
		}
		
		@RequestMapping(value = "/getYearlyLoanReport", params = {"flag","type","year"}, method = RequestMethod.GET)
		public List<Object[]> getYearlyLoanReport(@RequestParam(value = "flag")Integer flag,@RequestParam(value = "type")String type,
				@RequestParam(value = "year")Integer year) {
		
			System.out.println(type+" Date :"+year);
			return loanTransactionService.reportYearlyLoan(flag, type, year);//loanTransactionService.reportYearlyLoan(flag, type, year);
		}
	
	

		//==========================================================================================
		
		
		
		//For Decade Reports 
				@RequestMapping(value = "/getDecadeReport", params = {"type","lyear","uyear"}, method = RequestMethod.GET)
				public List<Object[]> getDecadeReport(@RequestParam(value = "type")String type,@RequestParam(value = "lyear")Integer lyear
						,@RequestParam(value = "uyear")Integer uyear) {
				
					System.out.println(type+" Year :"+lyear+"-"+uyear);
					return voucherService.reportDecade(type, lyear, uyear);
				}
				
				
				@RequestMapping(value = "/getDecadeLoanUpdateReport", params = {"type","lyear","uyear"}, method = RequestMethod.GET)
				public List<Object[]> getDecadeLoanUpdateReport(@RequestParam(value = "type")String type,@RequestParam(value = "lyear")Integer lyear
						,@RequestParam(value = "uyear")Integer uyear) {
				
					System.out.println(type+" Year :"+lyear+"-"+uyear);
					return loanTransactionService.reportDecadeTransactionUpdate(type, lyear, uyear);
				}
				
				@RequestMapping(value = "/getDecadeLoanReport", params = {"flag","type","lyear","uyear"}, method = RequestMethod.GET)
				public List<Object[]> getDecadeLoanReport(@RequestParam(value = "flag")Integer flag,@RequestParam(value = "type")String type,@RequestParam(value = "lyear")Integer lyear
						,@RequestParam(value = "uyear")Integer uyear) {
				
					System.out.println(type+" Year :"+lyear+"-"+uyear);
					return loanTransactionService.reportDecadeLoan(flag, type, lyear, uyear);//loanTransactionService.reportYearlyLoan(flag, type, year);
				}
			
			

				//==========================================================================================
		
	//delete loan transaction
	@RequestMapping(value = "/loan/delete", params = {"id","paidAmt","paidInterestAmt","voucherId"}, method = RequestMethod.POST)
	public void updateRemainderDate(@RequestParam(value = "id") Long id,@RequestParam(value = "paidAmt") BigDecimal paidAmt,
			@RequestParam(value = "paidInterestAmt") BigDecimal paidInterestAmt,@RequestParam(value = "voucherId")Long voucherId) {
		
		loanTransactionService.deleteAndUpdateAmount(id,paidAmt,paidInterestAmt,voucherId);
	}
}
