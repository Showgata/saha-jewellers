package com.sahaJwellers.app.restController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.service.CustomerService;

@RestController
@RequestMapping("/modgage-app/api/customer")
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
	
	
}
