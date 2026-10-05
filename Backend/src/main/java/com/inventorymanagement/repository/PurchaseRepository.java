package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	List<Purchase>
	findTop5ByOrderByPurchaseDateDesc();
	
	@Query("SELECT COALESCE(SUM(pr.quantityPurchased),0)From Purchase pr")
	long getTotalItemsPurchased();

}
