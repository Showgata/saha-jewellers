

$(document).ready(function(){
	fullApiCall("0","loan_take");
})


var fullApiCall = function(f,t)
{
	var newFlag = formatIntegerValue(f);
	var newType = t;
	
	
	var URL = getAbsoluteUrl("/mortgage-app/api/voucher/filter");
	$.ajax({
	     type: "GET",
	     url: URL,
	    data:{flag:newFlag,type:newType},
	  		success: function(respJson) {
	  			
	  			
	  			console.log(respJson);
	  			
	  			
	  		}
		});
	
}