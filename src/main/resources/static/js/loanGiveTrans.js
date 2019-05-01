

//update mode
$("#up").click(function(){
	
	showPreviousValuesOfTransaction(voucherObj);
	mode =1;
	$("#component1").hide();
	$("#component2").show(400);
	$(this).hide();
	toggleFields(mode);
	$("#serial").attr("disabled","disabled");
	$("#can").show();
	
	
	
	
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
$("#loan_amount").change(function(){
	
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
	var txt = $("#serial").val();
	var subTxt = txt.substring(3,6);
    let voucherId = parseInt(subTxt);
	
	 buildAjax(getAbsoluteUrl("mortgage-app/api/voucher/loan/id/"+voucherId),"GET").then(function(loanJson){
		 
		 	console.log("Loan Details");
	 		console.log(loanJson);
		 if(loanJson!=null && loanJson.length!=0)
			 {
			 	//try calling via id
			 	//implement update function
			 console.log(loanJson);
			 
			 	updateTransactionFields(loanJson[0],voucherObj);
			 }else{
				 
				 alert("Please make your first transaction before updating...");
				 callOnCancel();
			 }
	 	},function(err){
	 		console.log("Error while doing this ="+err);
	 	});

}

var updateTransactionFields =function(loanJson,respJson)
{
	
	
	let dueAmt =parseFloat(loanJson.dueAmount+loanJson.previousLoanAmount);
	
	var pa = parseFloat(loanJson.transaction_amount - dueAmt);
	let prevAmnt = pa>0 ? pa:0;
	let rate = parseFloat(respJson.transaction.account.interestRate);
	
	//set the loan id to a hidden field
	$("#update_prev_id").val(loanJson.id);
	console.log("loan id = "+loanJson.id);
	
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
	
	var dia = roundTo(totalInterestAmnt,2)-loanJson.interestPaidAmount;
	
	var pi = (loanJson.interestPaidAmount-loanJson.interestPreviousLoanAmount);
	var prevInt = pi>0 ? pi:0;
	
	$("#total_interest_amount").val(roundTo(totalInterestAmnt,2));	       	    		 
 $("#prev_interest_paid").val(prevInt);
 $("#paid_interest_amount").val(loanJson.interestPreviousLoanAmount);
 $("#due_interest_amount").val(dia);
 $("#due_interest_amount").attr("data-init",dia);
 
 $("#totalLoanAmount").val(roundTo(loanJson.transaction_amount,2));
 $("#totalLoanAmount").attr("data-init",loanJson.transaction_amount);
 $("#totalInterestAmount").val(roundTo(totalInterestAmnt,2));
 $("#totalInterestAmount").attr("data-init",totalInterestAmnt);
 $("#totalAmount").val(roundTo(totalInterestAmnt+loanJson.transaction_amount,2));
 $("#paidAmount").val(prevAmnt);
 $("#paidInterestAmount").val(prevInt);
 $("#paidInterestAmount").attr("data-init",prevInt);
 $("#paidAmount").attr("data-init",prevAmnt);
 $("#dueAmount").val(roundTo(dueAmt,2));
 $("#due_amount").attr("data-init",roundTo(dueAmt,2));
 $("#dueInterestAmount").val(dia);
 $("#netAmount").val(loanJson.interestPaidAmount+prevAmnt);
 
 
 
 
 
 
}

var saveOrUpdate1 = function(mode,type){
	   
	   let loanTransaction = {}; 
	 
	   
	   
	var txt =$("#serial").val(); 
	var subTxt = txt.substring(3,6);
   let voucherId = parseInt(subTxt);
   
   
	   loanTransaction["id"]=parseInt($("#update_prev_id").val());
	   
	   
	   loanTransaction["type"]=type; 
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
				$("#component2").hide();
				
				let loanTransaction = {}; 
				 
				 
				$("#up").show();
				$("#voucher_id").removeAttr("disabled");
				$("#serial").removeAttr("disabled");
				reset();
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


var callOnCancel = function(){
	
	mode =0;
	$(this).hide();
	$("#component2").hide();
	$("#component1").show(400);
	$("#up").show();
	toggleFields(mode);
	
	
	
	
	
	var txt = $("#serial").val();
	var subTxt = txt.substring(3,6);
    let voucherId = parseInt(subTxt);
	console.log("id="+txt);
	$("#serial").removeAttr("disabled");
	$("#update_prev_id").val("");
	apiCall(voucherId);
}


$("#can").click(function(){
	
	callOnCancel();
});









