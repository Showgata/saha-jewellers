//var websiteInitialUrl = "http://54.84.238.249:8080";   //elastic ip or check "www.sahajewellers.com"
var websiteInitialUrl = "http://localhost:8080";   
//websiteInitialUrl = "http://node21049-sahajweller.mj.milesweb.cloud";
var disabledDays = [0];
var dateSettings = {
    language: 'en',
    //maxDate: new Date(),
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


var monthSettings ={
        dateFormat: "mm/yy",
        language: 'en',
        startView: "months", 
        minViewMode: "months",
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        onClose: function(dateText, inst) {


            function isDonePressed(){
                return ($('#ui-datepicker-div').html().indexOf('ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all ui-state-hover') > -1);
            }

            if (isDonePressed()){
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1)).trigger('change');
                
                 $('.date-picker').focusout()//Added to remove focus from datepicker input box on selecting date
            }
        },
        beforeShow : function(input, inst) {

            inst.dpDiv.addClass('month_year_datepicker')

            if ((datestr = $(this).val()).length > 0) {
                year = datestr.substring(datestr.length-4, datestr.length);
                month = datestr.substring(0, 2);
                $(this).datepicker('option', 'defaultDate', new Date(year, month-1, 1));
                $(this).datepicker('setDate', new Date(year, month-1, 1));
                $(".ui-datepicker-calendar").hide();
            }
        }
    }





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