package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import com.sahaJwellers.app.model.Product;

public interface ProductService {

	Product saveOrupdateProduct(Product product);

	Optional<Product> findProductById(Long id);

	void deleteProductById(Long id);

	List<Product> findAllProduct();

}