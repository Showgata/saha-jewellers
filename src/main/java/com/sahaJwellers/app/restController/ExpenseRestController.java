package com.sahaJwellers.app.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Expense;
import com.sahaJwellers.app.model.ExpenseInfo;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.ExpenseService;

@RestController
@RequestMapping("/mortgage-app/api/exp")
public class ExpenseRestController {

	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/info")
	public List<ExpenseInfo> fetchAllInfo() {
		List<ExpenseInfo> expenseInfo = expenseService.fetchAllExpenseInfo();
		return expenseInfo;
	}
	
	@GetMapping("/all")
	public List<Expense> fetchAllExpense() {
		List<Expense> expense = expenseService.fetchAllExpense();
		return expense;
	}
	
	
	@GetMapping("/expense-today")
	public List<Expense> fetchAllExpenseToday() {
		List<Expense> expense = expenseService.listExpenseForToday();
		return expense;
	}
	
	@PostMapping("/saveInfo")
	public ExpenseInfo saveInfo(@RequestBody ExpenseInfo expenseInfo) {
		System.out.println("=========================================================================================");
		System.out.println(expenseInfo.toString());
		System.out.println(expenseInfo.getExpenseName());
		System.out.println(expenseInfo.getExpenseDescription());
		System.out.println("=========================================================================================");
		
		return expenseService.saveExpenseInfo(expenseInfo);
		
	}
	
	@PostMapping("/save_expense")
	public Expense saveExpense(@RequestBody Expense e) {
		return expenseService.saveExpense(e);
		
	}
	
	
	@GetMapping("/getOne/{id}")
	public Expense fetchOneById(@PathVariable("id") Long id) {
		
		return expenseService.getExpenseById(id);
	}
	
	
	@GetMapping("/getOneInfo/{id}")
	public ExpenseInfo fetchOneInfoById(@PathVariable("id") Long id) {
		
		return expenseService.getExpenseInfoById(id);
	}
	
	@PostMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		
		expenseService.deleteExpense(id);
	}
	
	@PostMapping("/info/{id}")
	public void deleteByInfoId(@PathVariable("id") Long id) {
		
		expenseService.deleteExpenseInfo(id);
	}
	
	
	@GetMapping("/getAll")
	public List<Object[]> getAll() {
		
		return expenseService.getAllExpenseDetails();
	}
	
	@GetMapping("/getOneExpenseDetail/{id}")
	public List<Object[]> getOneExpenseDetail(@PathVariable("id") Long id) {
		
		return expenseService.getOneExpenseDetail(id);
	}
	
	
	
	@RequestMapping(value = "/search", params = { "date" }, method = RequestMethod.GET)
	public List<Object[]> getECOfSpecificDate(@RequestParam(value = "date") String date){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {

            Date d = formatter.parse(date);
            System.out.println(d);
            System.out.println(formatter.format(d));
            
            return expenseService.listExpensesBySpecificDate(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
		
	}
	
	
	
	
	
	@RequestMapping(value = "/search", params = { "dateto","datefrom"}, method = RequestMethod.GET)
	public List<Object[]> getECByDateRange(@RequestParam(value = "dateto") String dateto,@RequestParam(value = "datefrom") String datefrom){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {

            Date dto = formatter.parse(dateto);
            Date dfrom =formatter.parse(datefrom);
            
            return expenseService.listExpensesByDateRange(dto, dfrom);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
		
	}
	
	
	
	@RequestMapping(value = "/getWeeklyReport", params = {"date"}, method = RequestMethod.GET)
	public List<Object[]> reportWeeklyExpense(@RequestParam(value = "date")Date date)
	{
		System.out.println("Exp Date : "+date);
		return expenseService.reportWeeklyExpense(date);
	}
	
	
	@RequestMapping(value = "/getMonthlyReport", params = {"month","year"}, method = RequestMethod.GET)
	public List<Object[]> reportMonthlyExpense(@RequestParam(value = "month")Integer month,@RequestParam(value = "year")Integer year)
	{
		System.out.println("Exp Date : "+year);
		return expenseService.reportMonthlyExpense(month, year);
	}
	
	
	@RequestMapping(value = "/getYearlyReport", params = {"year"}, method = RequestMethod.GET)
	public List<Object[]> reportYearlyExpense(@RequestParam(value = "year")Integer year)
	{
		System.out.println("Exp Date : "+year);
		return expenseService.reportYearlyExpense(year);
	}
	
	
	@RequestMapping(value = "/getDecadeReport", params = {"lyear","uyear"}, method = RequestMethod.GET)
	public List<Object[]> reportDecadeExpense(@RequestParam(value = "lyear")Integer lyear,@RequestParam(value = "uyear")Integer uyear)
	{
		System.out.println("Exp Date : "+lyear+"-"+uyear);
		return expenseService.reportDecadeExpense(lyear, uyear);
	}
	
	
}
