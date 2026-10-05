package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
	List<Sale>
	findTop5ByOrderBySaleDateDesc();
	
	@Query("SELECT COALESCE(SUM(s.quantitySold),0)From Sale s")
	long getTotalItemsSold();

}
