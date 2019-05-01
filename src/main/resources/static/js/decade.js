var selectedLYear = 2010;
var selectedUYear = 2019;

var changeDateFormat = function(date,ex){
	
	var d =moment(date,"DD/MM/YYYY");;
	console.log("date=="+d);
	return d.format(ex);
}

var getTodaysDate =function()
{
	return moment().format("DD/MM/YYYY");
}

//Select month
var fillYear = function(){
	
	var currentMonth =moment().month();
	var currentYear =moment().year();
	
	//Select specific year
	var minYear =2019;
	var maxYear =2100;
	
	var temp =2010;
	
	for(var i=minYear;i<=maxYear;i=i+10)
		{
			var s = temp +"-"+i;
			var o = new Option(s, s);
		 	/// jquerify the DOM object 'o' so we can use the html method
		 	$(o).html(s);
		 	$("#decade").append(o);
		 	
		 	temp=minYear+1;
		}
	
	//$("#year option[value='" + currentYear + "']").attr('selected', 'selected');
	
}
//Urls
var URL = getAbsoluteUrl("/mortgage-app/api/voucher/getDecadeReport");
var expURL = getAbsoluteUrl("/mortgage-app/api/exp/getDecadeReport");
var loanURL = getAbsoluteUrl("/mortgage-app/api/voucher/getDecadeLoanReport");
var loanUpdateURL = getAbsoluteUrl("/mortgage-app/api/voucher/getDecadeLoanUpdateReport");

var currentMonth =moment().month()+1;
var currentYear =moment().year();

//params
var loantakeParams = {type:"loan_take",lyear:2010,uyear:2019};
var loangiveParams = {type:"loan_give",lyear:2010,uyear:2019};
var capitalParams = {type:"capital",lyear:2010,uyear:2019};
var expParams = {lyear:2010,uyear:2019};
var receiveParams = {flag:0,type:"mortgage",lyear:2010,uyear:2019};
var deliveryParams = {flag:1,type:"mortgage",lyear:2010,uyear:2019};

var loanUpdateParams = {type:"mortgage",lyear:2010,uyear:2019};
var luDetails ={type:"trans",p:loanUpdateParams};

var exDetails ={type:"expense",p:expParams};
var rcDetails ={type:"receive",p:receiveParams};
var deDetails ={type:"delivery",p:deliveryParams};

$(document).ready(()=>{
	fillYear();
	
	
	//api calls 
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
  			plotGraph(respJson,graph);
  		}
	})

}

var convertToDate =function(milliTime)
{
	
	var date = new Date(milliTime);
	return(date.toString());
}


//plot graph
var plotGraph =function(respJson,graph){
	
	if(respJson !=null)
	{
		var data = [];
		var option={};
		var yaxis=[];
		
		jQuery.each(respJson, function(i, val) {
			
			var singleElement = [val[0],val[1]];
			data.push(singleElement);
			yaxis.push(val[1]);
			//console.log(convertToDate(val[3]));
			
		});
		
		var yLimit = Math.max(...yaxis) +15;
		
		var xaxis_ticks = [];
		
		for(var i =selectedLYear,j=0;i<=selectedUYear,j<10;i++,j++){xaxis_ticks[j]=[i,i];}
		
		console.log(xaxis_ticks);
		
		
		var dataset = [            
		    { data: data},
		    respJson
		];
		
		
		var option = {
				xaxis:{
					min:selectedLYear,
					max:selectedUYear,
					ticks: xaxis_ticks,
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
	//var month = parseInt($( "#month option:selected" ).val());
	var split_year = $( "#decade option:selected" ).val();
	var year_array=split_year.split("-");
	
	selectedLYear =parseInt(year_array[0]);
	selectedUYear =parseInt(year_array[1]);
	
	console.log("year "+selectedLYear+"-"+selectedUYear);
	
	var loantakeParams = {type:"loan_take",lyear:selectedLYear,uyear:selectedUYear};
	var loangiveParams = {type:"loan_give",lyear:selectedLYear,uyear:selectedUYear};
	var capitalParams = {type:"capital",lyear:selectedLYear,uyear:selectedUYear};
	//var expParams = {year};
	//var receiveParams = {flag:0,type:"mortgage",year};
	//var deliveryParams = {flag:1,type:"mortgage",year};
	
	var loanUpdateParams = {type:"mortgage",lyear:selectedLYear,uyear:selectedUYear};
	var luDetails ={type:"trans",p:loanUpdateParams};
	
	var expParams = {lyear:selectedLYear,uyear:selectedUYear};
	var exDetails ={type:"expense",p:expParams};

	var receiveParams = {flag:0,type:"mortgage",lyear:selectedLYear,uyear:selectedUYear};
	var rcDetails ={type:"receive",p:receiveParams};
	var deliveryParams = {flag:1,type:"mortgage",lyear:selectedLYear,uyear:selectedUYear};
	var deDetails ={type:"delivery",p:deliveryParams};
	
	
	//api calls 
	apiCall(loanUpdateURL,luDetails,$("#flot-bar-chart"));
	apiCall(loanURL,rcDetails,$("#flot-bar1"));
	apiCall(loanURL,deDetails,$("#flot-bar2"));
	apiCall(expURL,exDetails,$("#flot-bar3"));
	apiCall(URL,loantakeParams,$("#flot-bar4"));
	apiCall(URL,loangiveParams,$("#flot-bar5"));
	apiCall(URL,capitalParams,$("#flot-bar6"));
	
	
}

$("#show").click(changeDateAndShowGraph);

var setInFields = function(type,respJson)
{
	var voucherCount =0;
	var loanAmt =0;
	
	jQuery.each(respJson, function(i, val) {
		
		voucherCount += val[1];
		loanAmt += val[2];
	});
	
	
	
	
	var vc_field = "#"+type+"_vc";
	var la_field = "#"+type+"_la";
	console.log("==================");
	console.log(vc_field+"  "+la_field);
	console.log(respJson);
	console.log("==================");
	$(vc_field).val(voucherCount);
	$(la_field).val(loanAmt);
		
	
	
}


//selection

//select in delivery transaction
$(".del").on('change', function (e) {
  
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
$(".rec").on('change', function (e) {
  
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
$(".trans").on('change', function (e) {
  
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

