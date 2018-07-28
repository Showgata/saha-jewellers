package com.sahaJwellers.app.restController;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.service.CustomerService;
import com.sahaJwellers.app.util.DateUtil;

@RestController
@RequestMapping("/mortgage-app/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(path="/",method=RequestMethod.GET)
	public List<Customer> list(){
		return customerService.findAllCustomer();
/*		Customer cust = new Customer();
		cust.setAddress("gj");
		cust.setCustomerId(1L);
		cust.setCustomerName("sd");
		cust.setMobile("hfth");
		cust.setNote("hftg");
		cust.setReferences("hfhf");
		
		List<Customer> custList = new ArrayList<Customer>();
		custList.add(cust);
		*/
		//return custList; 
	}
	
	@RequestMapping(path="/",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Customer saveCustomer(@RequestBody Customer customer) {
		System.out.println("saved Customer");
		customer = customerService.saveOrupdateCustomer(customer);
		return customer;
	}
	
	@GetMapping("/{id}")
	public Customer findVoucherById(@PathVariable("id") Long id) {
		Optional<Customer> customer = customerService.findCustomerById(id);
		if(customer.isPresent()) {
			return customer.get();
		} else {
			return new Customer();
		}
	}
	
	@PostMapping("/{id}")
	public void deleteCustomerById(@PathVariable("id") Long id) {
		customerService.deleteCustomerById(id);
	}
	
	@GetMapping("/today")
	public List<Customer> fetchCustomerListForToday(){
		//return customerService.fetchAllCustromerByDate(DateUtil.atStartOfDay(new Date()));
		return customerService.findAllCustomer();
	}
}
