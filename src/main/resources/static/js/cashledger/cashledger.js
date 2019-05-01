   var updatedRow = null;
   var currentPage = null;

  
$(document).ready(function () {

	showAllData();
	 //  listAllCustomer();
   });

function showAllData(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	  
	if(dd<10) {
	    dd = '0'+dd
	} 

	if(mm<10) {
	    mm = '0'+mm
	} 

	today = yyyy+ '-'+ mm+ '-'+ dd ;
	
	
	 var balance = 0;
	 var URL = getAbsoluteUrl("/mortgage-app/api/cash/");
	 var URL2 = getAbsoluteUrl("/mortgage-app/api/cash/closingbal/");// to start opening balance with yesterdays closing balance
//	
//	 function getData(yt_url, callback) {
//	 $.ajax({
//	        url: yt_url,
//	        method: "GET",
//	        dataType: "json",
//	        success: callback,
//	        error: function(request, status, error) {
//	            alert(status);
//	        }
//	 });
//	 }//end function
//	 alert( JSON.parse(getData(URL2, function(response) { return response;})));
//	 
//	 
	var  balance = function () {
		    var tmp = null;
		    $.ajax({
		        'async': false,
		        'type': "GET",
		        'global': false,
		        'url': URL2,
		        'success': function (data) {
		            tmp = data;
		        }
		    });
		    return tmp;
		}();
	 
	 
	 
	 
		 
		   // alert('The response was: ' + response);
	 
	 var data1 = [];
	 data1.push(
		 1,
		 today,
		 "opening-balance",
		 "0",
		 "0",
		 "0",
		 "0",
		 "0",
		 "0",
		 "0",
		 "0",
		 "0",
		 (balance==""?0:balance),
		 
	 
	 
	 );
	 console.log(data1);
	 //data1 = {"1/03/19","opening-balance","0","0","0","0","0","0","0","0","0","-2000"};
	 var data = [];
	   buildAjax(URL, "GET").then(function(respJson){
   		console.log(respJson);
   	
   		var valuee = JSON.parse(respJson);
   		
   	//	alert(respJson.length);
   	// jQuery.each(respJson, function(j, val) {
 		var value = 0;
 		var key = 1 ;
 		
 		//cash_in filters
 		
 		
 		
 		var balance = function () {
		    var tmp = null;
		    $.ajax({
		        'async': false,
		        'type': "GET",
		        'global': false,
		        'url': URL2,
		        'success': function (data) {
		            tmp = data;
		        }
		    });
		    return tmp;
		}();
		
 		var total_in = 0;
 		var total_out = 0;
 		
 		if(balance == "" && total_in == 0 && total_out == 0){
 			$("#dt_bal").val("0");
 			$("#cashb").text("0");
 			$("#dt_out").val(total_out);
				$("#casho").text(total_out);
				$("#dt_in").val(total_in);
 				$("#cashi").text(total_in);
 		}
 		
 		else
 			if(balance != 0 && total_in == 0 && total_out == 0){
 			$("#dt_bal").val(balance);
 			$("#cashb").text(balance);
 			$("#dt_out").val(total_out);
				$("#casho").text(total_out);
				$("#dt_in").val(total_in);
 				$("#cashi").text(total_in);
 		}
//here1
   		jQuery.each(valuee, function(i, val) {
 			key = key+1;//so that the table id increments
//   		
//   			
//   	
 			
 			
 			if(val.tmtype === "Gm-U" || val.tmtype === "Gm-D" || val.tmtype === "Lg-U" || val.tmtype === "Lg-D" || val.tmtype === "Lt" || val.tmtype === "CI")
 				{
 				var cashin = val.amount;
 				balance = +balance + +cashin ;
 				total_in = +total_in +  +cashin;
 				$("#dt_in").val(total_in);
 				$("#cashi").text(total_in);
 				
 				}
 			else
 				{
 				var 	cashout = 	val.amount;
 				balance =  +balance - +cashout ;
 				total_out = +total_out +  +cashout;
 				$("#dt_out").val(total_out);
 				$("#casho").text(total_out);
 				}
 			$("#dt_bal").val(balance);
 			$("#cashb").text(balance);
   			data.push([
   				key,
   				val.date,
					val.tmtype,
					(val.voucher_sl==null?"0":val.voucher_sl),
					
					(val.customer_sl==null?"0":val.customer_sl),
					val.name,	
					(val.int_rate==null?"0":val.int_rate),//place intrate here
					val.paid_int,//paid int amt
					val.paid_amt,//paid loan amt
					(val.amount==null?"0":val.amount),//amount
					(cashin==null?"0":cashin),//cashin
					(cashout==null?"0":cashout),//cashout
					balance,//balance
					//(val.account.interestRate==null?"":val.account.interestRate)
//	 				"<input id='cust'"+val.customerId+" class='form-control col-sm-2 btn btn-xs btn-theme upd' updid='"+val.customerId+"'  type='button' name='update' value='update' id='update' />",
//	 				"<input class='form-control col-sm-2 btn btn-xs btn-theme del' type='button' name='delete' value='delete' delid='"+val.customerId+"' id='delete' />"
	 				]); 
   			 }); 
   		
   		//here
 //  	});//endfor
 		
 		
 		
   		console.log(data);
   		data.push(data1);
   		//data1.concat(data);
   		console.log(data);
   		//placing data in datatable
var	customerDataTable = $('#cashledger_tb').DataTable({
    		data:           data,
            deferRender:    true,
            scrollY:        300,
            scrollCollapse: true,
            scroller:       true,
            //            "order": [[0,"ID","asc"]],//1, "Voucher SL", "ASC"

//			 "footerCallback": function ( row, data, start, end, display ) {
//    var api = this.api(), data;
//}
    	});  
 
   		//click a button

   		
	//down is last brace of build 
},function(reason){
  	 console.log("error in processing your request", reason);
	}); 

	   
	 
	 //  /mortgage-app/api/cash/todays
	   
		 setTimeout(function() {
			 $('.abc').trigger('click');
		    },30);
		
		 var interval = null;

		 jQuery(function(){
		   interval = setInterval(callFunc, 3000);
		 });

		 function callFunc(){
		   jQuery('.abc').trigger('click');
		 }
		 
		 
		 
		
		  $(".abc").click(function() { 
		
			  var value1=$("#dt_in").val();
			  var value2=$("#dt_out").val();
			  var value3=$("#dt_bal").val();
			    
			  
			  
			  $.ajax({
				   url: getAbsoluteUrl("/mortgage-app/api/cash/todays"),
				   type: "get", //send it through get method
				   data: { 
				     cashin: value1  , 
				     cashout: value2 , 
				     balance: value3
				   },
				   success: function(response) {
				    console.log(response.toString());
					   
				   },
				   error: function(xhr) {
				     //Do Something to handle error
				   }
				 });  
			   });
		
	   
	 
	  
	   
}


// function listAllCustomer(){
//   var URL = getAbsoluteUrl("/mortgage-app/api/cashledger/all");
//   var data = [];
//   buildAjax(URL, "GET").then(function(respJson){
// 		console.log(respJson);
// 		
// 		 jQuery.each(respJson, function(i, val) {
//   			 data.push([(val.customerId==null?"0":val.customerId),
//   				val.customerName,
//				val.mobile,
//				val.address,
//				val.references,
//				(val.note==null?"":val.note),
// 				"<input id='cust'"+val.customerId+" class='form-control col-sm-2 btn btn-xs btn-theme upd' updid='"+val.customerId+"'  type='button' name='update' value='update' id='update' />",
// 				"<input class='form-control col-sm-2 btn btn-xs btn-theme del' type='button' name='delete' value='delete' delid='"+val.customerId+"' id='delete' />"
// 				]); 
//   			 });
//  		customerDataTable = $('#basic-datatables').DataTable({
// 		data:           data,
//         deferRender:    true,
//         scrollY:        200,
//         scrollCollapse: true,
//         scroller:       true,
//         "order": [[ 0 ,"asc"]],
//			 "footerCallback": function ( row, data, start, end, display ) {
// var api = this.api(), data;
//}
// 	});  
//		 
//		},function(reason){
//	       	 console.log("error in processing your request", reason);
//    	}); 
//listallcustomer end

