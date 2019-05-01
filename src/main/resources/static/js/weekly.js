var changeDateFormat = function(date,ex){
	
	var d =moment(date,"DD/MM/YYYY");;
	console.log("date=="+d);
	return d.format(ex);
}

var allAmountDetail ={};

var getTodaysDate =function()
{
	return moment().format("DD/MM/YYYY");
}


var setUpDate =function(){
	var dateSetting = {
            language: 'en',
            //maxDate: new Date(),
            autoClose:true,
            
        };
		
		$("#weekly_date").datepicker(dateSetting);
		//date format changed
		   $("#weekly_date").datepicker({
			   dateFormat: "dd/mm/yyyy"
		   });
		   $("#weekly_date").val(moment().format("DD/MM/YYYY"));
	
}

//Urls
var URL = getAbsoluteUrl("/mortgage-app/api/voucher/getWeeklyReport");
var expURL = getAbsoluteUrl("/mortgage-app/api/exp/getWeeklyReport");
var loanURL = getAbsoluteUrl("/mortgage-app/api/voucher/getWeeklyLoanReport");
var loanUpdateURL = getAbsoluteUrl("/mortgage-app/api/voucher/getWeeklyLoanUpdateReport");


//params
var loantakeParams = {type:"loan_take",date:getTodaysDate()};
var loangiveParams = {type:"loan_give",date:getTodaysDate()};
var capitalParams = {type:"capital",date:getTodaysDate()};

var loanUpdateParams = {type:"mortgage",date:getTodaysDate()};
var luDetails ={type:"trans",p:loanUpdateParams};

var expParams = {date:changeDateFormat(getTodaysDate(),"MM/DD/YYYY")};
var exDetails ={type:"expense",p:expParams};

var receiveParams = {flag:0,type:"mortgage",date:getTodaysDate()};
var rcDetails ={type:"receive",p:receiveParams};

var deliveryParams = {flag:1,type:"mortgage",date:getTodaysDate()};
var deDetails ={type:"delivery",p:deliveryParams};

$(document).ready(()=>{
	setUpDate();
	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
	apiCall(loanURL,rcDetails,$("#flot-bar1"));
	apiCall(loanURL,deDetails,$("#flot-bar2"));
	apiCall(expURL,exDetails,$("#flot-bar3"));
	apiCall(URL,loantakeParams,$("#flot-bar4"));
	apiCall(URL,loangiveParams,$("#flot-bar5"));
	apiCall(URL,capitalParams,$("#flot-bar6"));
});

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



//Basic api call
var apiCall =function(URL,params,graph){
	
	var actualParam = params;
	if(params.p != null)
		{
		actualParam =params.p;
		}
	
	$.ajax({
     type: "GET",
     url: URL,
     data:actualParam,
     // parameters
  		success: function(respJson) {
  			console.log(respJson);
  			setInFields(params.type,respJson);
  			plotGraph(respJson,graph,params.type);
  		}
	})

}

var convertToDate =function(milliTime)
{
	
	var date = new Date(milliTime);
	return(date.toString());
}


//plot graph
var plotGraph =function(respJson,graph,type){
	
	if(respJson !=null)
	{
		var data = [];
		var option={};
		var yaxis=[];
		
		jQuery.each(respJson, function(i, val) {
			
			var singleElement = [val[0],val[1]];
			data.push(singleElement);
			
			console.log(convertToDate(val[3]));
			yaxis.push(val[1]);
		});
		
		
		
		
		var yLimit = Math.max(...yaxis) +15;
		var dataset = [            
		    { data: data},
		    respJson
		];
		
		
		var option = {
				xaxis:{
					min:0,
					max:6,
					ticks: [[0,"Monday"], [1,"Tuesday"], [2,"Wednesday"], [3,"Thursday"], [4,"Friday"], [5,"Saturday"],[6,"Sunday"]],
					tickLength: 0,
		            axisLabelUseCanvas: true,
		            axisLabelFontSizePixels: 12,
		            axisLabelFontFamily: 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
		            axisLabelPadding: 5
				},
				yaxis: {
					min: 0,
			        max: yLimit,
		            axisLabelUseCanvas: true,
		            axisLabelFontSizePixels: 10,
		            axisLabelFontFamily: 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
		            axisLabelPadding: 2
		        },
		        grid: {
		            hoverable: true,
		            clickable: false,
		            borderWidth: 1
		        },
		        legend: {
		            labelBoxBorderColor: "none",
		            position: "right"
		        },
		        tooltip: true,
		        tooltipOpts:{
		        	content: toolTipContent
		        },
		        series: {
		            lines: {
		                show: true,
		                fill: true,
		                fillColor: { colors: [{ opacity: 0.7 }, { opacity: 0.1}] }
		            },
		            points:{
		                show: true,
		                radius: 3
		            }
		        }
		}
		
		
		$.plot(graph,dataset,option);
		
	}
	
}


var setInFields = function(type,respJson)
{
	var voucherCount =0;
	var loanAmt =0;
	
	
	if(respJson!=null){
	jQuery.each(respJson, function(i, val) {
		
		voucherCount += val[1];
		loanAmt += val[2];
	});
	
	}
	console.log(type);
	
	var vc_field = "#"+type+"_vc";
	var la_field = "#"+type+"_la";
	
	console.log(vc_field+"  "+la_field);
	
	$(vc_field).val(voucherCount);
	$(la_field).val(loanAmt);
		
	
	
}



var toolTipContent = function(label, xval, yval, flotItem)
{
	console.log(flotItem);
	var intVal = parseInt(xval);
	return "<strong>Total Vouchers</strong> : "+yval +"<br><strong>Total Loan Amount</strong> :"+flotItem.datapoint[2];
}

//get week
var getWeekName = function(millitime){
	return new date(millitime)
}


var changeDateAndShowGraph = function(e){
	
	var date = $("#weekly_date").val().trim();
	
	
	var loanUpdateParams = {type:"mortgage",date};
	var luDetails ={type:"trans",p:loanUpdateParams};
	
	var loantakeParams = {type:"loan_take",date};
	var loangiveParams = {type:"loan_give",date};
	var capitalParams = {type:"capital",date};
	var expParams = {date:changeDateFormat(date,"MM/DD/YYYY")};
	var exDetails ={type:"expense",p:expParams};
	
	var receiveParams = {flag:0,type:"mortgage",date};
	var rcDetails ={type:"receive",p:receiveParams};
	
	var deliveryParams = {flag:1,type:"mortgage",date};
	var deDetails ={type:"delivery",p:deliveryParams};
	
	
	//api calls
	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
	apiCall(loanURL,rcDetails,$("#flot-bar1"));
	apiCall(loanURL,deDetails,$("#flot-bar2"));
	apiCall(expURL,exDetails,$("#flot-bar3"));
	
	/*
	 * 
	 * Not Required
	apiCall(URL,loantakeParams,$("#flot-bar4"));
	apiCall(URL,loangiveParams,$("#flot-bar5"));
	
	*/
	apiCall(URL,capitalParams,$("#flot-bar6"));
	
	
}

$("#show").click(changeDateAndShowGraph);


//selection

//select in delivery transaction
$(".weekly_del").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    switch(valueSelected){
    
    case "loan_take":
    	deliveryParams.type = "loan_take";
    	apiCall(loanURL,deDetails,$("#flot-bar2"));
    	break;
    	
    case "mortgage":
    	deliveryParams.type = "mortgage";
    	apiCall(loanURL,deDetails,$("#flot-bar2"));
    	break;
   
    case "loan_give":
    	deliveryParams.type = "loan_give";
    	apiCall(loanURL,deDetails,$("#flot-bar2"));
    	break;	
    
    }
});


//select in receive transaction
$(".weekly_rec").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    switch(valueSelected){
    
    case "loan_take":
    	receiveParams.type = "loan_take";
    	apiCall(loanURL,rcDetails,$("#flot-bar1"));
    	break;
    	
    case "mortgage":
    	receiveParams.type = "mortgage";
    	apiCall(loanURL,rcDetails,$("#flot-bar1"));
    	break;
   
    case "loan_give":
    	receiveParams.type = "loan_give";
    	apiCall(loanURL,rcDetails,$("#flot-bar1"));
    	break;	
    
    }
});


//select in transaction update
$(".weekly_transUpdate").on('change', function (e) {
    
    var valueSelected = this.value;
    console.log(valueSelected);
    switch(valueSelected){
    
    case "loan_take":
    	loanUpdateParams.type = "loan_take";
    	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
    	break;
    	
    case "mortgage":
    	loanUpdateParams.type = "mortgage";
    	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
    	break;
   
    case "loan_give":
    	loanUpdateParams.type = "loan_give";
    	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
    	break;	
    
    }
});



