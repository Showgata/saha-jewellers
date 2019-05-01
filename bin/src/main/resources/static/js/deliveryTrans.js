

//update mode
$("#up").click(function(){
	
	showPreviousValuesOfTransaction(voucherObj);
	mode =1;
	$("#component1").hide();
	$("#component2").show(400);
	$(this).hide();
	toggleFields(mode);
	$("#voucher_id").attr("disabled","disabled");
	
	
	
	
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
	var txt = $("#voucher_id").val();
	var subTxt = txt.substring(3,6);
    let voucherId = parseInt(subTxt);
	console.log("id="+txt);
	
	 buildAjax(getAbsoluteUrl("mortgage-app/api/voucher/loan/id/"+voucherId),"GET").then(function(loanJson){
		 
		 	console.log("Loan Details");
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
	let prevAmnt = parseFloat(loanJson.transaction_amount - dueAmt);
	let rate = parseFloat(respJson.mortgage.interestRate);
	
	
	console.log(respJson);
	$("#paid_amount").val(loanJson.previousLoanAmount);
	$("#prev_loan_amount").val(prevAmnt);
	
	
	console.log(dueAmt);
	$("#due_amount").val(dueAmt);
	
	let totalInterestAmnt=0;
	let old = moment(respJson.date,"DD/MM/YYYY"); 
	let today = moment(date, "DD/MM/YYYY"); 
	let diff =  Math.abs(today.diff(old,"days"));
	
	if((diff-31)>0){
	    	let noOfDays = (diff-31);
	        totalInterestAmnt = (rate*dueAmt*noOfDays)/100;
	   		
	   		//prePaidInterest=(rate*paidAmt*noOfDays)/100;
	     }else
     {
	    	let noOfDays = (diff);
	    	console.log("noOfDays => "+noOfDays);
	        totalInterestAmnt = (rate*dueAmt)/100;
	    	
	    	//prePaidInterest=(rate*paidAmt)/100;
	     }
	     
	
	//let prevIAmt = loanJson.interestPaidAmount;
	//let dueIAmt= 
	
	
	$("#total_interest_amount").val(roundTo(totalInterestAmnt,2));	       	    		 
 $("#prev_interest_paid").val();
 $("#paid_interest_amount").val(loanJson.interestPreviousLoanAmount);
 $("#due_interest_amount").val(roundTo(totalInterestAmnt,2));
 $("#due_interest_amount").attr("data-init",roundTo(totalInterestAmnt,2));
 
 $("#totalLoanAmount").val(roundTo(respJson.mortgage.loanAmount,2));
 $("#totalLoanAmount").attr("data-init",respJson.mortgage.loanAmount);
 $("#totalInterestAmount").val(roundTo(totalInterestAmnt,2));
 $("#totalInterestAmount").attr("data-init",totalInterestAmnt);
 $("#totalAmount").val(roundTo(totalInterestAmnt+respJson.mortgage.loanAmount,2));
 $("#paidAmount").val(prevAmnt);
 $("#paidInterestAmount").val(loanJson.interestPaidAmount);
 $("#paidInterestAmount").attr("data-init",loanJson.interestPaidAmount);
 $("#paidAmount").attr("data-init",prevAmnt);
 $("#dueAmount").val(roundTo(dueAmt,2));
 $("#dueInterestAmount").val(roundTo(totalInterestAmnt,2));
 $("#netAmount").val(loanJson.interestPaidAmount+prevAmnt);
 
 
 
 
 
 
}


var saveOrUpdate = function(mode){
	   
	   let loanTransaction = {}; 
	 
	var txt =$("#voucher_id").val(); 
	var subTxt = txt.substring(3,6);
   let voucherId = parseInt(subTxt);
	 
   loanTransaction["flag"]=formatIntegerValue("0");
	 loanTransaction["voucherId"] = formatIntegerValue(voucherId);
	 loanTransaction["dueAmount"] = formatFloatValue($("#due_amount").val());
	 loanTransaction["paidAmount"] = formatFloatValue($("#paidAmount").val());
	 loanTransaction["previousLoanAmount"] = formatFloatValue($("#prev_loan_amount").attr("data-init"));
	 loanTransaction["transaction_amount"] = formatFloatValue($("#tot_loan_amnt").val());
	 loanTransaction["date"] = $("#date").val();
	 loanTransaction["interest_amount"] = formatFloatValue($("#total_interest_amount").val());
	 loanTransaction["interestDueAmount"] = formatFloatValue($("#due_interest_amount").val());
	 loanTransaction["interestPaidAmount"] = formatFloatValue($("#paidInterestAmount").val());
	 loanTransaction["interestPreviousLoanAmount"] = formatFloatValue($("#prev_interest_paid").attr("data-init"));
	 
	 
	 let url = getAbsoluteUrl("mortgage-app/api/voucher/loan/");
	 buildAjaxForJson(url, "POST", loanTransaction).then(function(respJson){
		 
		 
		 if(mode === 1)
			 {
			 	
				$("#component1").show(400);
				$("#component2").hide();let loanTransaction = {}; 
				 
				var txt =$("#voucher_id").val(); 
				var subTxt = txt.substring(3,6);
			   let voucherId = parseInt(subTxt);
				 
			   loanTransaction["flag"]=formatIntegerValue("0");
				 loanTransaction["voucherId"] = formatIntegerValue(voucherId);
				 loanTransaction["dueAmount"] = formatFloatValue($("#due_amount").val());
				 loanTransaction["paidAmount"] = formatFloatValue($("#paidAmount").val());
				 loanTransaction["previousLoanAmount"] = formatFloatValue($("#prev_loan_amount").attr("data-init"));
				 loanTransaction["transaction_amount"] = formatFloatValue($("#tot_loan_amnt").val());
				 loanTransaction["date"] = $("#date").val();
				 loanTransaction["interest_amount"] = formatFloatValue($("#total_interest_amount").val());
				 loanTransaction["interestDueAmount"] = formatFloatValue($("#due_interest_amount").val());
				 loanTransaction["interestPaidAmount"] = formatFloatValue($("#paidInterestAmount").val());
				 loanTransaction["interestPreviousLoanAmount"] = formatFloatValue($("#prev_interest_paid").attr("data-init"));
				 
				$("#up").show();
				$("#voucher_id").removeAttr("disabled");
				alert("updated LoanTransaction");
			 }
		 else{
			alert("saved LoanTransaction");
			 reset();
		 }
		 
		 console.log(respJson);
		
	 },function(reason){
	           	 console.log("error in processing your request while saving loanTransaction", reason);
  	}); 
	   
	   
	   
}


$("#can").click(function(){
	
	var txt = $("#voucher_id").val();
	var subTxt = txt.substring(3,6);
    let voucherId = parseInt(subTxt);
	console.log("id="+txt);
	$("#voucher_id").removeAttr("disabled");
	apiCall(voucherId);
});


$("#update").click(function(){
	
	saveOrUpdate(1);
	
	
});






