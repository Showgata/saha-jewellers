var getSumById =function(voucherId)
    {
    	
    	var URL =getAbsoluteUrl("mortgage-app/api/voucher/loan/sum/"+voucherId); 
  		
  		$.ajax({
  	     type: "GET",
  	     url: URL,
  	    success: function(respJson) {
  	    	
  	    	console.log("Total sum="+respJson);
  	    	$("#total_paid_balance").val(respJson);
  	    	
         	
  	  		}  
  	})
    }




var getSumInterestById =function(voucherId)
{
	
	var URL =getAbsoluteUrl("mortgage-app/api/voucher/loan/sum-interest/"+voucherId); 
		
		$.ajax({
	     type: "GET",
	     url: URL,
	    success: function(respJson) {
	    	
	    	console.log("Total interest sum="+respJson);
	    	$("#total_paid_interest_balance").val(respJson);
	    	
     	
	  		}  
	})
}