package com.inventorymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.dto.DashboardStats;
import com.inventorymanagement.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	
	
	@GetMapping("/stats")
	public DashboardStats getStats() {
		return dashboardService.getDashboardStats();
	}
	
	@GetMapping("/total-stock")
	public long getTotalStockQuantity() {
	    return dashboardService.getTotalStockQuantity();
	}
	
	@GetMapping("/out-of-stock")
	public long getOutOfStock() {
	    return dashboardService.getOutOfStockCount();
	}
	
	@GetMapping("/total-items-sold")
	public long getTotalItemsSold() {
		return dashboardService.getTotalItemsSold();
	}
	
	@GetMapping("/total-items-purchased")
	public long getTotalItemsPurchased() {
		return dashboardService.getTotalItemsPurchased();
	}

}
