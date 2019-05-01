var dateSet = function(currDate,reminderDate)
        {
        	/*Default reminder date*/
        	var monthsReminder = moment(currDate.val(), "DD/MM/YYYY").add(15, 'months');
        	
        	var formatDate = moment(monthsReminder).format('DD/MM/YYYY');
        	reminderDate.val(formatDate);
        	
        	var setInDatepicker = monthsReminder.toDate();
        	console.log("setInDatepicker = "+setInDatepicker);
	
        	/*
        	$(function() {
        	    
        		reminderDate.datepicker({
        	        inline: true,
        	        showOtherMonths: true,
        	        dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        	        dateFormat: 'dd/mm/yy'
        	    });
        		reminderDate.datepicker("defaultDate", setInDatepicker);
        	});
        	*/
        }