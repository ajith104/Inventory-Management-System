package com.inventorymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.DashboardStats;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.PurchaseRepository;
import com.inventorymanagement.repository.SaleRepository;
import com.inventorymanagement.repository.SupplierRepository;

@Service
public class DashboardService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	public DashboardStats getDashboardStats() {
		return new DashboardStats(
				productRepository.count(),
				saleRepository.count(),
				purchaseRepository.count(),
				supplierRepository.count()
		);
	}
	
	public long getTotalStockQuantity() {
	    return productRepository.getTotalStockQuantity();
	}
	
	public long getOutOfStockCount() {
		return productRepository.countByQuantity(0);
	}
	
	public long getTotalItemsSold() {
		return saleRepository.getTotalItemsSold();
	}
	
	public long getTotalItemsPurchased() {
		return purchaseRepository.getTotalItemsPurchased();
	}

}
