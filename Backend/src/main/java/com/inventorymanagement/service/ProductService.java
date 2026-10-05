package com.inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> getLowStockProducts() {
		return productRepository.findByQuantityLessThanEqual(10);
	}
	
	public List<Product> searchProducts(String productName) {
		return productRepository.findByProductNameContainingIgnoreCase(productName);
	}
	
	public double getInventoryValue() {
		double totalValue = 0.0;
		
		List<Product> products = productRepository.findAll();
		
		for(Product product : products) {
			totalValue += product.getPrice() * product.getQuantity();
		}
		return totalValue;
	}
	
	

}
