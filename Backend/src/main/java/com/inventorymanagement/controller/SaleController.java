package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.model.Sale;
import com.inventorymanagement.service.SaleService;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {
	@Autowired
	private SaleService saleService;
	
	@PostMapping("/add")
	public Sale addSale(@RequestBody Sale sale) {
		return saleService.saveSale(sale);
	}
	
	@GetMapping
	public List<Sale> getAllSales() {
		return saleService.getAllSale();
	}
	
	@GetMapping("/{id}")
	public Sale getSaleById(@PathVariable Long id) {
		return saleService.getSaleById(id);
	}
	
	@PutMapping("/{id}")
	public Sale updateSale(@PathVariable Long id, @RequestBody Sale sale) {
		return saleService.updateSale(id, sale);
	}
	
	@DeleteMapping("/{id}")
	public void deleteSale(@PathVariable Long id) {
		saleService.deleteSale(id);
	}
}
