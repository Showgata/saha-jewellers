# www.sahajewellers.com

## Gold loan management and transaction website ##

This application allows user to make various gold mortgage related vouchers and transaction. It uses spring boot mvc along with hibernate on the server side along few other libraries.In the front end side, it uses html,css,bootstrap,jquery and few additional frameworks for datepicker and plotting graph.The project is structured in a well-defined fashion and follows the principles of model-view-controller architecture.

Various segment of the project is discussed below :















1 . App package : It contains MortgageAppApplication.java file which is responsible for configuring and running this spring boot application. It also contains code to initialize and add resource handlers.

2.App.configuration :This package is responsible for set-up and configuration of the database and also for App session and authentication procedures.This contains fours java files:
MortgageUserDetailsService
MortgageAppSecurity
 CustomAuthenticationSuccessHandler
MasterDatabaseConfig

	The MortgageUserDetailsService and  MortgageAppSecurity is responsible for login authentication and session management.The configure method is mostly responsible for this. It configures the HttpSecurity. They also uses a password Encoder like BCryptPasswordEncoder for safe encoding.CustomAuthenticationSuccessHandler is used to start a session if the authentication is successful. The few code snippets are given below for demonstration :


MasterDatabaseConfig.java is responsible for setting up the hibernate with our database.It creates the master datasource bean which is required for creating the entity manager factory bean and then implements the entity manager factory bean which is required to access the JPA functionalities provided by the JPA persistence provider, i.e. Hibernate. The transaction manager is used in this applications as it uses a single JPA EntityManagerFactory for transactional data access.

### BASIC PROGRAM FLOW : ###

DispatcherServlet : It is responsible for request handling by delegating requests to additional components of Spring MVC e.g. actual controller classes i.e. those which are annotated using @Controller or @RestController (in case of RESTful Web Services), Views, View Resolvers, handler mappers etc.
HandlerMapping : The DispatcherServlet uses HandlerMapping implementations - pre-built or provided as part of the application to route incoming requests to handler objects. By default, it uses BeanNameUrlHandlerMapping and DefaultAnnotationHandlerMapping, which is driven by @RequestMapping annotation.
Controller And RestController : Both normal controller and rest controller is used in this projects. 
App.Controller : This package contains two Controller classes. One is HomeController.java and other is VoucherWebController.java . HomeController contains few basic methods maps the /login,/logout and /home requests. On the other hand ,VoucherWebController handles the request from different components of the application. 
App.RestController :It interacts with the service and handles various json request.
This package consists of seven rest controller classes.All the class are annotated by @RestController along with  default request mapping.The seven classes are explained more elaborately in the upcoming pages.They are as follows:
CustomerController
 LoanRestController
LoginController
ProductRestController
TransactionController
UserController
VoucherRestController

4. Model :The Model represents the information (the data) of the application and the rules used to manipulate the data.There are about 8 model classes used in this project.They are:
Account model
Customer model
LoanTransaction model
Mortgage model
Product model
Transaction model
User model
Voucher model
5 a.Repository:It interacts with the databases via various queries.Every repository class has @Repository annotation and extends jpaRepository class which takes two generic parameter. There are 6 repository classes used in this project namely-
	
CustomerRepository
LoanTransactionRepository
MortgageRepository
ProductRepository
TransactionRepository
UserRepository
VoucherRepository


5 b. Services and Its Implementations : Various services in this project are interfaces which acts a base class for service implementation classes. Service interfaces are annotated via @Service which was a marker annotation subclassed from @Component. There are 6 service interfaces and 6 classes providing their implementation.


FRONT-END :
All the html,css and javascript files are found in the resource directory where html files are in templates folder,and js and css files are in static folder. 

VARIOUS SECTIONS OF THE APPLICATION :
HOME SECTION :
This section provides information about transaction from various other sections like gold-mortgage,loan-take,loan-give according to their remainder date. This page has three datatable- gold-mortgage chart,loan-take chart , loan-give chart.

js file : static/js/home.js

All the charts makes an api call to mortgage-app/api/voucher/rdate (that has two parameter date and type) to retrieve their corresponding json data when the document is loaded. The below code snippet gives their implementation : 










Two api calls are made as shown in the code snippet:
First through apiCall function :



Then through transactionApiCall function :

The data retrieved is first copied in another array and then is stored in mortgageDetails / loanTakeDetails / loangiveDetails array. This array is used to update the chart via updateTable function. If no transaction is made previously, due interest amount, total interest etc are calculated in the calInterestDetails function. Two buttons are provided in the chart - update and delete. Whenever update button is clicked, a modal pops up which contains a input field with a datepicker attached to it and update reminder date button. When this button is clicked, updateDate function is called. The following code snippet shows its implementation :



















The api call takes two params the field date i.e reminder date from the input field and transId.
This request is handled by updateReminderDate method in VoucherRestController class.
Then its corresponding services method is invoked.


### CREATE-VOUCHER/CREATE-LOAN TAKE ,LOAN-GIVE AND CREATE CAPITAL SECTION : ###
A. For fetching vouchers :
 fetchVouchers() - in Create Voucher
fetchloangiveVouchers() - Create loan-give voucher
fetchLoanTakeVouchers() - Create loan-take voucher
listAllCapital() - in Create Capital
	B.For saving vouchers :
saveVoucher() - Saves a gold-mortgage voucher
saveloangiveInfo() - Saves a loan-give voucher
saveLoanTakeInfo() - Saves a loan-take voucher
saveVoucher() - Saves a capital voucher

The section helps to create a gold-mortgage voucher and view the latest ones. When the section is first loaded,one condition is checked i.e if update_id and update_type is not empty.If true, updateTransaction() method is called or else *A method is called and the table below is filled with the information of mortgages that are made on that day.

#### C. Few helper Methods: ####
getVoucherDetails() - Gets all data from the input fields ,creates a voucher object and returns it.
initPrepBeforeSaving() - Checks if fields are empty or not,before saving. And also clears all fields if save is clicked twice with the help of isSavedAlready flag.
fillFields(data) - fills up all fields with data that is passed as an argument
loadAndFillAllInputs(id,type) - this method is used for updating purpose
makeUpdateBtnToVisible() - this makes update btn appear in the screen and save button disappear.
enableSaveBtn()-this makes save btn appear in the screen and update button disappear.
reset() - resets all input fields
updateVoucherAndLoan() - updates Voucher and loan transaction both.
setInitialTransaction(bool)- sets the initial loan transaction when the voucher is first created. bool indicates the mode - normal save or update mode.
setAutoComplete() - sets the auto-complete feature of the input fields
updateVoucher(id) {in Create Capital} - this method is used for updating a existing capital voucher.

SERVER-SIDE















In the model of the voucher, the fields are of concern are :











voucher _id column does not have any constraints here as the value can be repeated depending whether it is mortgage,loan-back,loan-take,capital. The voucherId is not set by the user rather it programmically set-up in the voucherServiceImp section.












What it does is that if the voucher is created for the first time , it checks if the voucher SlId is null then last updated voucher Number is fetch with the help of getLastVoucherIdByType which takes voucher type as parameter. Then id is given accordingly.

CREATE-EXPENSE:
/expense_voucher2.html

In create-expense , saving has two parts - 
addExpenseInfo() and then setExpenseInfo() - for adding a single expense item in the drop down
saveExpenseVoucher() - for saving a expense voucher

For fetching/listing expenses :
listExpenseVoucher() - lists all the expense vouchers created that specific day
showExpenseInfoModal() - lists all the expense item created
DELIVERY,LOAN-TAKE,LOAN-GIVE TRANSACTION SECTION :
When these sections are first loaded, all the fields are initially empty except the date. When the user selects an id from the auto-complete list, all the fields are loaded with the values related to the specific id.

All this three section shares one common js file : /js/loan_transaction.js
The methods used here :
calculateInterest(...)- calculates the total interest  keeping the day in mind
giveTransAndMortgageInfo() - helper method to distinguish between mortgage and loan-take,loan-give
ifDelivered() - disables/enables some button if voucher is delivered/saved.
apiCall()- makes suitable api call
fillUpLoanTransaction() - fills up all the loan fields if api Call is successful
fillUpInterestTransactionFields() -  fills up all the interest fields if api Call is successful
updateTransactionDetailFields() -fills up all the Transaction Detail fields if api Call is successful
saveOrUpdate(type,mode) - saves if mode is 0 and updates if mode is 1
deliveryBtn - delivers a voucher
reset() - resets all the fields

CUSTOM/CUSTOM-REPORT SECTION:
This section has three important sub - section :
Transaction-History ~ User can get  transaction history of a specific id
Receive Transaction ~ All the vouchers that are saved but delivered
Delivery Transaction ~ All the vouchers that are delivered so far


Some important helper methods : Transaction History -
initSuggestion() - initializes the auto-complete feature
setAutoComplete(type) - sets the auto-complete feature based on type of voucher
giveTransAndMortgageInfo() - helper method to distinguish between mortgage and loan-take,loan-give
initializeDataTable() - initializes the data-table
fillFields(...) - fills up all fields with data that is passed as an argument
apiCall()- makes suitable api call
getPrefix(type) - get sutiable prefix based on type
updateTableAndFields - show info in the data table
searchBySlno/Name/type/id - searches by the given identifier
calculateInterest(...)- calculates the total interest  keeping the day in mind


Some important helper methods : Receive Transaction -
rtApiCall - makes suitable api call for receive transaction details
rt_updateTable -show info in the data table
fillreceiveFields - fills up all the receive transaction input fields
searchByRSlno/RName/RMType/RId/RDate - searches by the given identifier in receive transaction

Some important helper methods : Delivery Transaction -
dtApiCall - makes suitable api call for Delivery transaction details
dt_updateTable-show info in the data table
filldeliveryFields- fills up all the delivery transaction input fields
searchByDFields/DName/DType/DId - searches by the given identifier in receive transaction


Clicking view report triggers the searchBy methods and fills the input fields.

// Clicking the delete button in any data table makes a request to corresponding delete api end point.
// Clicking the update button in any data table makes a request to corresponding update api end point and takes to the corresponding type section

CUSTOM/CUSTOMER-CREDIT-LIST SECTION:

Voucher details of individual customer name can be found in this section. All the methods used in this section along with their description is given below :
initializeDataTable() - initializes the data-table
setCustomerAutoCompleteList()- sets the auto-complete feature 
apiCall(id,type,prefix)-makes suitable api call to a specific customer id’s transaction details by type
updateTable()-show info in the data table

//When view report is clicked, it takes the value of drop down and makes the apiCall based on the type and id

REPORTS SECTION:
This module contains five sub modules among which implementation of daily report is different and rest of them follows a common pattern.Each section shows graph about no of vouchers , total amount based on the day,week,month,year and decade.

DAILY REPORT SECTION:
5 urls are defined which are responsible for fetching appropriate data for the tables in this section. The urls along with the params that used during call defined are : 
jsFile : /js/daily.js
 


When the section is first loaded, api calls were made and if successful,their respective tables are filled up.


Helper methods :
updateTableTransUpdateDailyFun ~  fills up transaction update table
updateTableReceiveDailyFun ~  fills up receive transaction table
updateTableDeliveryDailyFun ~  fills up delivery transaction table
updateTableExpenseDailyFun ~  fills up expense table
updateTableCapitalDailyFun ~  fills up capital table
changeDateAndShowGraph ~ shows all the voucher details of a specific in this section via calling appropriate api and re updating all the tables. 

REST OF THE FOUR SECTIONS (WEEKLY,MONTHLY,YEARLY,DECADE)
js Files: js/weekly,js/monthly,js/year,js/decade



All the js files have urls defined and invoked in this way.The above is an example of Weekly.js. Others follow the same pattern ; just instead of weekly it has their appropriate name like yearly,monthly,decade etc.

Some Helper Methods: 

apiCall()- makes suitable api call
giveTransAndMortgageInfo() - helper method to distinguish between mortgage and loan-take,loan-give
plotGraph() - helps to plot the graph with appropriate data
setInFields() - sets data to the input fields
toolTipContent()- shows details when hovered over the graphs
changeDateAndShowGraph() - When the date is changed from the drop down ,refresh and load with new data
setUpDate() - sets up Datepicker 
fillMonthAndYear() - sets up Month and Year picker


ASSUMPTIONS / DEPENDENCIES

Most of the dependencies are found in  common/common-header.html

The Following dependencies were used :
Jquery
Font-awesome
Modernizr 
Bootstrap
Jquery Datatables
Jquery Flot .js
Jquery Datepicker
