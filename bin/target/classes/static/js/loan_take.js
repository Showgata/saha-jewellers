

//update mode
$("#up").click(function(){
	
	mode =1;
	$("#component1").hide();
	$("#component2").show(400);
	$(this).hide();
	toggleFields(mode);
	
	
	showPreviousValuesOfTransaction(voucherObj);
	
	
});

// cancel update
$("#can").click(function(){
	
	mode =0;
	$(this).hide();
	$("#component2").hide();
	$("#component1").show(400);
	$("#up").show();
	toggleFields(mode);
	
	
});

//disable or enable input field
var toggleFields =function(mode)
{
	if(mode ===1){
		
		$("#rate").removeAttr('disabled');
		$("#loan_amnt").removeAttr('disabled');
	}else{
		$("#rate").attr('disabled','disabled');
		$("#loan_amnt").attr('disabled','disabled');
	}
	
}

//disable update/edit btn if serial is empty
$( "#serial" ).change(function(){
	
	if($(this).val()!="" || $(this).val()!=null ){
		 $("#up").remove('disabled');
	}else{
		$("#up").attr('disabled','disabled');
	}

	
});

//if rate is changed
$("#rate").change(function(){
	
	let newRate = $(this).val()===""?"0":$(this).val();
	voucherObj["interestRate"]=parseFloat(newRate);
	console.log(voucherObj);
	let d=$("#date").val();
	calFirstTimeInterest(voucherObj,d);
	
});


//if loan amount is changed
$("#loan_amnt").change(function(){
	
	let newAmount = $(this).val()===""?"0":$(this).val();
	voucherObj["loanAmount"]=parseInt(newAmount);
	console.log(voucherObj);
	let d=$("#date").val();
	calFirstTimeInterest(voucherObj,d);
	
});

//if date is changed


$("#date").datepicker({
	  onSelect: function(dateText) {
		  
		  console.log(dateText);
		  updatePrimaryFields(voucherObj,dateText);
		  calFirstTimeInterest(voucherObj,dateText);
		  }
		});




var showPreviousValuesOfTransaction =function(voucherObj)
{
	var transId = $("#trans_id").val();
	console.log("id="+transId);
	
	 buildAjax(getAbsoluteUrl("mortgage-app/api/voucher/takeloan/transaction/id/"+transId),"GET").then(function(loanJson){
		 
	 		console.log(loanJson);
		 if(loanJson!=null)
			 {
			 	//try calling via id
			 	//implement update function
			 
			 	updateTransactionFields(loanJson,voucherObj);
			 }else{
				 
			 }
	 	},function(err){
	 		console.log("Error while doing this ="+err);
	 	});

}

var updateTransactionFields =function(loanJson,respJson)
{
	let dueAmt =parseFloat(loanJson.dueAmount+loanJson.previousLoanAmount);
	let prevAmnt = parseFloat(loanJson.transaction_amount - loanJson.previousLoanAmount);
	
	console.log(respJson);
	$("#paid_amount").val(loanJson.previousLoanAmount);
	$("#prev_loan_amount").val(prevAmnt);
	
	
	console.log(dueAmt);
	$("#due_amount").val(dueAmt);
	
	let totalInterestAmnt=0;
	let old = moment(respJson.createdDate,"DD/MM/YYYY"); 
	let today = moment(date, "DD/MM/YYYY"); 
	let diff =  Math.abs(today.diff(old,"days"));
	
	if((diff-31)>0){
	    	let noOfDays = (diff-31);
	        totalInterestAmnt = (respJson.interestRate*dueAmt*noOfDays)/100;
	   		
	   		//prePaidInterest=(rate*paidAmt*noOfDays)/100;
	     }else
     {
	    	let noOfDays = (diff);
	    	console.log("noOfDays => "+noOfDays);
	        totalInterestAmnt = (respJson.interestRate*dueAmt)/100;
	    	
	    	//prePaidInterest=(rate*paidAmt)/100;
	     }
	     
	
	//let prevIAmt = loanJson.interestPaidAmount;
	//let dueIAmt= 
	
	
	$("#total_interest_amount").val(roundTo(totalInterestAmnt,2));	       	    		 
 $("#prev_interest_paid").val();
 $("#paid_interest_amount").val(loanJson.interestPreviousLoanAmount);
 $("#due_interest_amount").val(roundTo(totalInterestAmnt,2));
 $("#due_interest_amount").attr("data-init",roundTo(totalInterestAmnt,2));
 
 $("#totalLoanAmount").val(roundTo(respJson.loanAmount,2));
 $("#totalLoanAmount").attr("data-init",respJson.loanAmount);
 $("#totalInterestAmount").val(roundTo(totalInterestAmnt,2));
 $("#totalInterestAmount").attr("data-init",totalInterestAmnt);
 $("#totalAmount").val(roundTo(totalInterestAmnt+respJson.loanAmount,2));
 $("#paidAmount").val(prevAmnt);
 $("#paidInterestAmount").val(loanJson.interestPaidAmount);
 $("#paidInterestAmount").attr("data-init",loanJson.interestPaidAmount);
 $("#paidAmount").attr("data-init",prevAmnt);
 $("#dueAmount").val(roundTo(dueAmt,2));
 $("#dueInterestAmount").val(roundTo(totalInterestAmnt,2));
 $("#netAmount").val(loanJson.interestPaidAmount+prevAmnt);
}



$("#can").click(function(){
	reset();
})









