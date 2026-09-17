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

import com.inventorymanagement.model.Supplier;
import com.inventorymanagement.service.SupplierService;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin(origins = "http://localhost:4200")

public class SupplierController {
	@Autowired
	private SupplierService supplierService;
	
	@PostMapping("/add")
	public Supplier addSupplier(@RequestBody Supplier supplier) {
		return supplierService.saveSupplier(supplier);
	}
	
	@GetMapping
	public List<Supplier> getAllSuppliers() {
		return supplierService.getAllSupplier();
	}
	
	@GetMapping("/{id}")
	public Supplier getSupplierById(@PathVariable Long id) {
		return supplierService.getSupplierById(id);
	}
	
	@PutMapping("/{id}")
	public Supplier updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
		supplier.setSupplierId(id);
		return supplierService.saveSupplier(supplier);
	}
	
	@DeleteMapping("/{id}")
	public void deleteSupplier(@PathVariable Long id) {
		supplierService.deleteSupplier(id);
	}

}
