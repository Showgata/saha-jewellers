package com.sahaJwellers.app.restController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sahaJwellers.app.model.ClosingBalance;
import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.LoanTransaction;
import com.sahaJwellers.app.model.TodayBalance;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.BalanceService;
import com.sahaJwellers.app.service.CashService;
import com.sahaJwellers.app.service.ClosingBalanceService;
import com.sahaJwellers.app.service.CustomerService;
import com.sahaJwellers.app.service.ExpenseService;
import com.sahaJwellers.app.service.LoanTransactionService;
import com.sahaJwellers.app.service.TransactionService;
import com.sahaJwellers.app.service.VoucherService;
import com.sahaJwellers.app.util.DateUtil;

@RestController
@RequestMapping("/mortgage-app/api/cash")
public class CashLedgerController {
	
	@Autowired
	private CashService cashService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private TransactionService accountService;
	
	@Autowired
	private LoanTransactionService transactionService;
	
	@Autowired
	private ClosingBalanceService closingBalanceService;
	
	//todaybal
	@Autowired
	private BalanceService balanceService;
	
	
	@RequestMapping(path="/",method=RequestMethod.GET)

	public String getall() {
//	List<Customer> custList = new ArrayList<Customer>();
//		
//		custList.addAll(cashService.findAllCustomer());
//		
		List<LoanTransaction> transList = new ArrayList<LoanTransaction>();
		//transList.addAll(cashService.fetchAllLoanTransactions());
		transList.addAll(transactionService.getTodaysLoan());
		
//need account table
		List<Transaction> ac = new ArrayList<Transaction>();
		ac.addAll(accountService.fetchAll());
		
		
		 
		List<Expense> exp = new ArrayList<Expense>();
		exp.addAll(expenseService.listExpenseForToday());
		
		List<Voucher> vl = new ArrayList<Voucher>();
		vl.addAll(cashService.fetchAllVouchers());
		
		List<Voucher> voucherlist = new ArrayList<Voucher>();
		voucherlist.addAll(voucherService.fetchAllCapitalVoucherForToday());
		voucherlist.addAll(voucherService.fetchAllTodaysMortgageVoucher());
		voucherlist.addAll(voucherService.fetchAllTodaysLoanTakeVoucher());
		voucherlist.addAll(voucherService.fetchAllTodaysLoanGiveVoucher());
		ArrayList al = new ArrayList();
		//al.add(custList);
		//al.add(voucherlist);
		
		//date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println("todays date is "+dateFormat.format(date));
		for(Voucher v : voucherlist ){
				
//			hmap.put("date",v.getDate().toString());
			String s1 =" \"date\" :" +"\""+ v.getDate().toString()+"\"" ; 
			String s2 = "";
			if(v.getType().equals("mortgage")) {
				 s2 =" \"tmtype\" :" +"\""+"Gm"+"\"" ; 

			}
			else if(v.getType().equals("loan_take")) {
				 s2 =" \"tmtype\" :" +"\""+"Lt"+"\"" ;	
			}
			else if(v.getType().equals("loan_give")) {
				 s2 =" \"tmtype\" :" +"\""+"Lg"+"\"" ; 
           	}
			else if(v.getType().equals("capital")) {
				 s2 =" \"tmtype\" :" +"\""+"Civ"+"\"" ; 
	
			}
			String s3 =" \"voucher_sl\" :"+"\""+ v.getId().toString()+"\"" ; 
//			String s4 =" \"customer_sl\" :"+"\"" + v.getCustomer().getCustomerId().toString()+"\"" ; 
//
//			String s5 = " \"name\" :"+"\"" + v.getCustomer().getCustomerName().toString()+"\"" ;
//			String s6 = " \"int_rate\" :"+"\""+ v.getMortgage().getInterestRate().toString()+"\"" ;
			
			String s4 =""; 
			String s5 = "" ;
			String s6 = "";
			String s9 = "";	
			//mortgage int rate
			for(Voucher vt : vl ){
				if(v.getId().equals(vt.getId())) {
					
					s5 = " \"name\" :"+"\"" + vt.getCustomer().getCustomerName()+"\"" ;
					   
					
					if(v.getType().equals("mortgage")) {
						s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
						s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
                        s9 = " \"amount\" :"+"\"" +v.getMortgage().getLoanAmount().toString()+"\"" ;

			            
					}
					else if(v.getType().equals("loan_take")) {
						
//						for(Transaction a : ac ){
//							if(a.getAccount().getAccountId().equals(v.getTransaction().getAccount()) ) {
//								
//								
//							}}
						
						s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
						
						s4 =" \"customer_sl\" :"+"\"" + "0"+"\""  ;
						
						
			          s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;

			            
						
					}
					else if(v.getType().equals("loan_give")) {
						s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
						s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
						s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
					}
					else if(v.getType().equals("capital")) {
						s6 = " \"int_rate\" :"+"\""+"0"+"\"" ;
						s4 =" \"customer_sl\" :"+"\"" +"0"+"\""  ;
						s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
					}
					
					
					
					
					}
			}
			
			
            String s7 = " \"paid_int\" :"+"\"" + "0"+"\"" ; 
            String s8 = " \"paid_amt\" :"+"\"" +"0"+"\"" ;
            String vouchers = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
            
//			hmap.put("amount", v.getMortgage().getLoanAmount().toString());
			
			
			
			
			
			
               
			al.add(vouchers);
				}
		
		for(LoanTransaction lt : transList ){
		    float c = Float.parseFloat(lt.getInterestPaidAmount().toString());
            float d =  Float.parseFloat(lt.getPaidAmount().toString());
            float summm = c + d ;
        if(summm != 0 )
        {
			
			String s1 =" \"date\" :" +"\""+ lt.getDate().toString()+"\""  ; 
			String s2 = "";
			if(lt.getType().equals("mortgage") && lt.getFlag().equals(1) ) {
				 s2 =" \"tmtype\" :" +"\""+"Gm-D"+"\"" ; 

			}
			else if( lt.getType().equals("mortgage") && lt.getFlag().equals(0)) {
				 s2 =" \"tmtype\" :" +"\""+"Gm-U"+"\"" ;	
			}
			else if(lt.getType().equals("loan_take") && lt.getFlag().equals(0)) {
				 s2 =" \"tmtype\" :" +"\""+"Lt-U"+"\"";	
			}
			else if(lt.getType().equals("loan_take") && lt.getFlag().equals(1)) {
				 s2 =" \"tmtype\" :" +"\""+"Lt-D"+"\"";	
			
			}
		
			else if(lt.getType().equals("loan_give") && lt.getFlag().equals(0)) {
				 s2 =" \"tmtype\" :" +"\""+"Lg-U"+"\"";	
			}
			else if(lt.getType().equals("loan_give") && lt.getFlag().equals(1)) {
				 s2 =" \"tmtype\" :" +"\""+"Lg-D"+"\"";	
			}
			String s3 =" \"voucher_sl\" :"+"\""+ lt.getVoucherId().toString()+"\"" ; 
			String s4 =""; 
			String s5 = "" ;
			String s6 = "";
	        	
			//mortgage int rate
			for(Voucher v : vl ){
				if(v.getId().equals(lt.getVoucherId())) {
					s4 =" \"customer_sl\" :"+"\"" + v.getCustomer().getCustomerId()+"\""  ;
					s5 = " \"name\" :"+"\"" + v.getCustomer().getCustomerName()+"\"" ;
//					s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
					
					
					
					
					
					
					if(v.getType().equals("mortgage")) {
						s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
//						s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
//                        s9 = " \"amount\" :"+"\"" +v.getMortgage().getLoanAmount().toString()+"\"" ;

			            
					}
					else if(v.getType().equals("loan_take")) {
						
//						for(Transaction a : ac ){
//							if(a.getAccount().getAccountId().equals(v.getTransaction().getAccount()) ) {
//								
//								
//							}}
						
						s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
						
//						s4 =" \"customer_sl\" :"+"\"" + "0"+"\""  ;
//						
//						
//			          s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
//
			            
						
					}
					else if(v.getType().equals("loan_give")) {
						s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
//						s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
//						s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
					}
					
					
					
					
					
					
					
				}
			}
			
			
			
			
			
			
			
		    String s7 = " \"paid_int\" :"+"\"" + lt.getInterestPaidAmount().toString()+"\"" ; 
            String s8 = " \"paid_amt\" :"+"\""  + lt.getPaidAmount().toString()+"\"" ;
            float a = Float.parseFloat(lt.getInterestPaidAmount().toString());
            float b =  Float.parseFloat(lt.getPaidAmount().toString());
            float summ = a + b ;
            
            String s9 = " \"amount\" :"+"\""  + String.valueOf(summ)+"\"" ;
        	String trans = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
			
			
			
			al.add(trans);
		
        }
		
		}
		
		
		

		for(Expense et : exp ){
			
			String s1 =" \"date\" :" +"\""+ et.getDate().toString()+"\""  ; 
			String s2 = " \"tmtype\" :" +"\""+ "Exp"+"\"";
					
			String s3 =" \"voucher_sl\" :"+"\""+ et.getId().toString()+"\"" ; 
			String s4 =" \"customer_sl\" :"+"\"" + et.getExpenseInfoId()+"\"" ; 
			String s5 = " \"name\" :"+"\"" + et.getExpenseHead()+"\"" ;
			String s6 = " \"int_rate\" :"+"\""+ "---"+"\"" ;
	        	
		    String s7 = " \"paid_int\" :"+"\""  + "---"+"\"" ; 
            String s8 =  " \"paid_amt\" :"+"\""  + "---"+"\"" ;
            
            String s9 =  " \"amount\" :"+"\""  + et.getAmount()+"\"" ;
        	String expensee = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
		
			al.add(expensee);
		
			
		
		}
		

		
				return al.toString();
	}
	
//	@RequestMapping(value = "/ec/search", params = { "date" }, method = RequestMethod.GET)
//	public List<Object[]> getallSpecificDate(@RequestParam(value = "date") String date){
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        
//        try {
//
//            Date d = formatter.parse(date);
//            System.out.println(d);
//            System.out.println(formatter.format(d));
//            
//            return cashService.fetchECBySpecificDate(d);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//		
//		return null;
//		
//	}
	@RequestMapping(path="/today/",method=RequestMethod.GET)
	public String getalltoday() {
		List<TodayBalance> tb = new ArrayList<TodayBalance>();
		//transList.addAll(cashService.fetchAllLoanTransactions());
		tb.addAll(balanceService.findTodaysBalance());	
		
		return tb.toString();
	}
	// params = { "cashin","cashout","balance"}, 
	@RequestMapping(value = "/todays",method = RequestMethod.GET)
	public List<TodayBalance> UpdateBalance(@RequestParam(value = "cashin") Long cashIn,@RequestParam(value = "cashout")Long cashOut,@RequestParam(value = "balance")Long Balance) {
		
//		List<TodayBalance> tb = new ArrayList<TodayBalance>();
//		//transList.addAll(cashService.fetchAllLoanTransactions());
//		tb.addAll(balanceService.findTodaysBalance());	
//		
//		return tb.toString();
		
	return balanceService.updateBalance(cashIn,cashOut,Balance);
	}
	
	@RequestMapping(path="/closingbal/",method=RequestMethod.GET)

	public List<ClosingBalance> closingBalanceData() {
	
		return closingBalanceService.closingBalance();
	  }
	
	//**************************** //date from ------- date to // **********************************//

	
	
	@RequestMapping(value="/datefrom_dateto",params = {"datefrom", "dateto"},method=RequestMethod.GET)

	public String allrecords(@RequestParam(value="datefrom") String dateFrom,@RequestParam(value="dateto") String dateTo) 
	{
		
		 java.sql.Date date1 = java.sql.Date.valueOf(dateFrom);   //converting string into sql date 
		  java.sql.Date date2 = java.sql.Date.valueOf(dateTo);   //converting string into sql date 
		  
		  //for transaction
		    List<LoanTransaction> transList = new ArrayList<LoanTransaction>();
			transList.addAll(transactionService.getdatespecific(date1,date2));
			
		  //for all vouchers
			 List<Voucher> vall = new ArrayList<Voucher>();
				vall.addAll(voucherService.getdatespecific(date1,date2));
			//for all expense
				List<Expense> exp = new ArrayList<Expense>();
				exp.addAll(expenseService.getdatespecific(date1,date2));
			
			//for all closingbalance
				List<ClosingBalance> cbb = new ArrayList<ClosingBalance>();
				cbb.addAll(closingBalanceService.getdatespecific(date1,date2));
				
				
			ArrayList dfdt = new ArrayList(); //to store everything in an arraylist
			//transaction part
			
			List<Voucher> vl = new ArrayList<Voucher>();
			vl.addAll(cashService.fetchAllVouchers());
			
			for(LoanTransaction lt : transList ){
			    float c = Float.parseFloat(lt.getInterestPaidAmount().toString());
	            float d =  Float.parseFloat(lt.getPaidAmount().toString());
	            float summm = c + d ;
	        //System.out.print("the sum is "+summm +"see");
	            if(summm != 0 )
	        {
				
				String s1 =" \"date\" :" +"\""+ lt.getDate().toString()+"\""  ; 
				String s2 = "";
				if(lt.getType().equals("mortgage") && lt.getFlag().equals(1) ) {
					 s2 =" \"tmtype\" :" +"\""+"Gm-D"+"\"" ; 

				}
				else if( lt.getType().equals("mortgage") && lt.getFlag().equals(0)) {
					 s2 =" \"tmtype\" :" +"\""+"Gm-U"+"\"" ;	
				}
				else if(lt.getType().equals("loan_take") && lt.getFlag().equals(0)) {
					 s2 =" \"tmtype\" :" +"\""+"Lt-U"+"\"";	
				}
				else if(lt.getType().equals("loan_take") && lt.getFlag().equals(1)) {
					 s2 =" \"tmtype\" :" +"\""+"Lt-D"+"\"";	
				
				}
			
				else if(lt.getType().equals("loan_give") && lt.getFlag().equals(0)) {
					 s2 =" \"tmtype\" :" +"\""+"Lg-U"+"\"";	
				}
				else if(lt.getType().equals("loan_give") && lt.getFlag().equals(1)) {
					 s2 =" \"tmtype\" :" +"\""+"Lg-D"+"\"";	
				}
				String s3 =" \"voucher_sl\" :"+"\""+ lt.getVoucherId().toString()+"\"" ; 
				String s4 =""; 
				String s5 = "" ;
				String s6 = "";
		        	
				//mortgage int rate
				for(Voucher v : vl ){
					if(v.getId().equals(lt.getVoucherId())) {
						s4 =" \"customer_sl\" :"+"\"" + v.getCustomer().getCustomerId()+"\""  ;
						s5 = " \"name\" :"+"\"" + v.getCustomer().getCustomerName()+"\"" ;
//						s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
						
						
						
						
						
						
						if(v.getType().equals("mortgage")) {
							s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
//							s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
//	                        s9 = " \"amount\" :"+"\"" +v.getMortgage().getLoanAmount().toString()+"\"" ;

				            
						}
						else if(v.getType().equals("loan_take")) {
							
//							for(Transaction a : ac ){
//								if(a.getAccount().getAccountId().equals(v.getTransaction().getAccount()) ) {
//									
//									
//								}}
							
							s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
							
//							s4 =" \"customer_sl\" :"+"\"" + "0"+"\""  ;
//							
//							
//				          s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
	//
				            
							
						}
						else if(v.getType().equals("loan_give")) {
							s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
//							s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
//							s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
						}
						
						
						
						
						
						
						
					}
				}
				
				
				
				
				
				
				
			    String s7 = " \"paid_int\" :"+"\"" + lt.getInterestPaidAmount().toString()+"\"" ; 
	            String s8 = " \"paid_amt\" :"+"\""  + lt.getPaidAmount().toString()+"\"" ;
	            float a = Float.parseFloat(lt.getInterestPaidAmount().toString());
	            float b =  Float.parseFloat(lt.getPaidAmount().toString());
	            float summ = a + b ;
	            
	            String s9 = " \"amount\" :"+"\""  + String.valueOf(summ)+"\"" ;
	        	String trans = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
				
				
				
				dfdt.add(trans);
			
	        }
			}
//end of transaction
	        
	        //voucher section
	        
			for(Voucher v : vall ){
				
//				hmap.put("date",v.getDate().toString());
				String s1 =" \"date\" :" +"\""+ v.getDate().toString()+"\"" ; 
				String s2 = "";
				if(v.getType().equals("mortgage")) {
					 s2 =" \"tmtype\" :" +"\""+"Gm"+"\"" ; 

				}
				else if(v.getType().equals("loan_take")) {
					 s2 =" \"tmtype\" :" +"\""+"Lt"+"\"" ;	
				}
				else if(v.getType().equals("loan_give")) {
					 s2 =" \"tmtype\" :" +"\""+"Lg"+"\"" ; 
	           	}
				else if(v.getType().equals("capital")) {
					 s2 =" \"tmtype\" :" +"\""+"Civ"+"\"" ; 
		
				}
				String s3 =" \"voucher_sl\" :"+"\""+ v.getId().toString()+"\"" ; 
//				String s4 =" \"customer_sl\" :"+"\"" + v.getCustomer().getCustomerId().toString()+"\"" ; 
	//
//				String s5 = " \"name\" :"+"\"" + v.getCustomer().getCustomerName().toString()+"\"" ;
//				String s6 = " \"int_rate\" :"+"\""+ v.getMortgage().getInterestRate().toString()+"\"" ;
				
				String s4 =""; 
				String s5 = "" ;
				String s6 = "";
				String s9 = "";	
				//mortgage int rate
				for(Voucher vt : vl ){
					if(v.getId().equals(vt.getId())) {
						
						s5 = " \"name\" :"+"\"" + vt.getCustomer().getCustomerName()+"\"" ;
						   
						
						if(v.getType().equals("mortgage")) {
							s6 = " \"int_rate\" :"+"\""+  v.getMortgage().getInterestRate()+"\"" ;
							s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
	                        s9 = " \"amount\" :"+"\"" +v.getMortgage().getLoanAmount().toString()+"\"" ;

				            
						}
						else if(v.getType().equals("loan_take")) {
							
//							for(Transaction a : ac ){
//								if(a.getAccount().getAccountId().equals(v.getTransaction().getAccount()) ) {
//									
//									
//								}}
							
							s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
							
							s4 =" \"customer_sl\" :"+"\"" + "0"+"\""  ;
							
							
				          s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;

				            
							
						}
						else if(v.getType().equals("loan_give")) {
							s6 = " \"int_rate\" :"+"\""+v.getTransaction().getAccount().getInterestRate()+"\"" ;
							s4 =" \"customer_sl\" :"+"\"" + vt.getTransaction().getTransactionSerial()+"\""  ;
							s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
						}
						else if(v.getType().equals("capital")) {
							s6 = " \"int_rate\" :"+"\""+"0"+"\"" ;
							s4 =" \"customer_sl\" :"+"\"" +"0"+"\""  ;
							s9 = " \"amount\" :"+"\"" +v.getTransaction().getAmount().toString()+"\"" ;
						}
						
						
						
						
						}
				}
				
				
	            String s7 = " \"paid_int\" :"+"\"" + "0"+"\"" ; 
	            String s8 = " \"paid_amt\" :"+"\"" +"0"+"\"" ;
	            String vouchers = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
	            
//				hmap.put("amount", v.getMortgage().getLoanAmount().toString());
				
				
				
				
				
				
	               
				dfdt.add(vouchers);
					}  
			
			//expense here
			
			for(Expense et : exp ){
				
				String s1 =" \"date\" :" +"\""+ et.getDate().toString()+"\""  ; 
				String s2 = " \"tmtype\" :" +"\""+ "Exp"+"\"";
						
				String s3 =" \"voucher_sl\" :"+"\""+ et.getId().toString()+"\"" ; 
				String s4 =" \"customer_sl\" :"+"\"" + et.getExpenseInfoId()+"\"" ; 
				String s5 = " \"name\" :"+"\"" + et.getExpenseHead()+"\"" ;
				String s6 = " \"int_rate\" :"+"\""+ "---"+"\"" ;
		        	
			    String s7 = " \"paid_int\" :"+"\""  + "---"+"\"" ; 
	            String s8 =  " \"paid_amt\" :"+"\""  + "---"+"\"" ;
	            
	            String s9 =  " \"amount\" :"+"\""  + et.getAmount()+"\"" ;
	        	String expensee = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
			
			dfdt.add(expensee);
			
				
			
			}
			

			//closing balance
			
			for(ClosingBalance cc : cbb ){
				String s1 =" \"date\" :" +"\""+cc.getDate().toString()+"\""  ; 
				String s2 = " \"tmtype\" :" +"\""+ "Closing Balance"+"\"";
						
				String s3 =" \"voucher_sl\" :"+"\""+ "====" +"\"" ; 
				String s4 =" \"customer_sl\" :"+"\"" + "===="+"\"" ; 
				String s5 = " \"name\" :"+"\"" + "====" +"\"" ;
				String s6 = " \"int_rate\" :"+"\""+ "====" +"\"" ;
		        	
			    String s7 = " \"paid_int\" :"+"\""  + "===="+"\"" ; 
	            String s8 =  " \"paid_amt\" :"+"\""  + "===="+"\"" ;
	            
	            String s9 =  " \"balance\" :"+"\""  + cc.getCbalance()+"\"" ;
	        	String allcb = "{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+"}";
			
				dfdt.add(allcb);
			
				
				
			}
					
			
		
		
	 
	
			return dfdt.toString();
	}
	
	
}
