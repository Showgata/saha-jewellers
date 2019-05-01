var dataTable;
var loanObj=[];
var allDetails=[];

var transHistoryTable =null;

$(document).ready(function(){
	setAutoComplete();
	initializeDataTable();
	
	fetchDefaultTransactions();
	
	
	
	
	$(".date").datepicker(dateSettings);
	$(".date").datepicker({
		   dateFormat: "dd/mm/yyyy"
	   });
})



function setAutoComplete(){
     	   
     	   var autoCompleteList = [];
     	   var URL = getAbsoluteUrl("mortgage-app/api/voucher/");
     	   buildAjax(URL, "GET").then(function(respJson){
     		   jQuery.each(respJson, function(i, val) {
     			   
     				   autoCompleteList.push({value: val.id,  label: val.customer.customerName+" > "+val.id});
     			   
     		   });
     	   
     		 $( "#r_serial" ).autocomplete({
   		        source: autoCompleteList,
   		        select: function (event, ui) {
   		        	var txt = ui.item.label;
   		          //let voucherId = parseInt(txt.split(">")[1]);
   		          
   		          // 	$("#serial").val(voucherId); 
   		        
   		        
   		          
   		      }
   		      });
     	   }, function(reason){
            	 console.log("error in processing your request customer", reason);
          	}); 
        }



function toggleModal(){
       	$('#myModal').modal('toggle');
       }
       
       function toggleDeliveryModal(){
          	$('#deliveryModal').modal('toggle');
          }
       
       
       function toggleViewModal(){
         	$('#viewModal').modal('toggle');
         }


var initializeDataTable = function()
{
	var data={};
	
	$('#transactionHistory_tbl').DataTable().destroy();
	
	dataTable = $('#transactionHistory_tbl').DataTable({
		data:           data,
        deferRender:    true,
        scrollY:        200,
        scrollCollapse: true,
        scroller:       true,
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



function fetchDefaultTransactions(){
    	
    	   $('#transactionHistory_tbl').DataTable().destroy();
    	   
    	   apiCall("mortgage");
    	   	
    	   
}
   

//==============================================================
//in receive transaction

var apiCall =function(type){
	
	
		var URL = getAbsoluteUrl("mortgage-app/api/voucher/filterByType"); 
		
		//$('#receiveTransaction_tbl').DataTable().destroy();
		
		$.ajax({
	     type: "GET",
	     url: URL,
	     data:{type:type},
	     // parameters
	  		success: function(respJson) {
	  			
	  		
	  		/*
	  		 jQuery.each(respJson, function(i, val) {
	  			rtTransactionApiCall(val,0);
			 });
			 
			 */
	  			updateTable(respJson);	
	  		
		  	console.log(respJson);
		  	
	  			
	  		}
		})
	
}








// select in receive transaction
$(".transHistory").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    switch(valueSelected){
    
    case "loan_take":
    	apiCall("loan_take");
    	break;
    	
    case "mortgage":
    	apiCall("mortgage");
    	break;
   
    case "loan_give":
    	apiCall("loan_give");
    	break;	
    	
    
    }
    
    reset();

});
		

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
     




var updateTable = function(allDetails)
{
	
		 let tr ="";
		 let data=[];
		 let balance=0;
		 let voc=(allDetails==null || allDetails.length==0)?[]:allDetails[0][1].id;
		 
		 var totalAna =0;
		 var totalBori =0;
		 var totalRatti=0;
		 var totalGram=0;
		 var totalBalance=0;
		 
		 $('#transactionHistory_tbl').DataTable().destroy();
		 
		 jQuery.each(allDetails, function(i, val) {
			//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
			
			 var voucherDetails = val[1];
			 var loanTransDetails = val[0];
			 
			 
			 
			 
			 var prefix =getPrefix(voucherDetails.type);
			 var formatId =prefix+" "+ voucherDetails.id;
			 
			
			 
			 
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
			
			 
			 var totalvalue = loanTransDetails.interestPaidAmount+loanTransDetails.paidAmount;
			 
			 if(voc === voucherDetails.id)
			 {
				 balance += totalvalue;
			 }else{
				 balance=totalvalue;
				 voc = voucherDetails.id;
			 }
			 
			 console.log(totalvalue);
			 
			 /**/
			 totalAna+=mortgageDetails.ana;
			 totalBori+=mortgageDetails.bori;
			 totalRatti+=mortgageDetails.ratti;
			 totalGram+=mortgageDetails.gram;
			 totalBalance+=balance;
			 
			 
			 data.push([
				 (loanTransDetails.id==null?"":loanTransDetails.id),
				 voucherDetails.customer.customerName,
				 prefix+voucherDetails.id,
				 voucherDetails.customer.customerId,
				 moment(loanTransDetails.date).format("DD/MM/YYYY"),
				mortgageDetails.bori,
				mortgageDetails.ana,
				mortgageDetails.ratti,
				mortgageDetails.gram,
				transDetails.rate==null?"0.0":transDetails.rate,
				dayCount(voucherDetails.date),
				transDetails.amount==null?"0.0":transDetails.amount,
				loanTransDetails.paidAmount==null?"0.0":loanTransDetails.paidAmount,
				loanTransDetails.interest_amount==null?"0.0":loanTransDetails.interest_amount	,	
				loanTransDetails.interestPaidAmount==null?"0.0":loanTransDetails.interestPaidAmount,
				totalvalue==null?"0.0":totalvalue,
				loanTransDetails.balance==null?"0.0":loanTransDetails.balance,
				"<input class='form-controlcol-sm-2  upd'  type='button' typeid='"+voucherDetails.type+"' updid='"+loanTransDetails.id+"'  name='update' value='update' id='rupdate' />",
  				"<input class='form-controlcol-sm-2  del' type='button' delid='"+loanTransDetails.id+"' name='delete' value='delete' id='rdelete' />",
  				"<input class='form-controlcol-sm-2  view' type='button' viewid='"+val.trans_id+"' name='view' value='View' data-toggle='modal'  data-target='#viewModal'id='rview' />"
				
			 ]); 
			
		}); 
		 transHistoryTable = $('#transactionHistory_tbl').DataTable({
		data:           data,
       deferRender:    true,
       
       autoWidth: false,
       scrollCollapse: true,
       scrollX:true,
       scroller:       true,
       columnDefs: [
           {
           	"searchable": true,
               "targets": [ 0 ],
               "visible": false,
               
           }
           ],
           
       "order": [],
		 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;


	    } });
		 
		 
		 
	$("#re_bori").val(totalBori);
	$("#re_ana").val(totalAna);
	$("#re_gram").val(Math.round(totalGram * 100) / 100);
	$("#re_ratti").val(totalRatti);
	$("#re_balance").val(totalBalance);
	
	selectSpecificTransaction();
		 
	
}





var dayCount = function(date)
{
	let old = moment(date); 
 	let today = moment(); 
 	let diff = today.diff(old,"days");		       	 
 	return diff;
	
};




$('#transactionHistory_tbl tbody').on( 'click', '.view',function()
{
	var id = transHistoryTable
	.row( $(this).parent().parent())
	.index();
	
	$(".details ul").empty();
	
	var labels = ["ID","Name","Serial No","Customer Sl.no",
		"Receive Date","Bori","Ana","Ratti","Gram",
		"Interest Rate","Day Count","Loan Amount","Paid Principal Amount"
		,"Interest Amount","Paid Interest Amount","Total Value","Balance"];
	
	var data=transHistoryTable
    .row( id )
    .data( );
	
	var d = $(".details ul");
	
	for(var i =0;i<labels.length;i++)
		{
		d.append("<li><strong>"+labels[i]+"</strong> : "+ data[i]+"</li>");
		}
	
	
	

});





	//==================================================================================================
	
	
	//Delete row data from table
	
	 $('#transactionHistory_tbl tbody').on( 'click', '#rdelete', function () {
     	var table = $('#transactionHistory_tbl').DataTable();
     	if(confirm('Are you sure !')){
     		  var currentPage = table.page();
     		  //console.log($(this).attr('delid'));
     		  
     		  var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/delete/"+parseInt($(this).attr('delid')));
     		  buildAjax(URL, "POST").then(function(respJson){
     			 
     			  alert("Deleted successfully ..")
     			  console.log(respJson);
     			  
     			  
     		  },function(reason){
		           	 console.log("error in processing your request", reason);
	           	}); 
     		  var row = table.row( $(this).parents('tr') );
               var rowNode = row.node();
               row.remove();
               table.page(currentPage).draw(false);
     	}
      
         
     });
	 
	 
	 
	 //==============================================================
	 
	 //Search
	 
	 

	 $("#r_view_report").click(function(){
	 	searchByRSlno();
	 	searchByRName();
	 	searchByRId();
	 	//searchByRAmount();
	 	
	 	var dateFrom = $("#r_datepicker_from").val();
	 	var dateTo = $("#r_datepicker_to").val();
	 	
	 	//searchByRType($(".transactionType option:selected").text());
	 	dateToAndFrom(transHistoryTable,dateFrom.dateTo);
	 });

	 function searchByRSlno(){
	 	
	 	transHistoryTable
	       .column(2)
	       .search($("#r_serial").val())
	       .draw();
	 }

	

	 function searchByRName(){
	 	
	 	transHistoryTable
	       .column(1)
	       .search($("#r_cust_name").val())
	       .draw();
	 }

	 	function searchByRId(){
	 	
	 		transHistoryTable
	       .column(3)
	       .search($("#r_cust_id").val())
	       .draw();
	 }

	 function searchByRDate(){
	 	
	 	transHistoryTable
	    .column(3)
	    .search($("#amount").val())
	    .draw();totalAna+=mortgageDetails.ana;
	 	 totalBori+=mortgageDetails.bori;
	 	 totalRatti+=mortgageDetails.ratti;
	 	 totalGram+=mortgageDetails.gram;
	 	 totalBalance+=balance;
	 }

	 //==============================search by date

	 //Date range filter
	 minDateFilter = "";
	 maxDateFilter = "";

	 var dateToAndFrom = function(table,dateFrom,dateTo)
	 {
	 	
	 	
	 	minDateFilter = new Date(dateFrom).getTime();
	 	maxDateFilter = new Date(dateTo).getTime();
	 	table.draw();


	 }



	 $.fn.dataTable.ext.search.push(
	 		  function(settings, data, dataIndex) {
	 		    var min = $("#r_datepicker_from").val();
	 		    var max = $("#r_datepicker_to").val();
	 		    var createdAt = data[4] || 0; // Our date column in the table

	 		    if (
	 		      (min == "" || max == "") ||
	 		      (moment(createdAt).isSameOrAfter(min) && moment(createdAt).isSameOrBefore(max))
	 		    ) {
	 		      return true;
	 		    }
	 		    return false;
	 		  }
	 		  );

	 
	 

	 var reset =function()
	 {
		 $("#r_cust_id").val("");
		 $("#r_cust_name").val("");
		 $("#r_serial").val("");
	 }


//====================================================================================================================
	 
	 //Update Transaction
	 $('#transactionHistory_tbl tbody').on( 'click', '#rupdate', function () {
	     	var table = $('#transactionHistory_tbl').DataTable();
	     	if(confirm('Are you sure !')){
	     		  var currentPage = table.page();
	     		  //console.log($(this).attr('delid'));  
	     		  var type_name = $(this).attr('typeid');
	 		    	var loanId =$(this).attr('updid'); 
	     		 
	     		 window.location.href=getAbsoluteUrl("mortgage-app/web/update_transaction?id="+loanId+"&type="+type_name);
	     		
	     	}
	      
	         
	     });
	 

	 
	 var selectSpecificTransaction = function()
	 {
		 if($("#s_id").val().trim()!="" && $("#s_voucher_id").val().trim()!=""){
			 
			 var id =parseInt($("#s_id").val().trim());
			var voucherId =parseInt($("#s_voucher_id").val().trim().split("/")[0]);
			searchByLoanId(id);
			
			
			
			
		 }
	 }
	 
	 
	 function searchByLoanId(id){
		 	
		 $('#transactionHistory_tbl').DataTable()
		       .column(0)
		       .search(id)
		       .draw();
		 }









