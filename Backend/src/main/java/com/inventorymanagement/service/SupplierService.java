package com.inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.Supplier;
import com.inventorymanagement.repository.SupplierRepository;

@Service
public class SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;
		
	public Supplier saveSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}
		
	public List<Supplier> getAllSupplier() {
		return supplierRepository.findAll();
	}
		
	public Supplier getSupplierById(Long id) {
		return supplierRepository.findById(id).orElse(null);
	}
		
	public void deleteSupplier(Long id) {
		 supplierRepository.deleteById(id);
	}

}
