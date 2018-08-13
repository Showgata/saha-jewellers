var websiteInitialUrl = "http://localhost:8080";   
websiteInitialUrl = "http://node16543-sahjwel.mj.milesweb.cloud";
var disabledDays = [0];
var dateSettings = {
    language: 'en',
    maxDate: new Date(),
    autoClose:true,
    onRenderCell: function (date, cellType) {
        if (cellType == 'day') {
            var day = date.getDay(),
                isDisabled = disabledDays.indexOf(day) != -1;
            return {
                disabled: isDisabled
            }
        }
    }
};



function getAbsoluteUrl(url){
	return websiteInitialUrl+"/"+url;
}

function roundTo(n, digits) {
            var negative = false;
            if (digits === undefined) {
                digits = 0;
            }
                if( n < 0) {
                negative = true;
              n = n * -1;
            }
            var multiplicator = Math.pow(10, digits);
            n = parseFloat((n * multiplicator).toFixed(11));
            n = (Math.round(n) / multiplicator).toFixed(2);
            if( negative ) {    
                n = (n * -1).toFixed(2);
            }
            return n;
        }
        
       
        	
        
        function GetFormattedDate() {

            var todayTime = new Date();

            var month = format(todayTime .getMonth() + 1);

            var day = format(todayTime .getDate());

            var year = todayTime .getFullYear();

            return day + "/" + month + "/" + year;

        }
        function buildAjaxForJson(url, methodType, inputData){
       	 return $.ajax({
       	    url : url,
       	    method : methodType,
       	    data : JSON.stringify(inputData),
       	    dataType : "json",
       	    contentType: "application/json"
       	 })
       	}
       
        function buildAjax(url, methodType){
        	return $.ajax({
           	    url : url,
           	    method : methodType,
           	    contentType: "application/json"
           	 })
        }
        // 2 digit formatted date
        function format(n){
        	return n<10? '0'+n:''+n;
        } 
        
       function formatFloatValue(val){
       		return val==""||isNaN(val)?null:parseFloat(val);
       	}
       	
       function formatIntegerValue(val){
      		return val==""||isNaN(val)?null:parseInt(val);
      	}	
       
       function formatValue(val){
    	   return val==""?null:val;
       }
       
       function moveDataTableRow(position,table){
    	   var currentPage = table.page();
    	   var index = position-1,
           rowCount = table.data().length-1,
           insertedRow = table.row(rowCount).data(),
           tempRow;

       for (var i=rowCount;i>index;i--) {
           tempRow = table.row(i-1).data();
           table.row(i).data(tempRow);
           table.row(i-1).data(insertedRow);
       }     
       //refresh the current page
       table.page(currentPage).draw(false);
       }