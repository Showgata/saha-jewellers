package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.Customer;
import com.sahaJwellers.app.model.Product;
import com.sahaJwellers.app.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.ProductService#saveOrupdateProduct(com.sahaJwellers.app.model.Product)
	 */
	@Override
	public Product saveOrupdateProduct(Product product){
		return productRepository.save(product);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.ProductService#findProductById(java.lang.Long)
	 */
	@Override
	public Optional<Product> findProductById(Long id){
		return productRepository.findById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.ProductService#deleteProductById(java.lang.Long)
	 */
	@Override
	public void deleteProductById(Long id){
		 productRepository.deleteById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sahaJwellers.app.service.ProductService#findAllProduct()
	 */
	@Override
	public List<Product> findAllProduct(){
		return productRepository.findAll();
	}
}
