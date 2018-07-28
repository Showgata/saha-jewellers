package com.sahaJwellers.app.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.Product;
import com.sahaJwellers.app.model.Voucher;
import com.sahaJwellers.app.service.ProductService;

@RestController
@RequestMapping("/modgage-app/api/product")
public class ProductRestController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public List<Product> findAll(){
		 List<Product> product = productService.findAllProduct();
		return product;
	}
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	            //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	            //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@GetMapping("/default")
	public Voucher defaultValue() {
		return new Voucher();
	}
	
	@PostMapping("/")
	public Product saveProduct(@RequestBody Product product) {
		return productService.saveOrupdateProduct(product);
	}
	
	@GetMapping("/{id}")
	public Product findProductById(@PathVariable("id") Long id) {
		Optional<Product> product = productService.findProductById(id);
		if(product.isPresent()) {
			return product.get();
		} else {
			return new Product();
		}
	}
	
	@PostMapping("/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProductById(id);
	}
	

}
