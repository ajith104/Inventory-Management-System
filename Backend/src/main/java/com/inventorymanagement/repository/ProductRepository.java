package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.model.Product;

@Repository 
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product>findByQuantityLessThanEqual(int quantity);
	
	List<Product>findByProductNameContainingIgnoreCase(String productName);
	
	@Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p")
	long getTotalStockQuantity();
	
	long countByQuantity(int quantity);

}
