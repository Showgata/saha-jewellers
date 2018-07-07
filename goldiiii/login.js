
var username = $("#fname").val();
var password = $("#password").val();

function(){
var jqXhr = $.ajax(customerAjaxSettings
{


});

}();
var customerAjaxSettings = {
    url: "https://api.github.com/users/tom ",//localhost:8080//api/login
    method: "GET",
    data: {
      userId: ;	
      user:  username;
	  pass:  password;
  };
var custobj = $.ajax(customerAjaxSettings);

  custobj.done(function(data) {
    console.log('common callback', data);
  })
  .fail(function(xhr) {
    console.log('error common back', xhr);
  });
