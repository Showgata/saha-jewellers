   var updatedRow = null;
   var currentPage = null;

  //date from and date to functionality
$(document).ready(function () {

	showDatefrom_toData();
	 //  listAllCustomer();
   });

function showDatefrom_toData(){


		
	    $( "#date_from" ).datepicker({ dateFormat: 'yyyy-mm-dd' });
	    $( "#date_to" ).datepicker({ dateFormat: 'yyyy-mm-dd' });
	    $( "#view_report" ).click(function(){
	    	$( "#todays" ).css("display","none");
//	        var table = $('#cashledger_tb').DataTable();
//            
//            table
//                .clear()
//                .draw();
            
            $("#cashledger_tb").dataTable().fnDestroy(); // to destroy previous records in table
        
//	    	 var URL3 = getAbsoluteUrl("/mortgage-app/api/cash/datefrom_dateto");
	    	 var date1 =  $( "#date_from" ).val();
	    	 var date2 =  $( "#date_to" ).val();

	    	 
	    	 
	    	 var balance = 0;
	    	 var URL = getAbsoluteUrl("/mortgage-app/api/cash/datefrom_dateto?datefrom="+date1+"&dateto="+date2);
	 
	    	 
	    	
	    	 //data1 = {"1/03/19","opening-balance","0","0","0","0","0","0","0","0","0","-2000"};
	    	 var data = [];
	    	   buildAjax(URL, "GET").then(function(respJson){
	       		console.log(respJson);
	       	alert(respJson);
	       		var valuee = JSON.parse(respJson);
	       		
	       	//	alert(respJson.length);
	       	// jQuery.each(respJson, function(j, val) {
	     		var value = 0;
	     		var key = 1 ;
	     		
	     		//cash_in filters
	     		
	     		
	     		

	    		
	     		var total_in = 0;
	     		var total_out = 0;
	     		
	     		if(balance == null && total_in == 0 && total_out == 0){
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
	    					(val.balance==null?balance:val.balance)//balance
	    					//(val.account.interestRate==null?"":val.account.interestRate)
//	    	 				"<input id='cust'"+val.customerId+" class='form-control col-sm-2 btn btn-xs btn-theme upd' updid='"+val.customerId+"'  type='button' name='update' value='update' id='update' />",
//	    	 				"<input class='form-control col-sm-2 btn btn-xs btn-theme del' type='button' name='delete' value='delete' delid='"+val.customerId+"' id='delete' />"
	    	 				]); 
	       			 }); 
	       		
	       		//here
	     //  	});//endfor
	     		
	     		
	     		
	       		console.log(data);
	       		
	       		//data1.concat(data);
	       		
	       		//placing data in datatable
	    var	customerDataTable = $('#cashledger_tb').DataTable({
	        		data:           data,
	                deferRender:    true,
	                scrollY:        300,
	                scrollCollapse: true,
	                scroller:       true,
	                "order": [[ 1, "asc" ]],
	             
	        	});  
	     
	       		//click a button

	       		
	    	//down is last brace of build 
	    },function(reason){
	      	 console.log("error in processing your request", reason);
	    	}); 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    
	    });
	
}