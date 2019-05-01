const transactionType = ["Mortgage","Loan Take","Loan Give"];
var tt = {Mortgage:"mortgage"
		,LoanTake:"loan_take",
		LoanGive:"loan_give"};
var loanObj =[];
var id=0;
var table;


var totaldueAmt =0;
var totalBalance=0;
var total=0;


$(document).ready(function(){
	setCustomerAutoCompleteList();
	initializeDataTable();
	
	
})


var initializeDataTable = function()
{
	var data={};
	
	$('#basic-datatables').DataTable().destroy();
	
	table = $('#basic-datatables').DataTable({
		data:           data,
        deferRender:    true,
        scrollY:        200,
        scrollCollapse: true,
        scroller:       true,
        columns: [
         		{ title: "Sl no"},
         		{ title: "Name"},
         		{ title: "Mobile"},
         		{ title: "Address"},
         		{ title: "Receive date"},
         		{ title: "Total Loan Amount"},
         		{ title: "Total Due Loan Amount"},
         		{ title: "Total Due Interest Amount"},
         		{ title: "Balance"}
     			],
        "order": [],
		 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;

// Remove the formatting to get integer data for summation
var intVal = function ( i ) {
    return typeof i === 'string' ?
        i.replace(/[\$,]/g, '')*1 :
        typeof i === 'number' ?
            i : 0;
};


//$("#voucher_count").val(rowsCount);

		 }
		 
		 
	})
	
}

	




function setCustomerAutoCompleteList(){
	   var URL = getAbsoluteUrl("mortgage-app/api/customer/");
	   var autoCompleteList = [];
	   buildAjax(URL, "GET").then(function(respJson){
		   customerObj = respJson;
		   jQuery.each(respJson, function(i, val) {
			   
				   autoCompleteList.push({value: val.customerName,  label: val.customerName+" > "+val.customerId});
			   
		   });
		   $( "#cust_name_id" ).autocomplete({
		        source: autoCompleteList,
		        select: function (event, ui) {
		        	var txt = ui.item.label;
		          let custId = parseInt(txt.split(">")[1]);
		          id=custId;
		          
		         
		          
		      }
		      });
	   }, function(reason){
      	 console.log("error in processing your request customer", reason);
   	}); 
}


$("#view_report").click(function(){
	
	totaldueAmt=0
	totalBalance=0;
	total=0;
	
	var option = $(".transactionType :selected");
	
	if(id === 0){
		id=parseInt($("#cust_name_id").val());
		}
	
	if(option.text().trim()==transactionType[0])
	{
		//call mortgage api
		apiCall(id,"DT-",tt.Mortgage);
	}else if(option.text().trim()==transactionType[1])
	{
		//call loan take api
		apiCall(id,"LT-",tt.LoanTake);
	}else{
		//call loan give api
		apiCall(id,"LG-",tt.LoanGive);
	}
	
	
});

var apiCall = function (id,prefix,type)
{
	
		var total=0;
   		var URL = getAbsoluteUrl("mortgage-app/api/voucher/id/");
   		
   		$('#basic-datatables').DataTable().destroy();
   		
   		$.ajax({
   	     type: "GET",
   	     url: URL,
   	     data: { cid:id,type:type} ,// parameters
   	  		success: function(respJson) {
   	  			
   	  		console.log(respJson);
   	  			
   	  		 jQuery.each(respJson, function(i, val) {
   	  			 loanTransactionApiCall(val,prefix);
  			 });
   	  			
   	  		
   	  			
   	  		}  
   	})
   	
   	

	
}


var loanTransactionApiCall = function(respJson,prefix){
	
	
		var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/"+respJson.id);
		
		$('#basic-datatables').DataTable().destroy();
		
		buildAjax(URL, "GET").then(function(loanJson){
			
			console.log(loanJson);
			loanObj.push(loanJson);
			
			updateTable(respJson,loanJson,prefix);
			//-------------------------------//
				//----------	---------//
			//-------------------------------//
			
			
		});
	  			
	  			
	  		
	  		
	     
	}


function moveDataTableRow(position,table){
	   var currentPage = table.page();
	   var index = position-1,
    rowCount = table.data().length-1,
    insertedRow = table.row(rowCount).data(),
    tempRow;

for (var i=rowCount;i>index;i--) {
    tempRow = table.row(i-1).data();
    table.row(i).data(tempRow);
    table.row(i-1).data(insertedRow);
}     
//refresh the current page
table.page(currentPage).draw(false);
}
	



var updateTable = function(val,loanJson,prefix){
	
		
			
			
 		console.log(val);
		var data = [];
		var loanTransaction = loanJson[0];
		
			 
			 //add all expenses
			
			
			if(loanJson.length === 0)
			{
				//when no transaction is made
				loanTransaction ={
						dueAmount:val.transaction.amount,
						interestDueAmount:0.0
						
				}
			}
			 
			var balance = loanTransaction.interestDueAmount+loanTransaction.dueAmount;
			 
			total += parseInt(loanTransaction.transaction_amount==null?"0.00":loanTransaction.transaction_amount);

			totaldueAmt += parseInt(loanTransaction.dueAmount==null?"0.00":loanTransaction.dueAmount);
			totalBalance +=balance;
			
			
			try{
				table.row.add([
				prefix+val.id,
				val.customer.customerName,
			val.customer.mobile,
			val.customer.address,
			val.date,
			(loanTransaction.transaction_amount==null?"0.00":loanTransaction.transaction_amount),
			(loanTransaction.dueAmount),
			(loanTransaction.interestDueAmount),
			balance
				]).draw();
		            
				
				
				moveDataTableRow(1,table);
				
			}catch(e){
				console.log(e);
			}
		
			
			
			$("#dt_total_amount").val(roundTo(total,2));
			$("#dt_due_amount").val(roundTo(totaldueAmt,2));
			$("#dt_balance").val(roundTo(totalBalance,2));
			
			
		//$("#basic-datatables").val(total);
	
	
}



 










