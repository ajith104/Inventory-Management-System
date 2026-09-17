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

import com.inventorymanagement.model.Purchase;
import com.inventorymanagement.service.PurchaseService;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {
	@Autowired
	private PurchaseService purchaseService;
	
	@PostMapping("/add")
	public Purchase addPurchase(@RequestBody Purchase purchase) {
		return purchaseService.savePurchase(purchase);
	}
	
	@GetMapping
	public List<Purchase> getAllPurchase() {
		return purchaseService.getAllPurchase();
	}
	
	@GetMapping("/{id}")
	public Purchase getPurchaseById(@PathVariable Long id) {
		return purchaseService.getPurchaseById(id);
	}
	
	@PutMapping("/{id}")
	public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
		return purchaseService.updatePurchase(id, purchase);
	}
	
	@DeleteMapping("/{id}")
	public void deletePurchase(@PathVariable Long id) {
		purchaseService.deletePurchase(id);
	}

}
