var dataTable;
var loanObj=[];
var allDetails=[];
var selectedType = "mortgage";
var autoCompleteList = [];

var lDetails=[];
var vDetails=[];

$(document).ready(function(){
	setAutoComplete(searchBySlno);
	initializeDataTable();
	$(".date").datepicker(dateSettings);
	$(".date").datepicker({
		   dateFormat: "dd/mm/yyyy"
	   });
	
	//fetchTransactions();
})


var formatDate = function(date){
	return moment(date).format("DD/MM/YYYY");
}


var initSuggestion =function(field,search)
{
		console.log("autocomplete="+autoCompleteList);
		field.autocomplete({
	        source: autoCompleteList,
	        select: function (event, ui) {
	        	var txt = ui.item.label;
	          let voucherId = parseInt(txt.split(">")[1]);
	          
	          // 	$("#serial").val(voucherId); 
	          
	          search(voucherId);
	    	
	          
	      }
	      });
}

/*
var pressEnterToSearch =function(field,searchMethod)
{
	field.on('keypress', function (e) {
        if(e.which === 13){

        	searchMethod();
        }
  });
}
*/

function setAutoComplete(search){
     	   
     	   
     	   var URL = getAbsoluteUrl("mortgage-app/api/voucher/");
     	   buildAjax(URL, "GET").then(function(respJson){
     		   jQuery.each(respJson, function(i, val) {
     			   
     				   autoCompleteList.push({value: val.id,  label: val.customer.customerName+" > "+val.id});
     			   
     		   });
     	   
     		  initSuggestion($( "#serial" ),search);

     	   }, function(reason){
            	 console.log("error in processing your request customer", reason);
          	}); 
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
	
	$('#basic-datatables').DataTable().destroy();
	
	dataTable = $('#basic-datatables').DataTable({
		data:           data,
        deferRender:    true,
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


//select in receive transaction
$(".custom").on('change', function (e) {
    
    
    selectedType =this.value;
    /*
    var URL =getAbsoluteUrl("mortgage-app/api/voucher/filterByTypeAndId");
    var voucherId = parseInt($("#serial").val());
    
    if(voucherId != null || voucherId != NaN){
    
	var params= {flag:formatIntegerValue("0"),type:"mortgage",voucherId};
	   
    switch(valueSelected){
    
    case "loan_take":
    	params.type="loan_take";
    	apiCall(URL,params,0,voucherId);
    	break;
    	
    case "mortgage":
    	params.type="mortgage";
    	apiCall(URL,params,0,voucherId);
    	break;
   
    case "loan_give":
    	params.type="loan_give";
    	apiCall(URL,params,0,voucherId);
    	break;	
    	
    
    }
    
    }*/
});



function fetchTransactions(){
    	
    	   $('#basic-datatables').DataTable().destroy();
    	   
    	   var URL =getAbsoluteUrl("mortgage-app/api/voucher/filterByType");
    	   var params= {type:"mortgage"};
    	   apiCall(URL,params,0);    	   
}


var fillFields = function(voucherDetails,loanDetails)
{
	var info = giveTransAndMortgageInfo(voucherDetails);
	var dueAmt =info.transDetails.amount-loanDetails.previousLoanAmount;
	var calInterest =calculateInterest(voucherDetails.date,moment(),info.transDetails.rate,info.transDetails.amount,dueAmt);
	
	 $("#receive_date").val(formatDate(voucherDetails.date));
	 $("#cust_name").val(voucherDetails.customer.customerName);
	 $("#cust_id").val(voucherDetails.customer.customerId);
	 $("#interest_amount").val(calInterest);
	 $("#loan_amount").val(info.transDetails.amount);
	 $("#total_amount").val(calInterest+info.transDetails.amount);
	 
	 $(".custom").val(voucherDetails.type).change();
}
    

var apiCall = function (URL,params,mode)
       {
       	
       		var total=0;
          		
          		
          		$('#basic-datatables').DataTable().destroy();
          		
          		$.ajax({
          	     type: "GET",
          	     url: URL,
          	   //data:{id:voucherId},
          	   data:params,
          	    success: function(respJson) {

          	    	updateTableAndFields(respJson,mode);
          	  			
          	  		}  
          	})
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
       
       
var updateTableAndFields =function(loanJson,mode)
{

	
	let tr ="";
	 let data=[];
	 let balance=0;
	 //let voc=(loanJson== null || loanJson.length <= 0)?[]:loanJson[0][1].id;
	 let totalAmt=0;
	 
	 console.log(loanJson);
	 
  		$('#basic-datatables').DataTable().destroy();
  		 jQuery.each(loanJson, function(i, val) {
  			//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
  			
  			var voucherDetails = val[1];
			var loanTransDetails = val[0];
			 
			 
			 var prefix =getPrefix(voucherDetails.type);
			 var formatId =prefix+" "+ voucherDetails.id;
			 
			
			 
			 //identifies btw mortgage, loan-give and loan-take
			 var info = giveTransAndMortgageInfo(voucherDetails);
			 
			 var mortgageDetails=info.mortgageDetails;
			 var transDetails=info.transDetails;
			 
			
			 
			 var totalvalue = loanTransDetails.interestPaidAmount+loanTransDetails.paidAmount;
			 //totalAmt = transDetails.amount + loanTransDetails.interest_amount;
			 
			
			 
			 console.log(totalvalue);
  			 
  			 data.push([voucherDetails.customer.customerName,
  				voucherDetails.customer.customerId,
  				transDetails.rate,
  				transDetails.amount,
  				totalvalue,
  				 (voucherDetails.date==null?"":formatDate(voucherDetails.date)),
  				loanTransDetails.id,
  				formatId,
  				dayCount(voucherDetails.date),
  				(loanTransDetails.interestPaidAmount==null?"0.0":loanTransDetails.interestPaidAmount),
  				(loanTransDetails.paidAmount==null?"0.0":loanTransDetails.paidAmount),
  				totalvalue,
  				loanTransDetails.balance,
  				"<input class='form-controlcol-sm-2  upd'  type='button' typeid='"+voucherDetails.type+"' updid='"+loanTransDetails.id+"'  name='update' value='update' id='rupdate' />",
  				"<input class='form-controlcol-sm-2  del' type='button' voucherId='"+voucherDetails.id+"' delid='"+loanTransDetails.id+"' paidAmt='"+loanTransDetails.paidAmount+"' paidInterestAmt='"+loanTransDetails.interestPaidAmount+"'  name='delete' value='delete' id='rdelete' />"
  			 ]); 
  			 
  		}); 
  		 
  		 	if(loanJson != null && loanJson.length >0){
  		 		fillFields(loanJson[0][1],loanJson[0][0]);
  		 	}
  			
  		 
  		 
  		dataTable = $('#basic-datatables').DataTable({
    		data:           data,
            deferRender:    true,
            //scrollX:        true,
            scroller:       true,
            columnDefs: [
                
                {
                	"searchable": true,
                    "targets": [ 2 ],
                    "visible": false,
                    
                },
                {
                	"searchable": true,
                    "targets": [ 3 ],
                    "visible": false
                },
                {
                	"searchable": true,
                    "targets": [ 4 ],
                    "visible": false
                }
                
            ],
            "order": [],
			 "footerCallback": function ( row, data, start, end, display ) {
    var api = this.api(), data;

   
  	    } });
 
  		
}

//=============================================================

var searchBySlno=function(voucherId){
	
	/*
	dataTable
        .column(7)
        .search($("#serial").val())
        .draw();*/
	
	
	var id = voucherId != null ? voucherId :$("#serial").val();
	
	/*
	var URL =getAbsoluteUrl("mortgage-app/api/voucher/filterByTypeAndId");
	var params= {type:selectedType,voucherId:id};
	apiCall(URL,params,1);
	*/
	
	var URL =getAbsoluteUrl("mortgage-app/api/voucher/filterById");
	var params= {id};
	apiCall(URL,params,1);
	
	
	//fillFields(vDetails,lDetails,0);

}

/*
function searchByDate(){
	
	dataTable
        .column(5)
        .search($("#receive_date").val())
        .draw();
}



function searchByName(){
	
	dataTable
        .column(0)
        .search($("#cust_name").val())
        .draw();
}

function searchByCId(){
	
	dataTable
        .column(1)
        .search($("#cust_id").val())
        .draw();
}

function searchByInterest(){
	
	dataTable
    .column(2)
    .search($("#interest_rate").val())
    .draw();
}

function searchByAmount(){
	
	dataTable
    .column(3)
    .search($("#loan_amount").val())
    .draw();
}

function searchByTotalAmount(){
	
	dataTable
    .column(4)
    .search($("#total_amount").val())
    .draw();
}
*/

$("#view_report").click(function(){
	var slno = parseInt($("#serial").val().trim()===""?"0":$("#serial").val());

	if(slno != NaN){
	searchBySlno(slno);
	}
	/*
	searchByDate();
	//searchByCId();
	searchByInterest();
	searchByAmount();
	searchByTotalAmount();
	searchByName();
	*/
})


//Update Transaction
	 $('#basic-datatables tbody').on( 'click', '#rupdate', function () {
	     	var table = $('#basic-datatables').DataTable();
	     	if(confirm('Are you sure !')){
	     		  var currentPage = table.page();
	     		  //console.log($(this).attr('delid'));
	     		  var type_name = $(this).attr('typeid');
	 		    	var loanId =$(this).attr('updid'); 
	     		 
	     		 window.location.href=getAbsoluteUrl("mortgage-app/web/update_transaction?id="+loanId+"&type="+type_name);
	     		
	     	}
	      
	         
	     });


$('#basic-datatables tbody').on( 'click', '#rdelete', function () {
 	var table = $('#basic-datatables').DataTable();
 	if(confirm('Are you sure !')){
 		
 		var currentPage = table.page();
 		
 		  var id =$(this).attr('delid'); 
 		  var paidAmt = parseInt($(this).attr('paidAmt'));
 		  var paidInterestAmt = parseInt($(this).attr('paidInterestAmt'));
 		  var voucherId =parseInt($(this).attr('voucherId'));
 		  
 		  var params ={id,paidAmt,paidInterestAmt,voucherId};
 		  
 		  var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/delete");
 		  
 		  
 		 $.ajax({
      	     type: "POST",
      	     url: URL,

      	   data:params,
      	    success: function(respJson) {

      	    	 alert("Deleted successfully ..")
    			  console.log(respJson);
      	  			
      	  		}  
      	})

 		  var row = table.row( $(this).parents('tr') );
          var rowNode = row.node();
          row.remove();
          table.page(currentPage).draw(false);
 	}
  
     
 });


$("#clear").click(function(){
	
	$("#cust_id").val("");
	$("#cust_name").val("");
	$("#receive_date").val("");
	$("#total_amount").val("");
	$("#loan_amount").val("");
	$("#serial").val("");
	$("#interest_amount").val("");
});




//==============================================================
//in receive transaction

var recTable=null;

$("#receive_modal").click(function(){
	loanObj=[];
	
	initSuggestion($("#r_serial"),searchByRSlno);
	
	var URL = getAbsoluteUrl("/mortgage-app/api/voucher/getRecentTransactions"); 
	var params ={flag:formatIntegerValue("0"),type:"mortgage"};
	rtApiCall(URL,params,0);
});


var rtApiCall =function(URL,params,mode){
	
	
		//$('#receiveTransaction_tbl').DataTable().destroy();
		
		$.ajax({
	     type: "GET",
	     url: URL,
	     data:params,
	     // parameters
	  		success: function(respJson) {
	  			
	  		
	  		/*
	  		 jQuery.each(respJson, function(i, val) {
	  			rtTransactionApiCall(val,0);
			 });
			 
			 */
	  			rt_updateTable(respJson,mode);	
	  		
		  	console.log(respJson);
		  	
	  			
	  		}
		})
	
}


// select in receive transaction
$(".transactionType").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    
    var URL = getAbsoluteUrl("/mortgage-app/api/voucher/getRecentTransactions"); 
	var params ={flag:formatIntegerValue("0"),type:"mortgage"};
  
    switch(valueSelected){
    
    case "loan_take":
    	params.type ="loan_take";
    	rtApiCall(URL,params,0);
    	break;
    	
    case "mortgage":
    	rtApiCall(URL,params,0);
    	break;
   
    case "loan_give":
    	params.type ="loan_give";
    	rtApiCall(URL,params,0);
    	break;	
    	
    
    }

});
		


var rt_updateTable = function(allDetails,mode)
{
	
		 let tr ="";
		 let data=[];
		 let balance=0;
		 
		 console.log(allDetails);
		 
		 //let voc=(allDetails==null || allDetails.length ===0)?[]:allDetails[0][1].id;
		 
		 var totalAna =0;
		 var totalBori =0;
		 var totalRatti=0;
		 var totalGram=0;
		 var totalBalance=0;
		 
		 $('#receiveTransaction_tbl').DataTable().destroy();
		 
		 jQuery.each(allDetails, function(i, val) {
			//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
			
			 var voucherDetails = val[1];
			 var loanTransDetails = val[0];
			 
			 if(mode === 1)
				 fillreceiveFields(voucherDetails,loanTransDetails);
			 
			 if(voucherDetails!=null &&  loanTransDetails !=null ){
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
			
			 
			 var totalvalue = transDetails.amount+loanTransDetails.interest_amount;
			 console.log(totalvalue);
			 
			 /**/
			 totalAna+=mortgageDetails.ana;
			 totalBori+=mortgageDetails.bori;
			 totalRatti+=mortgageDetails.ratti;
			 totalGram+=mortgageDetails.gram;
			 totalBalance+=loanTransDetails.balance;
			 
			 
			 data.push([
				 (loanTransDetails.id==null?"":loanTransDetails.id),
				 voucherDetails.customer.customerName,
				 prefix+voucherDetails.id,
				 voucherDetails.customer.customerId,
				 formatDate(loanTransDetails.date),
				mortgageDetails.bori,
				mortgageDetails.ana,
				mortgageDetails.ratti,
				mortgageDetails.gram,
				transDetails.rate==null?"0.0":transDetails.rate,
				dayCount(voucherDetails.date),
				transDetails.amount==null?"0.0":transDetails.amount,
				loanTransDetails.previousLoanAmount==null?"0.0":loanTransDetails.previousLoanAmount,
				loanTransDetails.interest_amount==null?"0.0":loanTransDetails.interest_amount	,	
				loanTransDetails.interestPreviousLoanAmount==null?"0.0":loanTransDetails.interestPreviousLoanAmount,
				totalvalue==null?"0.0":totalvalue,
				loanTransDetails.balance
				
			 ]); 
			 
		 }
			
		});
		 
		 
		 
		 
		 
		 recTable = $('#receiveTransaction_tbl').DataTable({
		data:           data,
       deferRender:    true,
       
       autoWidth: false,
       //scrollCollapse: true,
       scroller:       true,
       columnDefs: [
           {
           	"searchable": true,
               "targets": [ 0 ],
               "visible": false,
               
           }
           ],
           
       "order": [ 2, "desc" ],
		 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;


	    } });
		 
		 
		 
	$("#re_bori").val(totalBori);
	$("#re_ana").val(totalAna);
	$("#re_gram").val(Math.round(totalGram * 100) / 100);
	$("#re_ratti").val(totalRatti);
	$("#re_balance").val(totalBalance);
		 
	
}





/*
$('#receiveTransaction_tbl tbody').on( 'click', '.view',function()
{
	var id = recTable
	.row( $(this).parent().parent())
	.index();
	
	$(".details ul").empty();
	
	var labels = ["ID","Name","Serial No","Customer Sl.no",
		"Receive Date","Bori","Ana","Ratti","Gram",
		"Interest Rate","Day Count","Loan Amount","Paid Principal Amount"
		,"Interest Amount","Paid Interest Amount","Total Value","Balance"];
	
	var data=recTable
    .row( id )
    .data( );
	
	var d = $(".details ul");
	
	for(var i =0;i<labels.length;i++)
		{
		d.append("<li><strong>"+labels[i]+"</strong> : "+ data[i]+"</li>");
		}
	
	
	

});*/





var fillreceiveFields = function(voucherDetails,loanDetails)
{
	var info = giveTransAndMortgageInfo(voucherDetails);
	var dueAmt =info.transDetails.amount-loanDetails.previousLoanAmount;
	var calInterest =calculateInterest(voucherDetails.date,moment(),info.transDetails.rate,info.transDetails.amount,dueAmt);
	
	 $("#r_receive_date").val(formatDate(voucherDetails.date));
	 $("#r_cust_name").val(voucherDetails.customer.customerName);
	 $("#r_cust_id").val(voucherDetails.customer.customerId);
	 $("#r_interest_amount").val(calInterest);
	 $("#r_loan_amount").val(info.transDetails.amount);
	 $("#r_total_amount").val(calInterest+info.transDetails.amount);
}


//search


$("#r_view_report").click(function(){
	
	
	searchByRFields();
	//var id =parseInt($("#r_serial").val());
	
	//var URL = getAbsoluteUrl("/mortgage-app/api/voucher/getSpecificTrans"); 
	//var params ={flag:formatIntegerValue("0"),id};
	//rtApiCall(URL,params,1);
	
	//searchByRAmount();
	
	//fillreceiveFields()
	
	var dateFrom = $("#r_datepicker_from").val();
	var dateTo = $("#r_datepicker_to").val();
	
	//searchByRType($(".transactionType option:selected").text());
	dateToAndFrom(recTable,dateFrom.dateTo);
});

function searchByRSlno(){
	
	
	var term = $("#r_serial").val();
    regex = '\\b' + term + '\\b';

	recTable.columns(2).search(regex, true, false).draw();
	
}

function searchByRType(type){
	
	recTable
      .column(0)
      .search(type)
      .draw();
}

function searchByRName(){
	
	var term = $("#r_cust_name").val();
    regex = '\\b' + term + '\\b';

    recTable.columns(1).search(regex, true, false).draw();

	
}

	function searchByRId(){
		
		var term = $("#r_cust_id").val();
	    regex = '\\b' + term + '\\b';

	    recTable.columns(3).search(regex, true, false).draw();
	
		
}

function searchByRDate(){
	
	recTable
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

var searchByRFields =function()
{
	searchByRSlno();
	searchByRName();
	searchByRId();
	
}


$("#clear_rec").click(function(){
	
	$("#r_cust_id").val("");
	$("#r_cust_name").val("");
	$("#r_receive_date").val("");
	$("#r_datepicker_from").val("");
	$("#r_datepicker_to").val("");
	$("#r_serial").val("");
	searchByRFields();
});




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






//===========================================================================================
//delivery transaction

var deliveryTable=null;

$("#delivery_modal").click(function(){
	loanObj=[];
	initSuggestion($("#d_serial"),searchByDSlno);
	var URL =getAbsoluteUrl("mortgage-app/api/voucher/filter"); 
	var params ={flag:formatIntegerValue("1"),type:"mortgage"}
	dtApiCall(URL,params,0);
});



var dtApiCall =function(URL,params,mode){
	
	
	 $('#deliveryTransaction_tbl').DataTable().destroy();
	//$('#receiveTransaction_tbl').DataTable().destroy();
	
	$.ajax({
	     type: "GET",
	     url: URL,
	     data:params,
	     // parameters
	  		success: function(respJson) {
	  			

	  			dt_updateTable(respJson,mode);	
	  			
		  	
	  			
	  		}
		})

}
	
var deliveredType = "mortgage";
$(".transactionType1").on('change', function (e) {
    
	deliveredType = this.value;
    console.log(deliveredType);
    
    
    var URL =getAbsoluteUrl("mortgage-app/api/voucher/filter");
    var params ={flag:formatIntegerValue("1"),type:"mortgage"}
    switch(deliveredType){
    
    case "loan_take":
    	params.type="loan_take";
    	dtApiCall(URL,params,0);
    	break;
    	
    case "mortgage":
    	dtApiCall(URL,params,0);
    	break;
   
    case "loan_give":
    	params.type="loan_give";
    	dtApiCall(URL,params,0);
    	break;	
    	
    
    }

});



	

var dt_updateTable = function(allDetails,mode)
{


	 let tr ="";
	 let data=[];
	 let balance=0;
	 //let voc=allDetails[0][1].id;
	 
	 
	 var totalAna =0;
	 var totalBori =0;
	 var totalRatti=0;
	 var totalGram=0;
	 var totalBalance=0;
	 
	 $('#deliveryTransaction_tbl').DataTable().destroy();
	 
	 jQuery.each(allDetails, function(i, val) {
		//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
		
		 var voucherDetails = val[1];
		 var loanTransDetails = val[0];
		 

			if(mode === 1)
				filldeliveryFields(voucherDetails,loanTransDetails);

			
		 
		 
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
		
		 
		 var totalvalue = transDetails.amount+loanTransDetails.interest_amount;
		 
		
		 
		 console.log(totalvalue);
		 
		 totalAna+=mortgageDetails.ana;
		 totalBori+=mortgageDetails.bori;
		 totalRatti+=mortgageDetails.ratti;
		 totalGram+=mortgageDetails.gram;
		 totalBalance+=loanTransDetails.balance;
		 
		 
		 
		 
		 data.push([
			 (loanTransDetails.id==null?"":loanTransDetails.id),
			 voucherDetails.customer.customerName,
			 prefix+voucherDetails.id,
			 voucherDetails.customer.customerId,
			 formatDate(voucherDetails.date),
			mortgageDetails.bori,
			mortgageDetails.ana,
			mortgageDetails.ratti,
			mortgageDetails.gram,
			transDetails.rate==null?"0.0":transDetails.rate,
			formatDate(loanTransDetails.date),
			dayCount(voucherDetails.date),
			transDetails.amount==null?"0.0":transDetails.amount,
			loanTransDetails.previousLoanAmount==null?"0.0":loanTransDetails.previousLoanAmount,
			loanTransDetails.interest_amount==null?"0.0":loanTransDetails.interest_amount	,	
			loanTransDetails.interestPreviousLoanAmount==null?"0.0":loanTransDetails.interestPreviousLoanAmount,
			totalvalue==null?"0.0":totalvalue,
			loanTransDetails.balance,
			"<input class='form-controlcol-sm-2  upd'  type='button' typeid='"+voucherDetails.type+"' updid='"+loanTransDetails.id+"' voucherId='"+voucherDetails.id+"'  name='update' value='update' id='dupdate' />",
			"<input class='form-controlcol-sm-2  del' type='button' voucherId='"+voucherDetails.id+"' delid='"+loanTransDetails.id+"' paidAmt='"+loanTransDetails.paidAmount+"' paidInterestAmt='"+loanTransDetails.interestPaidAmount+"'  name='delete' value='delete' id='ddelete' />",
			"<input class='form-controlcol-sm-2  view' type='button' viewid='"+val.trans_id+"' name='view' value='View' data-toggle='modal'  data-target='#viewModal'id='dview' />"
			]);
		 
		 
		 console.log(data);
		
	}); 
	 
	 deliveryTable = $('#deliveryTransaction_tbl').DataTable({
	data:           data,
   deferRender:    true,
   scrollX:        true,
   scrollCollapse: true,
   //autoWidth: false,
   //scroller:       true,
   
   columnDefs: [
       {
       	"searchable": true,
           "targets": [ 0 ],
           "visible": false,
           
       }],
   "order": [],
	 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;


    } });
	
	 $("#dt_bori").val(totalBori);
		$("#dt_ana").val(totalAna);
		$("#dt_gram").val(totalGram);
		$("#dt_ratti").val(totalRatti);
		$("#dt_balance").val(totalBalance);
		
}


var dayCount = function(date)
{
let old = moment(date); 
	let today = moment(); 
	let diff = today.diff(old,"days");		       	 
	return diff;

};


var filldeliveryFields = function(voucherDetails,loanDetails)
{
	var info = giveTransAndMortgageInfo(voucherDetails);
	var dueAmt =info.transDetails.amount-loanDetails.previousLoanAmount;
	var calInterest =calculateInterest(voucherDetails.date,moment(),info.transDetails.rate,info.transDetails.amount,dueAmt);
	
	 $("#d_receive_date").val(voucherDetails.date);
	 $("#d_cust_name").val(voucherDetails.customer.customerName);
	 $("#d_cust_id").val(voucherDetails.customer.customerId);
	 $("#d_interest_amount").val(calInterest);
	 $("#d_loan_amount").val(info.transDetails.amount);
	 $("#d_total_amount").val(calInterest+info.transDetails.amount);
}


var searchByDFields =function()
{
	searchByDSlno();
	searchByDName();
	searchByDId();
	
}



$("#d_view_report").click(function(){
	
	searchByDFields();
	/*var URL =getAbsoluteUrl("mortgage-app/api/voucher/loan/flag");
	var id =parseInt($("#d_serial").val());
	var params = {flag:1,voucherId:id}
	dtApiCall(URL,params,1);
	var dateFrom = $("#d_datepicker_from").val();
	var dateTo = $("#d_datepicker_to").val();*/
	
	//searchByDType($(".transactionType1 option:selected").text());
	
	console.log(dateFrom);
	console.log(dateTo);
	//dateToAndFrom(deliveryTable,dateFrom.dateTo);
});

function searchByDSlno(){
	
	var term = $("#d_serial").val();
    regex = '\\b' + term + '\\b';

    deliveryTable.columns(2).search(regex, true, false).draw();
	
}


/*
function searchByDType(type){
	
	deliveryTable
      .column(0)
      .search(type)
      .draw();
}

*/
function searchByDName(){
	
	var term = $("#d_cust_name").val();
    regex = '\\b' + term + '\\b';

    deliveryTable.columns(1).search(regex, true, false).draw();
	
}

	function searchByDId(){
		
		
		var term = $("#d_cust_id").val();
	    regex = '\\b' + term + '\\b';

	    deliveryTable.columns(3).search(regex, true, false).draw();
	
		
}
	
	
	$('#deliveryTransaction_tbl tbody').on( 'click', '.view',function()
			{
				var id = deliveryTable
				.row( $(this).parent().parent())
				.index();
				
				$(".details ul").empty();
				
				var labels = ["ID","Name","Serial No","Customer Sl.no",
					"Receive Date","Bori","Ana","Ratti","Gram",
					"Interest Rate","Delivery Date","Day Count","Loan Amount","Paid Principal Amount"
					,"Interest Amount","Paid Interest Amount","Total Value","Balance"];
				
				var data=deliveryTable
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
	/*
	 $('#receiveTransaction_tbl tbody').on( 'click', '#rdelete', function () {
     	var table = $('#receiveTransaction_tbl').DataTable();
     	if(confirm('Are you sure !')){
     		  var currentPage = table.page();
     		  //console.log($(this).attr('delid'));
     		  
     		  var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/delete/"+parseInt($(this).attr('delid')));
     		  buildAjax(URL, "POST").then(function(respJson){
     		  },function(reason){
		           	 console.log("error in processing your request", reason);
	           	}); 
     		  var row = table.row( $(this).parents('tr') );
               var rowNode = row.node();
               row.remove();
               table.page(currentPage).draw(false);
     	}
      
         
     });*/
	 
	 
	 $('#deliveryTransaction_tbl tbody').on( 'click', '#ddelete', function () {
	     	var table = $('#deliveryTransaction_tbl').DataTable();
	     	if(confirm('Are you sure !')){
	     		var currentPage = table.page();
	     		
	   		  
	   		  
	   		  var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/delete/"+parseInt($(this).attr('delid')));
	   		  
	   		  
	   		 $.ajax({
	        	     type: "POST",
	        	     url: URL,
	        	    success: function(respJson) {

	        	    	 alert("Deleted successfully ..")
	        	    	 console.log(respJson);
	        	  			
	        	  		}  
	        	})

	   		  var row = table.row( $(this).parents('tr') );
	            var rowNode = row.node();
	            row.remove();
	            table.page(currentPage).draw(false);
	     	}
	      
	         
	     });
	 
	 
	 
	 //=========================================================================


	 //Update
	 /*
	 $('#receiveTransaction_tbl tbody').on( 'click', '#rupdate', function () {
	     	
	     	if(confirm('Are you sure !')){
	     		
	     		
	     		var voucherId = $(this).attr('vId');
	 		    var loanId =$(this).attr('updid'); 
	     			
	     		  
	     		 window.location.href=getAbsoluteUrl("mortgage-app/web/specific_transaction?id="+loanId+"&voucherId="+voucherId);
	     		  
	     	}
	      
	         
	     });*/
	 
	 
	 $("#clear_del").click(function(){
			
			$("#d_cust_id").val("");
			$("#d_cust_name").val("");
			$("#d_receive_date").val("");
			$("#d_datepicker_from").val("");
			$("#d_datepicker_to").val("");
			$("#d_serial").val("");
			
			searchByDFields();
		});

	 
	 
	 $('#deliveryTransaction_tbl tbody').on( 'click', '#dupdate', function () {
	     	
	     	if(confirm('Are you sure !')){
	     		  
	     		
	     		var type_name = $(this).attr('typeid');
	 		    var loanId =$(this).attr('updid'); 
	     			
	     		 window.location.href=getAbsoluteUrl("mortgage-app/web/update_transaction?id="+loanId+"&type="+type_name);
	     		  
	     	}
	      
	         
	     });
	 
	 
	 
	 
	//CALCULATE INTEREST 
	 var calculateInterest = function(receiveDate,transDate,rate,totalAmount,dueAmt)
	 {
	 	
	 	     let old = moment(receiveDate,"DD/MM/YYYY"); 
	 	     let today = moment(transDate, "DD/MM/YYYY"); 
	 	     let diff =  Math.abs(today.diff(old,"days"));
	 	     
	 	     
	 	     let totalInterestAmnt = (rate*totalAmount)/100;
	 	
	 	
	 	     if((diff-31)>0){
	 	    	let noOfDays = (diff-31);
	 	        totalInterestAmnt += (rate*dueAmt*noOfDays)/100;	        
	 	     }
	 	
	 	
	 	return totalInterestAmnt;
	 }










