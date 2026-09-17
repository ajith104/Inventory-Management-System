package com.inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.model.Sale;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.SaleRepository;

@Service
public class SaleService {

    private final PredictionService predictionService;
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private ProductRepository productRepository;

    SaleService(PredictionService predictionService) {
        this.predictionService = predictionService;
    }
		
	public Sale saveSale(Sale sale) {
		Product product = productRepository.findById(sale.getProductId()).orElse(null);
		
		if(product != null) {
			if(product.getQuantity() < sale.getQuantitySold()) {
				throw new RuntimeException("Not enough stock for product: " + product.getProductName());
			}
			
				product.setQuantity(product.getQuantity() - sale.getQuantitySold());
				productRepository.save(product);
		}
		return saleRepository.save(sale);
	}
		
	public List<Sale> getAllSale() {
		return saleRepository.findAll();
	}
		
	public Sale getSaleById(Long id) {
		return saleRepository.findById(id).orElse(null);
	}
	
	public Sale updateSale(Long id,Sale sale) {
		Sale oldSale = saleRepository.findById(id).orElse(null);
		
		if(oldSale == null) {
			return null;
		}
		
		Product product = productRepository.findById(oldSale.getProductId()).orElse(null);
		
		if(product !=null) {
			int difference = sale.getQuantitySold()-oldSale.getQuantitySold();
			if(product.getQuantity() + oldSale.getQuantitySold() < sale.getQuantitySold()) {
				throw new RuntimeException("Not enough stock for product: " + product.getProductName());
			}
			product.setQuantity(product.getQuantity()-difference);
			productRepository.save(product);
		}
		sale.setSaleId(id);
		return saleRepository.save(sale);
				
	}
		
	public void deleteSale(Long id) {
		 Sale sale=saleRepository.findById(id).orElse(null);
		 if(sale==null) {
			 return;
		 }
		 Product product = productRepository.findById(sale.getProductId()).orElse(null);
		 if(product != null) {
			 product.setQuantity(product.getQuantity() + sale.getQuantitySold());
			 productRepository.save(product);
		 }
		 saleRepository.deleteById(id);
	}

}
