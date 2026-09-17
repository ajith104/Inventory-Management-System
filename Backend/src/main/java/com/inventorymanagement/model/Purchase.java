package com.inventorymanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;
	private Long productId;
	private Integer quantityPurchased;
	private LocalDate purchaseDate;
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantityPurchased() {
		return quantityPurchased;
	}
	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", productId=" + productId + ", quantityPurchased="
				+ quantityPurchased + ", purchaseDate=" + purchaseDate + "]";
	}
	public Purchase(Long purchaseId, Long productId, Integer quantityPurchased, LocalDate purchaseDate) {
		super();
		this.purchaseId = purchaseId;
		this.productId = productId;
		this.quantityPurchased = quantityPurchased;
		this.purchaseDate = purchaseDate;
	}
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
