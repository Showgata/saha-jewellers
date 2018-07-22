 var customerDataTable = null;
   var updatedRow = null;
   var currentPage = null;

   
   $(document).ready(function () {
	   listAllCustomer();
   });
   
   function listAllCustomer(){
	   var URL = getAbsoluteUrl("mortgage-app/api/customer/today");
	   var data = [];
	   buildAjax(URL, "GET").then(function(respJson){
     		console.log(respJson);
     		
     		 jQuery.each(respJson, function(i, val) {
       			
       			 data.push([(val.customerId==null?"0":val.customerId),
       				val.customerName,
					val.mobile,
					val.address,
					val.references,
					(val.note==null?"":val.note),
	 				"<input id='cust'"+val.customerId+" class='form-control col-sm-2 btn btn-xs btn-theme upd' updid='"+val.customerId+"'  type='button' name='update' value='update' id='update' />",
	 				"<input class='form-control col-sm-2 btn btn-xs btn-theme del' type='button' name='delete' value='delete' delid='"+val.customerId+"' id='delete' />"
	 				]); 
       		}); 
     		
     		customerDataTable = $('#cust_table').DataTable({
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
     		 
     		},function(reason){
		       	 console.log("error in processing your request", reason);
	       	}); 
	   
   }
   
   ;(function () {
	   let addr_field = $("#cust_addr");
	   let name_field = $("#cust_name");
	   let mobile_field = $("#cust_mobile");
	   let note_field = $("#cust_note");
	   let ref_field = $("#cust_references");
	   let custId_field = $("#cust_Id");
	   let cust_form_field = $("#cust_form");
	   
	   
	   
	   $("#cust_form").on('submit',function(e){
		   e.preventDefault();
		
		   let customer = {};
		   customer["customerId"] = formatIntegerValue(custId_field.val());
		   customer["note"] = formatValue(note_field.val());
		   customer["customerName"] = formatValue(name_field.val());
		   customer["mobile"] = formatValue(mobile_field.val());
		   customer["references"]=formatValue(ref_field.val());
		   customer["address"] = formatValue(addr_field.val());
		   
		   
		   let URL = getAbsoluteUrl("mortgage-app/api/customer/");
		   
			buildAjaxForJson(URL, "POST",customer).then(function(respJson){
				
				addr_field.val("");
				name_field.val("");
				mobile_field.val("");
				note_field.val("");
				ref_field.val("");
				cust_form_field.val("");
				if(parseInt(custId_field.val())==parseInt(respJson.customerId)){
					custId_field.val("");
					var rowNode = updatedRow.node();
					updatedRow.remove();
					customerDataTable.page(currentPage).draw(false);
				}
						
				customerDataTable.row.add([(respJson.customerId==null?"0":respJson.customerId),
					respJson.customerName,
					respJson.mobile,
					respJson.address,
					respJson.references,
					(respJson.note==null?"":respJson.note),
	 				"<input id='cust'"+respJson.customerId+" class='form-control col-sm-2 btn btn-xs btn-theme upd' updid='"+respJson.customerId+"'  type='button' name='update' value='update' id='update' />",
	 				"<input class='form-control col-sm-2 btn btn-xs btn-theme del' type='button' name='delete' value='delete' delid='"+respJson.customerId+"' id='delete' />"
	 			 ]).draw();  
				
				moveDataTableRow(1,customerDataTable);
				
			}, function(reason){
		       	 console.log("error in processing your request", reason);
	       	}); 
	   });
	   
	   $('#cust_table tbody').on( 'click', '.del', function () {
		   customerDataTable = $('#cust_table').DataTable();
       	if(confirm('Are you sure !')){
       		  currentPage = customerDataTable.page();
       		  //console.log($(this).attr('delid'));
       		  var URL = getAbsoluteUrl("mortgage-app/api/customer/"+parseInt($(this).attr('delid')));
       		  buildAjax(URL, "POST").then(function(respJson){
       		  },function(reason){
 		           	 console.log("error in processing your request", reason);
  	           	}); 
       		     var row = customerDataTable.row( $(this).parents('tr') );
                 var rowNode = row.node();
                 row.remove();
                 customerDataTable.page(currentPage).draw(false);
       	}
        
           
       });
       
       $('#cust_table tbody').on( 'click', '.upd', function () {
    	   customerDataTable = $('#cust_table').DataTable();
              currentPage = customerDataTable.page();
       		  updatedRow = customerDataTable.row( $(this).parents('tr') );
       		  //var rowNode = row.node();
       		  
       		  //console.log($(this).attr('delid'));
       		  var URL = getAbsoluteUrl("mortgage-app/api/customer/"+parseInt($(this).attr('updid')));
       		  buildAjax(URL, "GET").then(function(respJson){
       			addr_field.val(respJson.address);
				name_field.val(respJson.customerName);
				mobile_field.val(respJson.mobile);
				note_field.val(respJson.note);
				ref_field.val(respJson.references);
				custId_field.val(respJson.customerId);
				
				
				
       		  },function(reason){
 		           	 console.log("error in processing your request", reason);
  	           	}); 
       });
	   
	 })();
   	