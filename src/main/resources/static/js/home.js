var mortgagedetails=[];
var loanGivedetails=[];
var loanTakedetails=[];

var transId=0;

$(document).ready(function(){
	
	
	
	var today = moment().format("DD/MM/YYYY");
	fetchGoldMortgageDetails('mortgage',today);
	fetchLoanTakeDetails('loan_give',today);
	fetchLoanGiveDetails('loan_take',today);
	
	
	$(".date").datepicker({
		   dateFormat: "dd/mm/yyyy"
	   });
	
	
	
	 var disabledDays = [0];
     var dateSettings = {
         language: 'en',
         maxDate: new Date(),
         autoClose:true,
         onRenderCell: function (date, cellType) {
             if (cellType == 'day') {
                 var day = date.getDay(),
                     isDisabled = disabledDays.indexOf(day) != -1;

                 return {
                     disabled: isDisabled
                 }
             }
         }
     };

     dateSettings.maxDate=null;
     var d = new Date();
     d.setDate(d.getDate() - 1);
     dateSettings.minDate=d;
 	$("#updateRemainderDate").datepicker(dateSettings);


})



var fetchGoldMortgageDetails =function(type,date)
{
	apiCall(type,date);
}

var fetchLoanTakeDetails =function(type,date)
{
	apiCall(type,date);
}

var fetchLoanGiveDetails =function(type,date)
{
	apiCall(type,date);
}


var apiCall =function(type,today)
{
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/rdate");
	$.ajax({
	     type: "GET",
	     url: URL,
	    data:{date:today,type:type},
	  		success: function(respJson) {
	  			var balance =0;
	  			console.log(respJson);
	  			
	  			jQuery.each(respJson, function(i, value) {
	  					
	  				transactionApiCall(value,type);
	  				
				 });
	  			
	  			
	  			
	  			
	  		}
		});	
}






var transactionApiCall = function(val,type)
{
	
if(val!=null){
	
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/"+val.id);
	$.ajax({
	     type: "GET",
	     url: URL,
	  		success: function(respJson) {
	  			
	  				var loanTrans = respJson[0];
	  				
	  				if(loanTrans == undefined || loanTrans == null)
	  					{
	  					console.log("Here")
	  					loanTrans= calInterestDetails(val,type);
	  					}
	  				
	  				var copy = JSON.parse(JSON.stringify(val));
	  				copy["loanTransaction"]=loanTrans;
	 				
		  			
	  				
	  				
	  				
	  				var tableIns=null;
		  			if(type == 'mortgage')
		  			{
		  				tableIns =$("#mortgageTable");
		  				mortgagedetails.push(copy);
		  				updateTable(tableIns,mortgagedetails,type);
		  			}else if(type == 'loan_take')
		  			{
		  				tableIns =$("#loan-take");
		  				loanTakedetails.push(copy);
		  				updateTable(tableIns,loanTakedetails,type);
		  			}else
		  				{
		  				tableIns =$("#loan-give");
		  				loanGivedetails.push(copy);
		  				updateTable(tableIns,loanGivedetails,type);
		  				}
	  				
	  				
	  				
		  			
		  			
	  				
	  				

	  		}
		});
	
}
}




var updateTable =function(table,value,type)
{
	let data =[];
	
	table.DataTable().destroy();
	 
	 jQuery.each(value, function(i, val) {
		 console.log("Data Above");
		 console.log(val);
		 var formatId =getPrefix(type)+" "+val.id;
		 
		 
		 
		 
		 
		 var mortgageDetails={};
		 var transDetails={};
		 if(val.mortgage!=null){
			 mortgageDetails = {
					 bori:val.mortgage.bori,
					 ana:val.mortgage.ana,
					 ratti:val.mortgage.ratti,
					 gram:val.mortgage.gram
			 }
			 
			 transDetails={
					rate:val.mortgage.interestRate,
					amount:val.mortgage.loanAmount
			 }
			 
		 }else{
			 mortgageDetails = {
					 bori:0,
					 ana:0,
					 ratti:0,
					 gram:0
			 }
			 
			 transDetails={
						rate:val.transaction.account.interestRate,
						amount:val.transaction.account.amount
				 }
		 }

		 
		 data.push([(val.loanTransaction.id==null?"-":val.loanTransaction.id),
			 formatId,
			 val.customer.customerName,
			 val.customer.mobile,
			 val.customer.address,
			 val.customer.customerId,
			 val.date,
			 val.loanTransaction.interestDueAmount,
			 val.loanTransaction.dueAmount,
			transDetails.amount==null?"0.0":transDetails.amount,
			dayCount(val.date),
			val.transaction.reminderDate,
			"<input class='form-controlcol-sm-2  upd'  type='button' updid='"+val.id+"' upTrans='"+val.transaction.id+"' name='update'  data-toggle='modal' data-target='#myModal' value='update' id='update' />",
			"<input class='form-controlcol-sm-2  can' type='button' cancelId='"+val.id+"' updid='"+val.id+"' delTrans='"+val.transaction.id+"' name='cancel' value='cancel' id='cancel' />"
			
		 ]);
		 
		 
	 });
	 
	dataTable = table.DataTable({
	data:           data,
   deferRender:    true,
   scrollY:        200,
   scrollCollapse: true,
   scroller:       true,
   "order": [],
   "footerCallback": function ( row, data, start, end, display ) {
	   var api = this.api(), data;
    }
	 }); 
	
	
	
}
  






var calInterestDetails = function(val,type)
{
		let old = moment(val.date,"DD/MM/YYYY"); 
	     let today = moment(); 
	     let diff =  Math.abs(today.diff(old,"days"));
	     
	     
	     
	     var mortgageDetails={};
		 var transDetails={};
		 if(val.mortgage!=null){
			 mortgageDetails = {
					 bori:val.mortgage.bori,
					 ana:val.mortgage.ana,
					 ratti:val.mortgage.ratti,
					 gram:val.mortgage.gram
			 }
			 
			 transDetails={
					rate:val.mortgage.interestRate,
					amount:val.mortgage.loanAmount
			 }
			 
		 }else{
			 mortgageDetails = {
					 bori:0,
					 ana:0,
					 ratti:0,
					 gram:0
			 }
			 
			 transDetails={
						rate:val.transaction.account.interestRate,
						amount:val.transaction.account.amount
				 }
		 }
	     
	     
		let dueAmt = parseFloat(transDetails.amount);
	 	let totalAmt =parseFloat(transDetails.amount);	
	    let totalInterestAmnt=0; 
	     
	     if((diff-31)>0){
	   	    	let noOfDays = (diff-31);
	   	        totalInterestAmnt = (transDetails.rate*dueAmt*noOfDays)/100;
	   	   		
	   	   		//prePaidInterest=(rate*paidAmt*noOfDays)/100;
	   	     }else
		     {
	   	    	let noOfDays = (diff);
	   	    	console.log("noOfDays => "+noOfDays);
	   	        totalInterestAmnt = (transDetails.rate*dueAmt)/100;
	   	    	
	   	    	//prePaidInterest=(rate*paidAmt)/100;
	   	     }
	     
	     
	     
	     var loanTransaction ={};
	     
	     loanTransaction["flag"]=formatIntegerValue("0");
	     loanTransaction["type"]=type;
	    loanTransaction["voucherId"] = formatIntegerValue(val);
	    loanTransaction["dueAmount"] = formatFloatValue(dueAmt);
	    	 loanTransaction["paidAmount"] = formatFloatValue('0.00');
	    	 loanTransaction["previousLoanAmount"] = formatFloatValue('0.00');
	    	 loanTransaction["transaction_amount"] = formatFloatValue(totalAmt);
	    	 loanTransaction["date"] =val.date;
	    	 loanTransaction["interest_amount"] = formatFloatValue(totalInterestAmnt);
	    	 loanTransaction["interestDueAmount"] = formatFloatValue(totalInterestAmnt);
	    	 loanTransaction["interestPaidAmount"] = formatFloatValue('0.00');
	    	 loanTransaction["interestPreviousLoanAmount"] = formatFloatValue('0.00');
	     
	    	 
	    	 
	    	 return loanTransaction;

}



var getPrefix = function(type)
{
	if(type != null)
		{
			if(type == 'mortgage')
				{return 'DT-';}
			else if(type == 'loan_give')
				{return 'LG-';}
			else{return 'LT-';}
		}
}    





var dayCount = function(date)
{
let old = moment(date,"DD/MM/YYYY"); 
	let today = moment(); 
	let diff = today.diff(old,"days");		       	 
	return diff;

};


$("#updateDate").click(function(){
	
	var date = $("#updateRemainderDate").val();
	
	
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/reminderDate");
	$.ajax({
	     type: "POST",
	     url: URL,
	    data:{date:date,id:transId},
	  		success: function(respJson) {
	  			
	  			alert("Reminder Date Changed!");
	  			transId=0;	
				 }
	  			
	  		})
				
});


$('#mortgageTable tbody').on( 'click', '.upd',function(){
	transId=parseInt($(this).attr('upTrans'))
} );


$('#loan-take tbody').on( 'click', '.upd',function(){
	transId=parseInt($(this).attr('upTrans'))
} );

$('#loan-give tbody').on( 'click', '.upd',function(){
	transId=parseInt($(this).attr('upTrans'))
} );



$("#cancel").click(function(){
	
});
   



        
        
        