



var calculateInterest = function(receiveDate,transDate,rate,totalAmount,dueAmt)
{
	
	     let old = moment(receiveDate); 
	     let today = moment(transDate); 
	     let diff =  Math.abs(today.diff(old,"days"));
	     
	     
	     let totalInterestAmnt = (rate*totalAmount)/100;
	
	
	     if((diff-30)>=0){
		    	let noOfDays = (diff-30);
		    	let calDue = (rate*dueAmt)/100;	    	
		    	let amnt = (calDue/30) *noOfDays;
		    	console.log("amnt="+amnt);
		        totalInterestAmnt +=amnt;
		        console.log("totalInterestAmnt="+totalInterestAmnt);
		     }
	
	
	return totalInterestAmnt;
}




//DISTINGUISH BETWEEN MORTGAGE, LOAN-TAKE AND LOAN-GIVE
var giveTransAndMortgageInfo =function(voucherDetails)
{
	
	var mortgageDetails={};
	 var transDetails={};
	
	
	if(voucherDetails.mortgage!=null){
		 mortgageDetails = {
				 bori:voucherDetails.mortgage.bori,
				 ana:voucherDetails.mortgage.ana,
				 ratti:voucherDetails.mortgage.ratti,
				 gram:voucherDetails.mortgage.gram
		 }
		 
		 transDetails={
				rate:voucherDetails.mortgage.interestRate,
				amount:voucherDetails.mortgage.loanAmount
		 }
		 
	 }else{
		 mortgageDetails = {
				 bori:0,
				 ana:0,
				 ratti:0,
				 gram:0
		 }
		 
		 transDetails={
					rate:voucherDetails.transaction.account.interestRate,
					amount:voucherDetails.transaction.account.amount
			 }
	 }
	
	
	var info ={mortgageDetails,transDetails};
	return info;
	
}



var calInterestDetails = function(val,type)
{
			var info = giveTransAndMortgageInfo(val);
			var calInter = calculateInterest(val.date,val.date,info.transDetails.rate,info.transDetails.amount,info.transDetails.amount);
		     
		    console.log("DATE======="+val.date) 
			
		     var loanTransaction ={};
		     
		     loanTransaction["flag"]=formatIntegerValue("0");
		     loanTransaction["type"]=type;
		    loanTransaction["voucherId"] = formatIntegerValue(val.id);
		    loanTransaction["dueAmount"] = formatFloatValue(info.transDetails.amount);
		    loanTransaction["paidAmount"] = formatFloatValue('0.00');
		    loanTransaction["previousLoanAmount"] = formatFloatValue('0.00');
		    loanTransaction["transaction_amount"] = formatFloatValue(info.transDetails.amount);
		    loanTransaction["date"] =new Date(val.date);
		    loanTransaction["interest_amount"] = formatFloatValue(calInter);
		    loanTransaction["interestDueAmount"] = formatFloatValue(calInter);
		    loanTransaction["interestPaidAmount"] = formatFloatValue('0.00');
		    loanTransaction["interestPreviousLoanAmount"] = formatFloatValue('0.00');
		    loanTransaction["balance"] = formatFloatValue('0.00');
		    	 
		    	 
		    	 return loanTransaction;

	}	

	
	
	
	var setInitialTransaction =function(respJson)
	{
		
		var lt = calInterestDetails(respJson,respJson.type);
		
		 let url = getAbsoluteUrl("mortgage-app/api/voucher/loan/");
    	 buildAjaxForJson(url, "POST", lt).then(function(respJson){
    		 
    		 console.log("Saved initial transaction="+respJson);
    		
    	 },function(reason){
    	           	 console.log("error in processing your request while saving loanTransaction", reason);
      	}); 
		
		
	}