package com.inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.model.Purchase;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.PurchaseRepository;
	
@Service
public class PurchaseService {
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public Purchase savePurchase(Purchase purchase) {
		Product product = productRepository.findById(purchase.getProductId()).orElse(null);
		
		if(product != null) {
			product.setQuantity(product.getQuantity() + purchase.getQuantityPurchased());
			productRepository.save(product);
		}
		return purchaseRepository.save(purchase);
	}
		
	public List<Purchase> getAllPurchase() {
		return purchaseRepository.findAll();
	}
		
	public Purchase getPurchaseById(Long id) {
		return purchaseRepository.findById(id).orElse(null);
	}
	
	public Purchase updatePurchase(Long id,Purchase purchase) {
		Purchase oldPurchase = purchaseRepository.findById(id).orElse(null);
		if(oldPurchase == null) {
			return null;
		}
		
		Product product=productRepository.findById(oldPurchase.getProductId()).orElse(null);
				if(product != null) {
					int difference=purchase.getQuantityPurchased() - oldPurchase.getQuantityPurchased();
					product.setQuantity(product.getQuantity() + difference);
					productRepository.save(product);
				}
				purchase.setPurchaseId(id);
				return 
					purchaseRepository.save(purchase);
	}
		
	public void deletePurchase(Long id) {
		Purchase purchase=purchaseRepository.findById(id).orElse(null);
		if(purchase == null) {
			return;
		}
		
		Product product = productRepository.findById(purchase.getProductId()).orElse(null);
		if( product!= null ) {
			product.setQuantity(product.getQuantity() - purchase.getQuantityPurchased());
			productRepository.save(product);
		}
		purchaseRepository.deleteById(id);
	}
	
	public List<Purchase> getRecentPurchases(){
		return
			purchaseRepository.findTop5ByOrderByPurchaseDateDesc();
	}
	
	public long getTotalItemsPurchased() {
		return purchaseRepository.getTotalItemsPurchased();
	}

}
