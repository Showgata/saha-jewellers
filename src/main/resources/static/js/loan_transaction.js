var loanTransactionObj ={};
var interestTransactionObj={};

var lDetails;
var vDetails;
var currentPaidAmount;
var currentInterestPaidAmount;
var currentVoucherDetails;
var currentLoanDetails;
var  delivered =false;


//CALCULATE INTEREST 
var calculateInterest = function(receiveDate,transDate,rate,totalAmount,dueAmt)
{
	
	     let old = moment(receiveDate); 
	     let today = moment(transDate, "DD/MM/YYYY"); 
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


//Enter to search
$("#voucher_id").keypress(function(e) {
    if(e.which == 13) {
        
    	if($(this).val().trim()!=""){
    		var id = parseInt($(this).val());
    		apiCall(id);
    	}else{
    		alert("Enter valid voucher no..."); 
    	}
        
        
    }
});

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


var ifDelivered =function(flag){
	
	if(flag)
	{
		$("#save").attr("disabled",true);
		$("#delivery").attr("disabled",true);
		$("#paid_amount").attr("disabled",true);
		$("#paid_interest_amount").attr("disabled",true);
		
	}else{
		 $("#save").removeAttr('disabled');
			
			$("#delivery").removeAttr('disabled');
			$("#paid_amount").prop("disabled", false);
			$("#paid_interest_amount").prop("disabled", false);
	}
};


var setUp =function(voucherDetails,loanTransDetails)
{
	let d = moment();
	updatePrimaryFields(voucherDetails,d);
	
	if($("#update_id").val().trim()!="")
	{
		ifDelivered(0);
	}else{
		ifDelivered(loanTransDetails.flag);
	}
	
	
	
	
	if(voucherDetails.mortgage !=null){
		setDataTableInTransactionDetails(voucherDetails);
		setDataTableInProductDetails(voucherDetails);
	}
	
	fillUpLoanTransaction(loanTransDetails,voucherDetails,0);
	
}


//MAKE AN API CALL
var apiCall =function(voucherId)
{
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/filterById");
	
	$.ajax({
 	     type: "GET",
 	     url: URL,
 	   data:{id:voucherId},
 	    success: function(respJson) {
 	    	
 	    	$("#save").removeAttr('disabled');
 			$("#delivery").removeAttr('disabled');
 	    	
 	    	var val = respJson[0];
 	    	console.log(val);
 	    	var voucherDetails = val[1];
			var loanTransDetails = val[0];
			
			/*For managing states*/
			vDetails = voucherDetails;
			lDetails = loanTransDetails;
			
			/*Initial states*/
			currentVoucherDetails = voucherDetails;
			currentLoanDetails=loanTransDetails;
			
			setUp(voucherDetails,loanTransDetails);
			
 	  		},
 	  		error: function(e) { 
 	  	        alert("Enter valid voucher no"); 
 	  	    }   
 	})

}


//FILL-UP THE LOAN-TRANSACTION FIELDS
var fillUpLoanTransaction = function(loanTransDetails,voucherDetails,mode)
{
	var totalAmt = giveTransAndMortgageInfo(voucherDetails).transDetails.amount;
	var calPrev=0;
	var presentPaidVal=0;
	if(mode === 0 ){
		//For save mode
		calPrev= parseFloat(loanTransDetails.previousLoanAmount);
		$("#prev_loan_amount").val(calPrev);
		$("#prev_loan_amount").attr("data-init",calPrev);
		$("#paid_amount").val("");
		currentPaidAmount=0;
		$("#paid_amount").attr("data-init",parseFloat(loanTransDetails.paidAmount));
		//presentPaidVal=0;
	}else{
		
		//For update mode
		calPrev = parseFloat(loanTransDetails.previousLoanAmount)-parseFloat(loanTransDetails.paidAmount);
		$("#prev_loan_amount").val(calPrev);
		$("#prev_loan_amount").attr("data-init",calPrev);
		$("#paid_amount").attr("placeholder",parseFloat(loanTransDetails.paidAmount));
		currentPaidAmount = parseFloat(loanTransDetails.paidAmount);		
		$("#paid_amount").attr("data-init",parseFloat(loanTransDetails.paidAmount));
		//presentPaidVal=0;
		
	}
	
	
	//Due Interest Amount
	var dueAmt =totalAmt-calPrev;
	$("#due_amount").val(dueAmt); 
	$("#due_amount").attr("data-init",dueAmt);
	
	
	//for later use
	loanTransactionObj ={
			 totalAmount: totalAmt,
			 prevAmount:calPrev,
			 dueAmt:dueAmt,
			 paidAmount:presentPaidVal,
			 balance:loanTransDetails.balance
	}
	
	
	var date = $("#date").val();
	fillUpInterestTransactionFields(loanTransDetails,voucherDetails,date,mode);
	
	
	
}


//FILL-UP THE INTEREST-TRANSACTION FIELDS
var fillUpInterestTransactionFields = function(loanTransDetails,voucherDetails,date,mode)
{
	//Total Interest Amount
	var info = giveTransAndMortgageInfo(voucherDetails);
	var calInterestPrev = 0;
	var presentInterestPaidVal=0;
	
	var dueAmount = $("#due_amount").val();
	var totalInterestAmount = calculateInterest(voucherDetails.date,date,info.transDetails.rate,info.transDetails.amount,dueAmount);
	$("#total_interest_amount").val(roundTo(totalInterestAmount,2));
	$("#totalInterestAmount").val(roundTo(totalInterestAmount,2));
	
	if(mode === 0 ){
	
	//Prev Interest Field
	calInterestPrev = parseFloat(loanTransDetails.interestPreviousLoanAmount);
	$("#prev_interest_paid").val(calInterestPrev);
	
	//Paid Interest Field
	$("#paid_interest_amount").val("");
	$("#paid_interest_amount").attr("data-init",parseFloat(loanTransDetails.interestPaidAmount));
	
	presentInterestPaidVal=0;
	
	
	}else{
		
		//For update mode
		calInterestPrev = parseFloat(loanTransDetails.interestPreviousLoanAmount)-parseFloat(loanTransDetails.interestPaidAmount);
		$("#prev_interest_paid").val(calInterestPrev);
		$("#prev_interest_paid").attr("data-init",calInterestPrev);
		$("#paid_interest_amount").attr("placeholder",parseFloat(loanTransDetails.interestPaidAmount));
		currentInterestPaidAmount = parseFloat(loanTransDetails.interestPaidAmount);		
		$("#paid_interest_amount").attr("data-init",parseFloat(loanTransDetails.interestPaidAmount));
		presentInterestPaidVal=0;
		
		
	}

	//Due Interest Amount
	var dueInterestAmt=0;
	if(calInterestPrev <= totalInterestAmount)
		dueInterestAmt =totalInterestAmount-calInterestPrev;
	else
		dueInterestAmt=totalInterestAmount;
	
	 	$("#due_interest_amount").val(roundTo(dueInterestAmt,2)); 
	 
	
	 
	 //For later use
	 interestTransactionObj={
			 totalInterestAmount: totalInterestAmount,
			 prevInterestAmount:calInterestPrev,
			 dueInterestAmt:dueInterestAmt,
			 paidInterestAmount:presentInterestPaidVal
	 }
	
	 
	 //update the transaction Detail section
	 updateTransactionDetailFields(loanTransactionObj,interestTransactionObj);
	
}




//ON CHANGE PAID AMOUNT FIELD
$("#paid_amount").change(function(e){
	
	var paidAmt = $(this).val()==null?0.0:parseFloat($(this).val());
	var dueAmt = parseFloat($("#due_amount").val());
	var prevAmt = parseFloat($("#prev_loan_amount").val());
	
	
	if($("#update_id").val().trim()!="")
	{
		var overallSum = $("#total_paid_balance").val();
		var sub = overallSum -lDetails.paidAmount;
		var remainingAmt = lDetails.transaction_amount - sub;
		console.log("LIMIT = "+remainingAmt);
		
		if(paidAmt > remainingAmt)
		{
			alert("Paid Amount should be less than "+remainingAmt);
			paidAmt =0;
			$(this).val("");
			//$(this).focus();
		}
		
		
	}
	
	
	
	
	
	if(paidAmt > dueAmt)
	{
		alert("Paid Amount should be less than Due Amount");
		$(this).val("");
		//$(this).focus();
	}else
	{
		//If the paid amount is acceptable
		var remainingDue = dueAmt - paidAmt;
		prevAmt+=paidAmt;
		
		$("#paid_amount").attr("data-init",paidAmt);
		$("#due_amount").val(roundTo(remainingDue,2));
		$("#paid_amount").val("");
		$("#paid_amount").attr("placeholder","0.00");
		$("#prev_loan_amount").val(prevAmt);
		
		
		//for later use
		loanTransactionObj.dueAmt =remainingDue;
		loanTransactionObj.prevAmount=prevAmt;
		loanTransactionObj.paidAmount=paidAmt;
		
		var date = $("#date").val();
		
		console.log(vDetails+"==="+lDetails);
		
		if($("#update_id").val().trim()!=""){
			updateTransactionDetailFields(loanTransactionObj,interestTransactionObj);
		}else{
			fillUpInterestTransactionFields(lDetails,vDetails,date,0);
		}
		
		

		
	}	
	
	   
 });


//ON CHANGE PAID INTEREST AMOUNT
$("#paid_interest_amount").change(function(e){
	
	var paidInterestAmt = $(this).val()==null?0.0:parseFloat($(this).val());
	var dueInterestAmt = $("#due_interest_amount").val()==null?0.0:parseFloat($("#due_interest_amount").val());
	var prevInterestAmt = $("#prev_interest_paid").val()==null?0.0:parseFloat($("#prev_interest_paid").val());
	
	
	
	if($("#update_id").val().trim()!="")
	{
		var overallSum = $("#total_paid_interest_balance").val();
		var sub = overallSum -lDetails.paidInterestAmt;
		var remainingInterestAmt = lDetails.interest_amount - sub;
		console.log("LIMIT = "+remainingInterestAmt);
		
		if(paidInterestAmt > remainingInterestAmt)
		{
			alert("Paid Amount should be less than "+remainingInterestAmt);
			paidInterestAmt =0;
			$(this).val("");
			//$(this).focus();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	if(paidInterestAmt > dueInterestAmt)
	{
		alert("Paid Interest Amount should be less than Due Interest Amount");
		$(this).val("");
		
	}else
	{
		//If the paid interest amount is acceptable
		var remainingDue = dueInterestAmt - paidInterestAmt;
		prevInterestAmt+=paidInterestAmt;
		$("#paid_interest_amount").attr("data-init",paidInterestAmt);
		$("#due_interest_amount").val(roundTo(remainingDue,2));
		$("#paid_interest_amount").val("");
		$("#paid_interest_amount").attr("placeholder","0.00");
		$("#prev_interest_paid").val(prevInterestAmt);
		
		
		
		interestTransactionObj.prevInterestAmount =prevInterestAmt;
		interestTransactionObj.dueInterestAmt=remainingDue;
		interestTransactionObj.paidInterestAmount=paidInterestAmt;
		
		
		lDetails.interestPreviousLoanAmount = prevInterestAmt;
		
		
		 updateTransactionDetailFields(loanTransactionObj,interestTransactionObj);

	}	
	
	   
 });








//UPDATE TRANSACTION DETAIL FIELDS
var updateTransactionDetailFields = function(loanTransactionObj,interestTransactionObj)
{
	
	//total Loan Amount Field
	$("#totalLoanAmount").val(loanTransactionObj.totalAmount);
	$("#totalInterestAmount").val(roundTo(interestTransactionObj.totalInterestAmount,2));
	
	var totalAmount = loanTransactionObj.totalAmount+interestTransactionObj.totalInterestAmount;
	$("#totalAmount").val(roundTo(totalAmount,2));
	$("#dueAmount").val(roundTo(loanTransactionObj.dueAmt,2));
	$("#paidAmount").val(roundTo(loanTransactionObj.prevAmount,2));


	$("#paidInterestAmount").val(roundTo(interestTransactionObj.prevInterestAmount,2));
	$("#paidInterestAmount").attr("data-init",roundTo(interestTransactionObj.prevInterestAmount,2));  	
	$("#dueInterestAmount").val(roundTo(interestTransactionObj.dueInterestAmt,2));
	
	
	
	var netAmount = (loanTransactionObj.dueAmt+interestTransactionObj.dueInterestAmt);
	$("#netAmount").val(roundTo(netAmount,2));
	
	
	
	
}




//Save loan transaction

var saveOrUpdate = function(type,mode){
	
	var retVal = confirm("Do you want to continue ?");
	
	if(retVal){
	   
	let loanTransaction = {}; 
	 
	var txt =$("#voucher_id").val(); 
	
	if(type === "loan_take" || type ==="loan_give"){
		txt = txt.substring(3,6).trim();
	}
	
	console.log("Voucher ID"+txt);
	 
	 loanTransaction["flag"]=formatIntegerValue("0");
	 loanTransaction["type"]=type;
	 loanTransaction["voucherId"] = formatIntegerValue(txt);
	 loanTransaction["dueAmount"] = formatFloatValue("0.00");
	 loanTransaction["paidAmount"] = roundTo(loanTransactionObj.paidAmount,2);
	 loanTransaction["previousLoanAmount"] = loanTransactionObj.prevAmount;
	 loanTransaction["transaction_amount"] = formatFloatValue($("#tot_loan_amnt").val());
	 var da = moment($("#date").val().trim(),"DD/MM/YYYY").format("MM/DD/YYYY");
	 loanTransaction["date"] = new Date(da);
	
	 loanTransaction["interest_amount"] = formatFloatValue(roundTo(interestTransactionObj.totalInterestAmount,2));
	 loanTransaction["interestDueAmount"] = formatFloatValue(roundTo(interestTransactionObj.dueInterestAmt,2));
	 loanTransaction["interestPaidAmount"] = formatFloatValue(roundTo(interestTransactionObj.paidInterestAmount,2));
	 loanTransaction["interestPreviousLoanAmount"] = formatFloatValue(roundTo(interestTransactionObj.prevInterestAmount,2));
	 
	 var agg = parseFloat(roundTo(loanTransactionObj.paidAmount,2)) + parseFloat(roundTo(interestTransactionObj.paidInterestAmount,2));
	 
	 loanTransaction["balance"] = loanTransactionObj.balance+agg;	 
	 
	 if($("#update_id").val().trim()!="")
		{
		 	console.log("currentPaidAmount="+currentPaidAmount);
		 	console.log("currentInterestPaidAmount="+currentInterestPaidAmount);
		 	console.log("loanTransactionObj.balance="+loanTransactionObj.balance);
		 	
			loanTransaction["id"]=parseInt($("#update_id").val().trim());
			
			var prevBalance = loanTransactionObj.balance -((currentPaidAmount)+(currentInterestPaidAmount));
			loanTransaction.balance = formatFloatValue(prevBalance+agg);
			console.log("interest balance="+formatFloatValue(prevBalance+agg));
		}
	 
	 console.log("loan transaction args=");
	 console.log(loanTransaction);
	 let url = getAbsoluteUrl("mortgage-app/api/voucher/loan/");
	 buildAjaxForJson(url, "POST", loanTransaction).then(function(respJson){
		 console.log(respJson);
		 
		 //Add or subtract from all previous values
		 if($("#update_id").val().trim()!="")
			{
				var id=parseInt($("#update_id").val().trim());
				var voucherId =parseInt(respJson.voucherId);
				var diff = parseFloat(respJson.paidAmount)-parseFloat(currentPaidAmount);
				var intDiff =parseFloat(respJson.interestPaidAmount)-parseFloat(currentInterestPaidAmount);
				
				console.log("Im here to update");
				
				var URL = getAbsoluteUrl("mortgage-app/api/voucher/updateAmounts");
				
				$.ajax({
			 	     type: "POST",
			 	     url: URL,
			 	   data:{voucherId,id,amnt:diff,intAmnt:intDiff},
			 	    success: function(respJson) {
			 	    	
			 	    	console.log(respJson);
			 	  	}  
			 	})
	
			}else{console.log("Im not here");}
		 
		 
		 if(mode === 1)
			 {
			 	
				$("#component1").show(400);
				$("#component2").hide();
				 
				$("#up").show();
				$("#voucher_id").removeAttr("disabled");
				alert("updated LoanTransaction");
			 }
		 else{
			alert("saved LoanTransaction");
			 reset();
			 
			 if($("#update_id").val().trim()!="")
			{
				 window.location.href=getAbsoluteUrl("mortgage-app/web/voucher_transaction");
			}
		 }
		 
		 console.log(respJson);
		
	 },function(reason){
	           	 console.log("error in processing your request while saving loanTransaction", reason);
	}); 
	   
	}else{setUp(currentVoucherDetails,currentLoanDetails);}   
	
}




//Delivery
var deliveryBtn = function(type){
	
	
	var retVal = confirm("Do you want to continue ?");
	
	if(retVal){
	
	let loanTransaction = {}; 
	 
		var txt =$("#voucher_id").val(); 
		//var subTxt = txt.substring(3,6);
    
		if(type === "loan_take" || type ==="loan_give"){
			txt = txt.substring(3,6).trim();
		}
		
		
		if($("#update_id").val().trim()!="")
		{
			loanTransaction["id"]=parseInt($("#update_id").val().trim());
		}
		 
    loanTransaction["flag"]=formatIntegerValue("1");
    loanTransaction["type"]=type;//("mortgage");
		 loanTransaction["voucherId"] = formatIntegerValue(txt);
		 loanTransaction["dueAmount"] = formatFloatValue("0.00");
		 loanTransaction["paidAmount"] = roundTo(loanTransactionObj.paidAmount,2);
		 loanTransaction["previousLoanAmount"] = loanTransactionObj.prevAmount;
		 loanTransaction["transaction_amount"] = formatFloatValue($("#tot_loan_amnt").val());
		 var da = moment($("#date").val().trim(),"DD/MM/YYYY").format("MM/DD/YYYY");
		 loanTransaction["date"] = new Date(da);
		
		 loanTransaction["interest_amount"] = formatFloatValue(roundTo(interestTransactionObj.totalInterestAmount,2));
		 loanTransaction["interestDueAmount"] = formatFloatValue(roundTo(interestTransactionObj.dueInterestAmt,2));
		 loanTransaction["interestPaidAmount"] = formatFloatValue(roundTo(interestTransactionObj.paidInterestAmount,2));
		 loanTransaction["interestPreviousLoanAmount"] = formatFloatValue(roundTo(interestTransactionObj.prevInterestAmount,2));
		 
		 var agg = parseFloat(roundTo(loanTransactionObj.paidAmount,2)) + parseFloat(roundTo(interestTransactionObj.paidInterestAmount,2));
		 loanTransaction["balance"] = loanTransactionObj.balance+agg;	
		 
		 if($("#update_id").val().trim()!="")
			{
			 	console.log("currentPaidAmount="+currentPaidAmount);
			 	console.log("currentInterestPaidAmount="+currentInterestPaidAmount);
			 	console.log("loanTransactionObj.balance="+loanTransactionObj.balance);
			 	
				loanTransaction["id"]=parseInt($("#update_id").val().trim());
				
				var prevBalance = loanTransactionObj.balance -((currentPaidAmount)+(currentInterestPaidAmount));
				loanTransaction.balance = formatFloatValue(prevBalance+agg);
				console.log("interest balance="+formatFloatValue(prevBalance+agg));
			}
		 
		 console.log(loanTransaction);
		 let url = getAbsoluteUrl("mortgage-app/api/voucher/loan/");
		 buildAjaxForJson(url, "POST", loanTransaction).then(function(respJson){
			 
			 
			 alert("delivered LoanTransaction");
			 console.log(respJson);
			 reset();
			 
			 if($("#update_id").val().trim()!="")
				{
					 window.location.href=getAbsoluteUrl("mortgage-app/web/voucher_transaction");
				}
			 
			 
		 },function(reason){
	           	 console.log("error in processing your request while saving loanTransaction", reason);
   	});
	
	}else{setUp(currentVoucherDetails,currentLoanDetails);}   
	
	
	
}



/*
$("#save").click(function(e){
	saveOrUpdate(0);
});*/




//reset
function reset(){
	   $("#voucher_id").val("");
	   $("#due_interest_amount").val("");
	   $("#paid_amount").val("");
	   $("#prev_loan_amount").val("");
	   $("#tot_loan_amnt").val("");
	   $("#total_interest_amount").val("");
	   $("#due_interest_amount").val("");
	   $("#paid_interest_amount").val("");
	   $("#prev_interest_paid").val("");
	   
	   
	   $("#cust_address").val("");
	       $("#cust_phone").val(""); 
	       $("#customer_name").val("");
	       $("#customer_id").val("");
	      $("#cust_serialNo").val("");
	       	    
	       	    $("#days_count").val("");
	       	    $("#loan_amount").val("");
	       	    $("#rate").val("");
	       	    $("#tot_loan_amnt").val("");
	       	 	$("#due_amount").val("");
	       	    $("#date").datepicker(dateSettings);
	       	    $("#date").val(moment().format("DD/MM/YYYY"));
	
	       	    
	       	    //================================modification
	       	    $("#receive_date").val("");
	       	 	$("#serial").val("");
	       	 	
	       		$("#totalLoanAmount").val("");
	       		$("#totalInterestAmount").val("");
	       		$("#paidInterestAmount").val("");
	       		$("#dueInterestAmount").val("");
	       		$("#paidAmount").val("");
	       		$("#totalAmount").val("");
	       		$("#dueAmount").val("");
	       		$("#netAmount").val("");
	       		$("#no_Of_days").val("");
	       		
	       		
	       		
	       	$("#bori").val(0.0);
	       $("#ana").val(0.0);
	       $("#ratti").val(0.0);
	       $("#gram").val(0.0);
	       
	       
	       $("#save").removeAttr('disabled');
			$("#paid_amount").prop("disabled", false);
			$("#paid_interest_amount").prop("disabled", false);
			$("#delivery").removeAttr('disabled');
	       	 	   
	       	 
 }



$("#cancel").click(function()
    	{reset();})









