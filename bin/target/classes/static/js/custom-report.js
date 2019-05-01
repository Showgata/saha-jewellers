var dataTable;
var loanObj=[];
var allDetails=[];

$(document).ready(function(){
	setAutoComplete();
	initializeDataTable();
	$(".date").datepicker(dateSettings);
	$(".date").datepicker({
		   dateFormat: "dd/mm/yyyy"
	   });
})



function setAutoComplete(){
     	   
     	   var autoCompleteList = [];
     	   var URL = getAbsoluteUrl("/mortgage-app/api/voucher/");
     	   buildAjax(URL, "GET").then(function(respJson){
     		   jQuery.each(respJson, function(i, val) {
     			   
     				   autoCompleteList.push({value: val.id,  label: val.customer.customerName+" > "+val.id});
     			   
     		   });
     	   
     		 $( "#serial" ).autocomplete({
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
	
	$('#basic-datatables').DataTable().destroy();
	
	dataTable = $('#basic-datatables').DataTable({
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



function fetchTransactions(voucherId){
    	
    	   $('#basic-datatables').DataTable().destroy();
    	   
    	   apiCall(voucherId);
    	   	
    	   
}
    

var apiCall = function (voucherId)
       {
       	
       		var total=0;
          		var URL = getAbsoluteUrl("mortgage-app/api/voucher/"+voucherId);
          		
          		$('#basic-datatables').DataTable().destroy();
          		
          		$.ajax({
          	     type: "GET",
          	     url: URL,
          	    success: function(respJson) {

          	  			 loanTransactionApiCall(respJson);

          	  			
          	  		}  
          	})
}


var loanTransactionApiCall = function(val){
       	
			if(val.type != "capital" || val.type != "expense"){
				
				
				var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/"+val.id);
				
	       		
	       		
	       		buildAjax(URL, "GET").then(function(loanJson){
	       			
	       			var balance =0;
	       			
		  			
		  			jQuery.each(loanJson, function(i, value) {
		  				
		  				var f = value.interestPaidAmount+value.paidAmount;
		  				 balance += f;
		  				
		  				
		  				var copy = JSON.parse(JSON.stringify(val));
		  				copy["loanTransaction"]=value;
		  				copy["balance"]=f;
		  				
		  				allDetails.push(copy);
					 });
		  			
		  			
		  			console.log("Executed ?");	
	      	  		console.log(allDetails);
		  			
		  			updateTableAndFields(allDetails);
	       			
	       			//-------------------------------//
	       				//----------	---------//
	       			//-------------------------------//
	       			
	       			
	       		});
				
				
				
				
				
			}
	
       		
       	     
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
       
       
var updateTableAndFields =function(loanJson)
{

	
  		 let tr ="";
  		 var balance =0;
  		 let data=[];
  		$('#basic-datatables').DataTable().destroy();
  		 jQuery.each(loanJson, function(i, val) {
  			//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
  			
  			 
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
  			 
			 console.log(val.customer.customerName);
  			 
  			 
  			 var debit = val.loanTransaction.interestPaidAmount+val.loanTransaction.paidAmount;
  			 balance += debit;
  			 
  			 var totalAmount =  val.loanTransaction.interest_amount+val.loanTransaction.transaction_amount;
  			 
  			 var prefix =getPrefix(val.type);
  			 var formatId =prefix+" "+val.id;
  			 
  			 data.push([val.customer.customerName,
  				val.customer.customerId,
  				transDetails.rate,
  				transDetails.amount,
  				totalAmount,
  				 (val.date==null?"":val.date),
  				 val.loanTransaction.id,
  				formatId,
  				dayCount(val.date),
  				(val.loanTransaction.interestPaidAmount==null?"0.0":val.loanTransaction.interestPaidAmount),
  				(val.loanTransaction.paidAmount==null?"0.0":val.loanTransaction.paidAmount),
  				debit,
  				val.balance
  				
  			 ]); 
  			 
  			 
  			 
  			 $("#receive_date").val(val.date);
  	  		 $("#cust_name").val(val.customer.customerName);
  	  		 $("#cust_id").val(val.customer.customerId);
  	  		 $("#interest_rate").val(transDetails.rate);
  	  		 $("#loan_amount").val(transDetails.amount);
  	  		 $("#total_amount").val(totalAmount);
  			
  		}); 
  		 
  		 
  		
  		 
  		 
  		dataTable = $('#basic-datatables').DataTable({
    		data:           data,
            deferRender:    true,
            scrollY:        200,
            scrollCollapse: true,
            scroller:       true,
            columnDefs: [
                {
                	"searchable": true,
                    "targets": [ 0 ],
                    "visible": false,
                    
                },
                {
                	"searchable": true,
                    "targets": [ 1 ],
                    "visible": false
                },
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

function searchBySlno(id){
	
	//dataTable
        //.column(7)
        //.search($("#serial").val())
        //.draw();
	
	fetchTransactions(id);
	
	
	
}

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

$("#view_report").click(function(){
	var slno = parseInt($("#serial").val());
	
	searchBySlno(slno);
	//searchByDate();
	//searchByCId();
	//searchByInterest();
	//searchByAmount();
	//searchByTotalAmount();
	//searchByName();
})






//==============================================================
//in receive transaction

var recTable=null;

$("#receive_modal").click(function(){
	rtApiCall();
});


var rtApiCall =function(){
	
		loanObj=[];
		var URL = getAbsoluteUrl("mortgage-app/api/voucher/");
		
		//$('#receiveTransaction_tbl').DataTable().destroy();
		
		$.ajax({
	     type: "GET",
	     url: URL,
	     // parameters
	  		success: function(respJson) {
	  			
	  		
	  		
	  		 jQuery.each(respJson, function(i, val) {
	  			rtTransactionApiCall(val,0);
			 });
	  			
	  		console.log("Voucher");	
		  	console.log(loanObj);
		  	
	  			
	  		}
		})
	
}
		
		
var rtTransactionApiCall = function(val,mode)
{
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/flag");
	$.ajax({
	     type: "GET",
	     url: URL,
	    data:{flag:mode,voucherId:val.id},
	  		success: function(respJson) {
	  			var balance =0;
	  			
	  			
	  			jQuery.each(respJson, function(i, value) {
	  				
	  				var f = value.interestPaidAmount+value.paidAmount;
	  				 balance += f;
	  				
	  				
	  				var copy = JSON.parse(JSON.stringify(val));
	  				copy["loanTransaction"]=value;
	  				copy["trans_id"]=value.id;
	  				copy["balance"]=f;
	  				
		  			loanObj.push(copy);
				 });
	  			
	  			rt_updateTable(loanObj);
	  			
	  		}
		});
	
}


var rt_updateTable = function(allDetails)
{
	
		let tr ="";
		
		var totalBori =0;
		var totalRatti=0;
		var totalGram=0;
		var totalAna=0;
		var totalBalance=0;
		
		 let data=[];
		 
		 $('#receiveTransaction_tbl').DataTable().destroy();
		 
		 jQuery.each(allDetails, function(i, val) {
			//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
			
			 
			 
			 
			 var prefix =getPrefix(val.type);
			 var formatId =prefix+" "+val.voucherId;
			 
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
			
			 
			 totalBori +=  mortgageDetails.bori;
			 totalRatti +=mortgageDetails.ratti;
			 totalGram +=mortgageDetails.gram;
			 totalAna +=mortgageDetails.ana;
			 totalBalance +=val.balance;
			 
			 var totalvalue = val.loanTransaction.interestPaidAmount+val.loanTransaction.paidAmount;
			 console.log("totalValue="+totalvalue);
			 
			 data.push([(val.loanTransaction.id==null?"":val.loanTransaction.id),
				 val.customer.customerName,
				 prefix+val.id,
				 val.customer.customerId,
				 val.loanTransaction.date,
				mortgageDetails.bori,
				mortgageDetails.ana,
				mortgageDetails.ratti,
				mortgageDetails.gram,
				transDetails.rate==null?"0.0":transDetails.rate,
				dayCount(val.date),
				transDetails.amount==null?"0.0":transDetails.amount,
				val.loanTransaction.paidAmount==null?"0.0":val.loanTransaction.paidAmount,
				val.loanTransaction.interest_amount==null?"0.0":val.loanTransaction.interest_amount	,	
				val.loanTransaction.interestPaidAmount==null?"0.0":val.loanTransaction.interestPaidAmount,
				totalvalue==null?"0.0":totalvalue,
				val.balance==null?"0.0":val.balance,
				"<input class='form-controlcol-sm-2  upd'  type='button' updid='"+val.id+"' name='update' value='update' id='rupdate' />",
  				"<input class='form-controlcol-sm-2  del' type='button' delid='"+val.id+"' name='delete' value='delete' id='rdelete' />",
  				"<input class='form-controlcol-sm-2  view' type='button' viewid='"+val.trans_id+"' name='view' value='View' data-toggle='modal'  data-target='#viewModal'id='rview' />"
				
			 ]); 
			
		});
		 
		 
		 
		 $("#re_bori").val(totalBori);
		 $("#re_ana").val(totalAna);
		 $("#re_ratti").val(totalRatti);
		 $("#re_gram").val(totalGram);
		 $("#re_balance").val(totalBalance);
		 
		 
		 
		 
		 recTable = $('#receiveTransaction_tbl').DataTable({
		data:           data,
       deferRender:    true,
       scrollY:        200,
       scrollCollapse: true,
       scroller:       true,
       "order": [],
		 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;


	    } });
		
	
}


var dayCount = function(date)
{
	let old = moment(date,"DD/MM/YYYY"); 
 	let today = moment(); 
 	let diff = today.diff(old,"days");		       	 
 	return diff;
	
};




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
	
	
	

});


//search


$("#r_view_report").click(function(){
	searchByRSlno();
	searchByRName();
	searchByRId();
	searchByRAmount();
	
	var dateFrom = $("#r_datepicker_from").val();
	var dateTo = $("#r_datepicker_to").val();
	
	console.log(dateFrom);
	console.log(dateTo);
	dateToAndFrom(recTable,dateFrom.dateTo);
});

function searchByRSlno(){
	
	recTable
      .column(2)
      .search($("#r_serial").val())
      .draw();
}

function searchByRName(){
	
	recTable
      .column(1)
      .search($("#r_cust_name").val())
      .draw();
}

	function searchByRId(){
	
		recTable
      .column(3)
      .search($("#r_cust_id").val())
      .draw();
}

function searchByRDate(){
	
	recTable
   .column(3)
   .search($("#amount").val())
   .draw();
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





















//===========================================================================================
//delivery transaction

var deliveryTable=null;

$("#delivery_modal").click(function(){
	dtApiCall();
});



var dtApiCall =function(){
	
	loanObj=[];
	var URL = getAbsoluteUrl("mortgage-app/api/voucher/");
	
	//$('#receiveTransaction_tbl').DataTable().destroy();
	
	$.ajax({
     type: "GET",
     url: URL,
     // parameters
  		success: function(respJson) {
  			
  		
  		
  		 jQuery.each(respJson, function(i, val) {
  			dtTransactionApiCall(val,1);
  			// mode -1 means delivery transaction
  			
		 });
  			
	  	
  			
  		}
	})

}
	
	
var dtTransactionApiCall = function(val,mode)
{
var URL = getAbsoluteUrl("mortgage-app/api/voucher/loan/flag");
$.ajax({
     type: "GET",
     url: URL,
    data:{flag:mode,voucherId:val.id},
  		success: function(respJson) {
  			var balance =0;
  			
  			
  			jQuery.each(respJson, function(i, value) {
  				
  				var f = value.interestPaidAmount+value.paidAmount;
  				 balance += f;
  				
  				
  				var copy = JSON.parse(JSON.stringify(val));
  				copy["loanTransaction"]=value;
  				copy["trans_id"]=value.id;
  				copy["balance"]=f;
  				
	  			loanObj.push(copy);
			 });
  			
  			dt_updateTable(loanObj);
  			
  		}
	});

}


var dt_updateTable = function(allDetails)
{

	let tr ="";
	
	var totalBori =0;
	var totalRatti=0;
	var totalGram=0;
	var totalAna=0;
	var totalBalance=0;
	 
	 let data=[];
	 
	 $('#deliveryTransaction_tbl').DataTable().destroy();
	 
	 jQuery.each(allDetails, function(i, val) {
		//autoCompleteList.push({value: val.customer.customerName,  label: val.customer.customerName+" > "+val.customer.customerId});
		
		 
		 
		 
		 var prefix =getPrefix(val.type);
		 var formatId =prefix+" "+val.voucherId;
		 
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
		 
		 
		 totalBori +=  mortgageDetails.bori;
		 totalRatti +=mortgageDetails.ratti;
		 totalGram +=mortgageDetails.gram;
		 totalAna +=mortgageDetails.ana;
		 totalBalance +=val.balance;
		
		 
		 var totalvalue = val.loanTransaction.interestPaidAmount+val.loanTransaction.paidAmount;
		 console.log("totalValue2="+totalvalue);
		 data.push([(val.loanTransaction.id==null?"":val.loanTransaction.id),
			 val.customer.customerName,
			 prefix+val.id,
			 val.customer.customerId,
			 val.loanTransaction.date,
			mortgageDetails.bori,
			mortgageDetails.ana,
			mortgageDetails.ratti,
			mortgageDetails.gram,
			transDetails.rate==null?"0.0":transDetails.rate,
			val.loanTransaction.date	,
			dayCount(val.date),
			transDetails.amount==null?"0.0":transDetails.amount,
			val.loanTransaction.paidAmount==null?"0.0":val.loanTransaction.paidAmount,
			val.loanTransaction.interest_amount==null?"0.0":val.loanTransaction.interest_amount	,	
			val.loanTransaction.interestPaidAmount==null?"0.0":val.loanTransaction.interestPaidAmount,
			totalvalue==null?"0.0":totalvalue,
			val.balance==null?"0.0":val.balance,
			"<input class='form-controlcol-sm-2  upd'  type='button' updid='"+val.id+"' name='update' value='update' id='dt_update' />",
				"<input class='form-controlcol-sm-2  del' type='button' delid='"+val.id+"' name='delete' value='delete' id='dt_delete' />",
				"<input class='form-controlcol-sm-2  view' type='button' viewid='"+val.trans_id+"' name='view' value='View' data-toggle='modal'  data-target='#viewModal' id='dt_view' />"
			
		 ]); 
		
	}); 
	 
	 
	 
	 
	 $("#dt_bori").val(totalBori);
	 $("#dt_ana").val(totalAna);
	 $("#dt_ratti").val(totalRatti);
	 $("#dt_gram").val(totalGram);
	 $("#dt_balance").val(totalBalance);
	 
	 
	 
	 
	 
	 
	 deliveryTable = $('#deliveryTransaction_tbl').DataTable({
	data:           data,
   deferRender:    true,
   scrollY:        200,
   scrollCollapse: true,
   scroller:       true,
   "order": [],
	 "footerCallback": function ( row, data, start, end, display ) {
var api = this.api(), data;


    } });
	

}


var dayCount = function(date)
{
let old = moment(date,"DD/MM/YYYY"); 
	let today = moment(); 
	let diff = today.diff(old,"days");		       	 
	return diff;

};




$("#d_view_report").click(function(){
	searchByDSlno();
	searchByDName();
	searchByDId();
	searchByDAmount();
	
	var dateFrom = $("#d_datepicker_from").val();
	var dateTo = $("#d_datepicker_to").val();
	
	console.log(dateFrom);
	console.log(dateTo);
	//dateToAndFrom(deliveryTable,dateFrom.dateTo);
});

function searchByDSlno(){
	
	deliveryTable
      .column(2)
      .search($("#d_serial").val())
      .draw();
}

function searchByDName(){
	
	deliveryTable
      .column(1)
      .search($("#d_cust_name").val())
      .draw();
}

	function searchByDId(){
	
		deliveryTable
      .column(3)
      .search($("#d_cust_id").val())
      .draw();
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
	
	


















