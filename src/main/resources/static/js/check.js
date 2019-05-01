/*
    	   	check if all fields in voucher are empty are empty
    	   */
    	   
    	   var isValid =function(ignoreThisArray){
    	   		
    	   		var result =true;
    	   		
    	   		$("input").each(function() {
    	    	      var element = $(this);
    	    	      //console.log(element.attr("type"));
    	    	      //
    	    	      if (element.val().trim() === "" && element.attr('type')==="text"  && ignoreThisArray.indexOf(element.attr("id")) <0) {
    	    	    	  console.log(element.attr("type"));
    	    	    	  console.log(element.attr("id"));
    	    	    	  result = false;
    	    	      }
    	    	   });
    	   		
    	   		return result;
    	   	}
    	   
    	   
    	   
    	   
    	   
    	   
/*
 * Check if all fields in transaction are empty 
 */
    	   
    	   var isLoansFieldEmpty =function(){
   	   		
   	   		var result ={
   	   			isfieldsEmpty : false,
   	   			isVoucherIdEmpty: false
   	   		}
   
   	    	      if(($("#paid_amount").val().trim()===""||$("#paid_amount").val()==null)
   	    	    		  && ($("#paid_interest_amount").val().trim()==="" || $("#paid_interest_amount").val()==null ))
   	    	    	  {
   	    	    	  		result.isfieldsEmpty =true;
   	    	    	  }
   	   		
   	   		
   	   			if($("#voucher_id").val().trim()===""|| $("#voucher_id").val()==null)
   	   				{
	    	  		result.isVoucherIdEmpty =true;
   	   				}

   	   		return result;
   	   	}