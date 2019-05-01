var getTodaysDate =function()
{
	return moment().format("DD/MM/YYYY");
}




var loanURL = getAbsoluteUrl("/mortgage-app/api/voucher/getDailyLoanReport");
//var dailyDeliveryURL =getAbsoluteUrl("mortgage-app/api/voucher/filter");
var dailyExpenseURL =getAbsoluteUrl("/mortgage-app/api/exp/search");
var loanUpdateURL = getAbsoluteUrl("/mortgage-app/api/voucher/getDailyLoanUpdateReport");
var URL =getAbsoluteUrl("/mortgage-app/api/voucher/getDailyReport");


var receiveParams ={flag:formatIntegerValue("0"),type:"mortgage",date:getTodaysDate()};
var deliveryParams ={flag:formatIntegerValue("1"),type:"mortgage",date:getTodaysDate()};

var loantakeParams = {type:"loan_take",date:getTodaysDate()};
var loanUpdateParams = {type:"mortgage",date:getTodaysDate()};
var loangiveParams = {type:"loan_give",date:getTodaysDate()};
var capitalParams = {type:"capital",date:getTodaysDate()};
var expenseParams = {date:getTodaysDate()};


var receiveTable;

$(document).ready(function(){
	//initial datepicker
	
	var dateSetting = {
            language: 'en',
            //maxDate: new Date(),
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
		
		$("#daily_date").datepicker(dateSetting);
		//date format changed
		   $("#daily_date").datepicker({
			   dateFormat: "dd/mm/yyyy"
		   });
		   $("#daily_date").val(moment().format("DD/MM/YYYY"));
	
	
	//transaction update section
	apiCall(loanUpdateURL,loanUpdateParams,updateTableTransUpdateDailyFun,$("#transUpdate_daily"));
	
	
	//receive section
	apiCall(loanURL,receiveParams,updateTableReceiveDailyFun,$("#receive_daily"));
	
	//delivery section
	apiCall(loanURL,deliveryParams,updateTableDeliveryDailyFun,$("#delivery_daily"));
	
	//expense section
	apiCall(dailyExpenseURL,expenseParams,updateTableExpenseDailyFun,$("#expense_daily"));
	
	/*
	//loan-take section
	apiCall(URL,loantakeParams,updateTableLoanTakeDailyFun,$("#loan_take_daily"));
	
	//loan-give section
	apiCall(URL,loangiveParams,updateTableLoanGiveDailyFun,$("#loan_give_daily"));
	*/
	
	//capital section
	apiCall(URL,capitalParams,updateTableCapitalDailyFun,$("#capital_daily"));
	
	
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


var getPrefix = function(type)
{
	if(type != null)
		{
			if(type == 'mortgage')
				{return '';}
			else if(type == 'loan_give')
				{return 'LG-';}
			else{return 'LT-';}
		}
} 



//Basic api call
var apiCall =function(URL,receiveParams,updateTableFun,table){
	
	
	$.ajax({
     type: "GET",
     url: URL,
     data:receiveParams,
     // parameters
  		success: function(respJson) {
  			//console.log(respJson);	
  			updateTableFun(respJson,table);
  		}
	})

}


//Update for Receive table
var updateTableReceiveDailyFun = function(respJson,table)
{
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, val) {
		
		var voucherDetails = val[1];
		var loanTransDetails = val[0];
		
		if(voucherDetails!=null && loanTransDetails!=null){
		
		var info = giveTransAndMortgageInfo(voucherDetails);
		
		 data.push([
			 getPrefix(voucherDetails.type)+voucherDetails.id,
			 voucherDetails.customer.customerName,
			 voucherDetails.customer.customerId,
			 info.transDetails.rate,
			 info.transDetails.amount
			 /*
			 loanTransDetails.paidAmount,
			 loanTransDetails.previousLoanAmount,
			 loanTransDetails.interestPreviousLoanAmount,
			 loanTransDetails.balance*/
			 
		 ]); 
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    /*
    columnDefs: [
        {
        	"searchable": true,
            "targets": [ 0 ],
            "visible": false,
            
        }
        ],*/
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,4);
		 $("#rec_voucher_count").val(obj.rowsCount);
		 $("#rec_total_amount").val(obj.total);
    } });
}

//Count and sum
var countAndSum = function(api,columnNo){
	var intVal = function ( i ) {
        return typeof i === 'string' ?
            i.replace(/[\$,]/g, '')*1 :
            typeof i === 'number' ?
                i : 0;
    };

    // Total over all pages
    var total = api
        .column( columnNo )
        .data()
        .reduce( function (a, b) {
            return intVal(a) + intVal(b);
        }, 0 );

    var rowsCount = api
    .column( columnNo )
    .data().length;
    
    return {total,rowsCount};
}




//Update for Delivery table
var updateTableDeliveryDailyFun = function(respJson,table)
{
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, val) {
		
		var voucherDetails = val[1];
		var loanTransDetails = val[0];
		
		if(voucherDetails!=null && loanTransDetails!=null){
		
		var info = giveTransAndMortgageInfo(voucherDetails);
		
		 data.push([
			 getPrefix(voucherDetails.type)+voucherDetails.id,
			 voucherDetails.customer.customerName,
			 voucherDetails.customer.customerId,
			 /*
			 info.transDetails.rate,
			 info.transDetails.amount,
			 loanTransDetails.paidAmount,*/
			 loanTransDetails.previousLoanAmount,
			 loanTransDetails.interestPreviousLoanAmount,
			 loanTransDetails.balance
			 
		 ]); 
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,/*
    columnDefs: [
        {
        	"searchable": true,
            "targets": [ 0 ],
            "visible": false,
            
        }
        ],*/
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,5);
		 $("#del_voucher_count").val(obj.rowsCount);
		 $("#del_total_amount").val(obj.total);
    } });
}



//select in delivery transaction
$(".daily_delivery_select").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    
    var date =$("#daily_date").val().trim();
    var deliveryParams ={flag:formatIntegerValue("1"),type:"mortgage",date};
    
    switch(valueSelected){
    
    case "loan_take":
    	deliveryParams.type = "loan_take";
    	apiCall(loanURL,deliveryParams,updateTableDeliveryDailyFun,$("#delivery_daily"));
    	break;
    	
    case "mortgage":
    	deliveryParams.type = "mortgage";
    	apiCall(loanURL,deliveryParams,updateTableDeliveryDailyFun,$("#delivery_daily"));
    	break;
   
    case "loan_give":
    	deliveryParams.type = "loan_give";
    	apiCall(loanURL,deliveryParams,updateTableDeliveryDailyFun,$("#delivery_daily"));
    	break;	
    
    }
});





//Update for Expense table
var updateTableExpenseDailyFun = function(respJson,table)
{
	console.log("Expense");
	console.log(respJson);
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, exp) {
		
		
		if(exp!=null){
			
			var val = exp[0];
			
		 data.push([
			 val.expenseHead,
			 (val.id==null?"":"EX-"+val.id),
 				moment(val.date).format("DD/MM/YYYY"),
 				(val.amount==null?"":val.amount),
 				(val.note.trim()==null?"-":val.note),
			 
		 ]); 
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,3);
		 $("#exp_voucher_count").val(obj.rowsCount);
		 $("#total_expense").val(obj.total);
    } });
}

/*
//Update for Loan-Take table
var updateTableLoanTakeDailyFun = function(respJson,table)
{
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, val) {
		
		if(val!=null){
		
		
		
		 data.push([
			 (val.customer==null?"":val.customer.customerName),
				("LT-"+val.id),
				(val.date==null?"==":moment(val.date).format("DD/MM/YYYY")),
				(val.transaction.account.interestRate),
				(val.transaction.account.amount)
			 
		 ]); 
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,4);
		 $("#lt_voucher_count").val(obj.rowsCount);
		 $("#lt_loan_amount").val(obj.total);
    } });
}




//Update for Loan-Give table
var updateTableLoanGiveDailyFun = function(respJson,table)
{
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, val) {
		
		if(val!=null){
		
		
		
		 data.push([
			 (val.customer==null?"":val.customer.customerName),
				("LG-"+val.id),
				(val.date==null?"==":moment(val.date).format("DD/MM/YYYY")),
				(val.transaction.account.interestRate),
				(val.transaction.account.amount)
			 
		 ]); 
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,4);
		 $("#lg_voucher_count").val(obj.rowsCount);
		 $("#lg_loan_amount").val(obj.total);
    } });
}*/




//Update for Capital table
var updateTableCapitalDailyFun = function(respJson,table)
{
	table.DataTable().destroy();
	var data = [];
	jQuery.each(respJson, function(i, val) {
		
		if(val!=null){
		
		
		
		 data.push([
				 val.customer.customerName,
  				(val.id==null?"0":"CI-"+val.id),
  				moment(val.date).format("DD/MM/YYYY"),
  				(val.transaction.amount==null?"0.0":val.transaction.amount),
  				(val.transaction.note==null?"":val.transaction.note)
  			 ]);  
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		 var obj = countAndSum(api,3);
		 $("#cap_voucher_count").val(obj.rowsCount);
		 $("#cap_loan_amount").val(obj.total);
    } });
}



//Dropdown for receive
//select in receive transaction
$(".daily_receive").on('change', function (e) {
    
   
	var valueSelected = this.value;
	var date = $("#daily_date").val().trim();
    console.log(valueSelected);
    var receiveParams ={flag:formatIntegerValue("0"),type:"mortgage",date};
	   
    switch(valueSelected){
    
    case "loan_take":
    	receiveParams.type="loan_take";
    	apiCall(loanURL,receiveParams,updateTableReceiveDailyFun,$("#receive_daily"));
    	break;
    	
    case "mortgage":
    	receiveParams.type="mortgage";
    	apiCall(loanURL,receiveParams,updateTableReceiveDailyFun,$("#receive_daily"));
    	break;
   
    case "loan_give":
    	receiveParams.type="loan_give";
    	apiCall(loanURL,receiveParams,updateTableReceiveDailyFun,$("#receive_daily"));
    	break;	
    	
    
    }
    
    
});



//Transaction Update table
var updateTableTransUpdateDailyFun = function(respJson,table)
{
	console.log("==========================================");
	console.log("Transaction Update Data");
	console.log(respJson);
	console.log("==========================================");
	table.DataTable().destroy();
	
	var voucherid_array =[];
	var total_voucher_updates=0;
	var total_update_loan = 0;
	
	
	
	
	var data = [];
	console.log(data);
	jQuery.each(respJson, function(i, loanTransDetails) {
		
		
		if(loanTransDetails!=null){
		
			var val =loanTransDetails[0];			
			total_voucher_updates+=loanTransDetails[1];
			total_update_loan+=(loanTransDetails[2]+loanTransDetails[3]);
			
			
			data.push([
				loanTransDetails[0].id,
				loanTransDetails[0].customer.customerName,
				loanTransDetails[0].customer.customerId,
				loanTransDetails[2],
				loanTransDetails[3]
			]);
			
		}
	});
	
	table.DataTable({
	data:           data,
	deferRender:    true,
    scrollCollapse: true,
    displayLength :5,
    columnDefs: [
        
        ],
    "order": [ 0, "desc" ],
    "lengthChange": false,
	"footerCallback": function ( row, data, start, end, display ) {
		 var api = this.api(), data;
		 
		$("#tu_voucher_count").val(total_voucher_updates);
		$("#tu_total_amount").val(total_update_loan);
    } });
}

/*
var groupById = function(respJson) {
	
	var sp =[];
	var id = respJson[0][1]!=null?respJson[0][1]:-99;
	console.log("id ="+id);
	var paidSum=0;
	var paidInterestSum=0;
	var temp = [];
	
	if(id != -99){
	
	jQuery.each(respJson, function(i, loanTransDetails) {
		
		temp[0]=loanTransDetails[0].id;
		temp[1]=loanTransDetails[0].customer.customerName;
		temp[2]=loanTransDetails[0].customer.customerId;
		temp[3]=paidSum;
		temp[4]=paidInterestSum;
		
		paidSum+=loanTransDetails[2];
		paidInterestSum+=loanTransDetails[3];
		
		console.log("paidSum="+paidSum+" paidInterestSum="+paidInterestSum);
		
		if(id != loanTransDetails[1]){
			
			sp.push(temp);
			temp =[];
			id = loanTransDetails[1];
			paidSum=0;
			paidInterestSum=0;
		}
		
	});
	
	sp.push(temp);
	}
	  
	return sp;
	};

*/
//Dropdown for update
//select in update transaction
$(".daily_transUpdate").on('change', function (e) {
  
 
	var valueSelected = this.value;
	var date = $("#daily_date").val().trim();
  console.log(valueSelected);
  var loanUpdateParams = {type:"mortgage",date};
	   
  switch(valueSelected){
  
  case "loan_take":
	  loanUpdateParams.type="loan_take";
  	apiCall(loanUpdateURL,loanUpdateParams,updateTableTransUpdateDailyFun,$("#transUpdate_daily"));
  	break;
  	
  case "mortgage":
	  loanUpdateParams.type="mortgage";
  	apiCall(loanUpdateURL,loanUpdateParams,updateTableTransUpdateDailyFun,$("#transUpdate_daily"));
  	break;
 
  case "loan_give":
	  loanUpdateParams.type="loan_give";
  	apiCall(loanUpdateURL,loanUpdateParams,updateTableTransUpdateDailyFun,$("#transUpdate_daily"));;
  	break;	
  	
  
  }
  
  
});



var changeDateAndShowGraph = function(e){
	var date =$("#daily_date").val().trim();//new Date(moment($("#daily_date").val().trim()).format("DD/MM/YYYY")) ;
	
	var receiveParams ={flag:formatIntegerValue("0"),type:"mortgage",date};
	var deliveryParams ={flag:formatIntegerValue("1"),type:"mortgage",date};

	var loantakeParams = {type:"loan_take",date};
	var loangiveParams = {type:"loan_give",date};
	var capitalParams = {type:"capital",date};
	var expenseParams = {date};
	var loanUpdateParams = {type:"mortgage",date};
	
	
	//transaction update section
	apiCall(loanUpdateURL,loanUpdateParams,updateTableTransUpdateDailyFun,$("#transUpdate_daily"));
	
	//receive section
	apiCall(loanURL,receiveParams,updateTableReceiveDailyFun,$("#receive_daily"));
	
	//delivery section
	apiCall(loanURL,deliveryParams,updateTableDeliveryDailyFun,$("#delivery_daily"));
	
	//expense section
	apiCall(dailyExpenseURL,expenseParams,updateTableExpenseDailyFun,$("#expense_daily"));
	
	/*
	//loan-take section
	apiCall(URL,loantakeParams,updateTableLoanTakeDailyFun,$("#loan_take_daily"));
	
	//loan-give section
	apiCall(URL,loangiveParams,updateTableLoanGiveDailyFun,$("#loan_give_daily"));
	*/
	
	//capital section
	apiCall(URL,capitalParams,updateTableCapitalDailyFun,$("#capital_daily"));
	
	
}

$("#show").click(changeDateAndShowGraph);






